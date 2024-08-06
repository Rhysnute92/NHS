package uk.ac.cf.spring.nhs.Diary.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.Entity.*;
import uk.ac.cf.spring.nhs.Diary.Repository.*;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DiaryEntryServiceTest {

    @Mock
    private DiaryEntryRepository diaryEntryRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private SymptomRepository symptomRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private DiaryEntryService diaryEntryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAndSaveDiaryEntrySavesDiaryEntry() throws Exception {
        CheckinForm checkinForm = new CheckinForm();
        checkinForm.setMood("GOOD");
        checkinForm.setNotes("Feeling good");
        checkinForm.setTroubleSleepingSeverity(2);
        checkinForm.setPainSeverity(3);
        checkinForm.setNumbnessSeverity(1);

        MultipartFile multipartFile = mock(MultipartFile.class);
        List<MultipartFile> photos = List.of(multipartFile);

        when(fileStorageService.storeFile(any(MultipartFile.class))).thenReturn("photo-url");
        when(photoRepository.save(any(Photo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(symptomRepository.save(any(Symptom.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(diaryEntryRepository.save(any(DiaryEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        diaryEntryService.createAndSaveDiaryEntry(checkinForm, photos);

        ArgumentCaptor<DiaryEntry> diaryEntryCaptor = ArgumentCaptor.forClass(DiaryEntry.class);
        verify(diaryEntryRepository).save(diaryEntryCaptor.capture());
        DiaryEntry savedDiaryEntry = diaryEntryCaptor.getValue();

        assertEquals("GOOD", savedDiaryEntry.getMood().name());
        assertEquals("Feeling good", savedDiaryEntry.getNotes());
        assertNotNull(savedDiaryEntry.getPhotos());
        assertNotNull(savedDiaryEntry.getSymptoms());
        assertEquals(3, savedDiaryEntry.getSymptoms().size());
    }

    @Test
    void createAndSaveDiaryEntryHandlesNullMoodAndNotes() throws Exception {
        CheckinForm checkinForm = new CheckinForm();
        checkinForm.setTroubleSleepingSeverity(2);
        checkinForm.setPainSeverity(3);
        checkinForm.setNumbnessSeverity(1);

        MultipartFile multipartFile = mock(MultipartFile.class);
        List<MultipartFile> photos = List.of(multipartFile);

        when(fileStorageService.storeFile(any(MultipartFile.class))).thenReturn("photo-url");
        when(photoRepository.save(any(Photo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(symptomRepository.save(any(Symptom.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(diaryEntryRepository.save(any(DiaryEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        diaryEntryService.createAndSaveDiaryEntry(checkinForm, photos);

        ArgumentCaptor<DiaryEntry> diaryEntryCaptor = ArgumentCaptor.forClass(DiaryEntry.class);
        verify(diaryEntryRepository).save(diaryEntryCaptor.capture());
        DiaryEntry savedDiaryEntry = diaryEntryCaptor.getValue();

        assertNull(savedDiaryEntry.getMood());
        assertNull(savedDiaryEntry.getNotes());
        assertNotNull(savedDiaryEntry.getPhotos());
        assertNotNull(savedDiaryEntry.getSymptoms());
        assertEquals(3, savedDiaryEntry.getSymptoms().size());
    }

    @Test
    void createAndSaveDiaryEntryHandlesNoPhotos() throws Exception {
        CheckinForm checkinForm = new CheckinForm();
        checkinForm.setMood("GOOD");
        checkinForm.setNotes("Feeling good");
        checkinForm.setTroubleSleepingSeverity(2);
        checkinForm.setPainSeverity(3);
        checkinForm.setNumbnessSeverity(1);

        when(symptomRepository.save(any(Symptom.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(diaryEntryRepository.save(any(DiaryEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        diaryEntryService.createAndSaveDiaryEntry(checkinForm, Collections.emptyList());

        ArgumentCaptor<DiaryEntry> diaryEntryCaptor = ArgumentCaptor.forClass(DiaryEntry.class);
        verify(diaryEntryRepository).save(diaryEntryCaptor.capture());
        DiaryEntry savedDiaryEntry = diaryEntryCaptor.getValue();

        assertEquals("GOOD", savedDiaryEntry.getMood().name());
        assertEquals("Feeling good", savedDiaryEntry.getNotes());
        assertTrue(savedDiaryEntry.getPhotos().isEmpty());
        assertNotNull(savedDiaryEntry.getSymptoms());
        assertEquals(3, savedDiaryEntry.getSymptoms().size());
    }
}
