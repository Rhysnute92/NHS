package uk.ac.cf.spring.nhs.Diary.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Diary.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.*;
import uk.ac.cf.spring.nhs.Diary.Repository.*;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiaryEntryServiceTest {

    @Mock
    private DiaryEntryRepository diaryEntryRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private SymptomRepository symptomRepository;

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private DiaryEntSerryvice diaryEntryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDiaryEntry() {
        DiaryEntry diaryEntry = new DiaryEntry(1, new Date());
        when(diaryEntryRepository.save(diaryEntry)).thenReturn(diaryEntry);

        DiaryEntry savedEntry = diaryEntryService.saveDiaryEntry(diaryEntry);

        assertNotNull(savedEntry);
        assertEquals(diaryEntry, savedEntry);
        verify(diaryEntryRepository, times(1)).save(diaryEntry);
    }

    @Test
    public void testGetAllDiaryEntries() {
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        when(diaryEntryRepository.findAll()).thenReturn(diaryEntries);

        List<DiaryEntry> result = diaryEntryService.getAllDiaryEntries();

        assertNotNull(result);
        assertEquals(diaryEntries, result);
        verify(diaryEntryRepository, times(1)).findAll();
    }

    @Test
    public void testGetDiaryEntryById() {
        int id = 1;
        DiaryEntry diaryEntry = new DiaryEntry(1, new Date());
        when(diaryEntryRepository.findById(id)).thenReturn(Optional.of(diaryEntry));

        Optional<DiaryEntry> result = diaryEntryService.getDiaryEntryById(id);

        assertTrue(result.isPresent());
        assertEquals(diaryEntry, result.get());
        verify(diaryEntryRepository, times(1)).findById(id);
    }

    @Test
    public void testGetDiaryEntriesByUserId() {
        int userId = 1;
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        when(diaryEntryRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"))).thenReturn(diaryEntries);

        List<DiaryEntry> result = diaryEntryService.getDiaryEntriesByUserId(userId);

        assertNotNull(result);
        assertEquals(diaryEntries, result);
        verify(diaryEntryRepository, times(1)).findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
    }

    @Test
    public void testDeleteDiaryEntryById() {
        int id = 1;
        doNothing().when(diaryEntryRepository).deleteById(id);

        diaryEntryService.deleteDiaryEntryById(id);

        verify(diaryEntryRepository, times(1)).deleteById(id);
    }

    @Test
    public void testCreateAndSaveDiaryEntry() throws Exception {
        CheckinForm checkinForm = new CheckinForm();
        checkinForm.setMood("GOOD");
        checkinForm.setNotes("Feeling good today");

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(fileStorageService.storeFile(mockFile)).thenReturn("photoUrl");
        checkinForm.setPhotos(Collections.singletonList(mockFile));

        SymptomDTO symptomDTO = new SymptomDTO();
        symptomDTO.setName("Pain");
        symptomDTO.setSeverity(2);
        checkinForm.setSymptoms(Collections.singletonList(symptomDTO));

        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setType("Weight");
        measurementDTO.setValue(60F);
        measurementDTO.setUnit("KG");
        checkinForm.setMeasurements(Collections.singletonList(measurementDTO));

        DiaryEntry diaryEntry = new DiaryEntry(1, new Date());

        when(diaryEntryRepository.save(any(DiaryEntry.class))).thenReturn(diaryEntry);

        diaryEntryService.createAndSaveDiaryEntry(checkinForm);

        ArgumentCaptor<DiaryEntry> diaryEntryCaptor = ArgumentCaptor.forClass(DiaryEntry.class);
        verify(diaryEntryRepository, times(1)).save(diaryEntryCaptor.capture());
        DiaryEntry capturedEntry = diaryEntryCaptor.getValue();

        assertEquals("GOOD", capturedEntry.getMood().name());
        assertEquals("Feeling good today", capturedEntry.getNotes());
        assertEquals(1, capturedEntry.getPhotos().size());
        assertEquals(1, capturedEntry.getSymptoms().size());
        assertEquals(1, capturedEntry.getMeasurements().size());
    }
}
