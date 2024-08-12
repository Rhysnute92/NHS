package uk.ac.cf.spring.nhs.Diary.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoDTO;
import uk.ac.cf.spring.nhs.Symptom.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.*;
import uk.ac.cf.spring.nhs.Diary.Repository.*;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiaryEntryServiceTest {

    @Mock
    private DiaryEntryRepository diaryEntryRepository;

    @Mock
    private PhotoService photoService;

    @Mock
    private SymptomService symptomService;

    @Mock
    private MeasurementService measurementService;

    @InjectMocks
    private DiaryEntryService diaryEntryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDiaryEntries() {
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        when(diaryEntryRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))).thenReturn(diaryEntries);

        List<DiaryEntry> result = diaryEntryService.getAllDiaryEntries();

        assertNotNull(result);
        assertEquals(diaryEntries, result);
        verify(diaryEntryRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Test
    public void testGetDiaryEntryById() {
        int id = 1;
        DiaryEntry diaryEntry = new DiaryEntry(1, new Date());
        when(diaryEntryRepository.findById(id)).thenReturn(Optional.of(diaryEntry));

        Optional<DiaryEntry> result = Optional.ofNullable(diaryEntryService.getDiaryEntryById(id));

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
        long userId = 1;

        CheckinFormDTO checkinForm = new CheckinFormDTO();
        checkinForm.setMood("GOOD");
        checkinForm.setNotes("Feeling good today");

        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setFile(mock(MultipartFile.class));
        photoDTO.setBodyPart("Neck");
        when(photoService.savePhoto(photoDTO, userId)).thenReturn(new Photo("photoUrl", new Date(), photoDTO.getBodyPart(), userId));
        checkinForm.setPhotos(Collections.singletonList(photoDTO));

        SymptomDTO symptomDTO = new SymptomDTO();
        symptomDTO.setName("Pain");
        symptomDTO.setSeverity(2);
        when(symptomService.saveSymptom(symptomDTO, 1L)).thenReturn(new Symptom(symptomDTO.getName(), symptomDTO.getSeverity(), userId));
        checkinForm.setSymptoms(Collections.singletonList(symptomDTO));

        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setType("Weight");
        measurementDTO.setValue(60F);
        measurementDTO.setUnit("KG");
        when(measurementService.saveMeasurement(measurementDTO, 1L)).thenReturn(new Measurement(measurementDTO.getType(), measurementDTO.getValue(), measurementDTO.getUnit(), userId));
        checkinForm.setMeasurements(Collections.singletonList(measurementDTO));

        DiaryEntry diaryEntry = new DiaryEntry(1, new Date());

        when(diaryEntryRepository.save(any(DiaryEntry.class))).thenReturn(diaryEntry);

        diaryEntryService.createAndSaveDiaryEntry(checkinForm, userId);

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
