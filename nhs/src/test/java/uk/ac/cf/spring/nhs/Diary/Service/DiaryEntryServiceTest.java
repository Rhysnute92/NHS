package uk.ac.cf.spring.nhs.Diary.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Repository.DiaryEntryRepository;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DiaryEntryServiceTest {

    @Mock
    private DiaryEntryRepository diaryEntryRepository;

    @Mock
    private PhotoService photoService;

    @Mock
    private SymptomService symptomService;

    @Mock
    private MeasurementService measurementService;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private DiaryEntryService diaryEntryService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void testSaveDiaryEntry() throws Exception {
        // Prepare mock data
        CheckinFormDTO checkinForm = new CheckinFormDTO();
        checkinForm.setMood("GOOD");
        checkinForm.setNotes("Feeling good");
        checkinForm.setPhotos(Collections.emptyList());
        checkinForm.setSymptoms(Collections.emptyList());
        checkinForm.setMeasurements(Collections.emptyList());

        DiaryEntry diaryEntry = new DiaryEntry(1L, LocalDateTime.now());
        when(diaryEntryRepository.save(any(DiaryEntry.class))).thenReturn(diaryEntry);

        // Test the service
        DiaryEntry savedDiaryEntry = diaryEntryService.saveDiaryEntry(checkinForm, 1L);

        // Verify that diary entry is saved and related entities aren't
        verify(diaryEntryRepository, times(1)).save(any(DiaryEntry.class));
        verify(photoService, never()).saveAll(anyList());
        verify(symptomService, never()).saveAll(anyList());
        verify(measurementService, never()).saveAll(anyList());

        assertNotNull(savedDiaryEntry);
    }

    @Test
    void testGetDiaryEntryById() {
        // Prepare mock data
        DiaryEntry diaryEntry = new DiaryEntry(1L, LocalDateTime.now());
        when(diaryEntryRepository.findById(1L)).thenReturn(Optional.of(diaryEntry));

        // Test the service
        DiaryEntry foundDiaryEntry = diaryEntryService.getDiaryEntryById(1L);

        // Verify diary entry is found
        verify(diaryEntryRepository, times(1)).findById(1L);
        assertNotNull(foundDiaryEntry);
        assertEquals(1L, foundDiaryEntry.getUserId());
    }

    @Test
    void testGetDiaryEntryById_NotFound() {
        // Mocking the case where the diary entry is not found
        when(diaryEntryRepository.findById(1L)).thenReturn(Optional.empty());

        // Test the service
        DiaryEntry foundDiaryEntry = diaryEntryService.getDiaryEntryById(1L);

        // Verify diary entry is not found
        verify(diaryEntryRepository, times(1)).findById(1L);
        assertNull(foundDiaryEntry);
    }

    @Test
    void testDeleteDiaryEntryById() {
        // Test the service
        diaryEntryService.deleteDiaryEntryById(1L);

        // Verify diary entry is deleted
        verify(diaryEntryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllDiaryEntries() {
        // Prepare mock data
        List<DiaryEntry> diaryEntries = Collections.singletonList(new DiaryEntry(1L, LocalDateTime.now()));
        when(diaryEntryRepository.findAll(any(Sort.class))).thenReturn(diaryEntries);

        // Test the service
        List<DiaryEntry> result = diaryEntryService.getAllDiaryEntries();

        verify(diaryEntryRepository, times(1)).findAll(any(Sort.class));
        assertEquals(1, result.size());
    }

    @Test
    void testGetDiaryEntriesByUserId() {
        // Prepare mock data
        List<DiaryEntry> diaryEntries = Collections.singletonList(new DiaryEntry(1L, LocalDateTime.now()));
        when(diaryEntryRepository.findByUserId(eq(1L), any(Sort.class))).thenReturn(diaryEntries);

        // Test the service
        List<DiaryEntry> result = diaryEntryService.getDiaryEntriesByUserId(1L);

        verify(diaryEntryRepository, times(1)).findByUserId(eq(1L), any(Sort.class));
        assertEquals(1, result.size());
    }
}