package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.Entity.*;
import uk.ac.cf.spring.nhs.Diary.Repository.*;
import uk.ac.cf.spring.nhs.Common.util.FileStorageService;

import java.util.*;

@Service
public class DiaryEntryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final PhotoRepository photoRepository;
    private final SymptomRepository symptomRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public DiaryEntryService(DiaryEntryRepository diaryEntryRepository,
                             PhotoRepository photoRepository,
                             SymptomRepository symptomRepository,
                             FileStorageService fileStorageService) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.photoRepository = photoRepository;
        this.symptomRepository = symptomRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public DiaryEntry saveDiaryEntry(DiaryEntry diaryEntry) {
        return diaryEntryRepository.save(diaryEntry);
    }

    @Transactional(readOnly = true)
    public List<DiaryEntry> getAllDiaryEntries() {
        return diaryEntryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DiaryEntry> getDiaryEntryById(int id) {
        return diaryEntryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DiaryEntry> getDiaryEntriesByUserId(int userId) {
        return diaryEntryRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteDiaryEntryById(int id) {
        diaryEntryRepository.deleteById(id);
    }

    @Transactional
    public void createAndSaveDiaryEntry(CheckinForm checkinForm, List<MultipartFile> photos) throws Exception {

        int userId = 1;

        DiaryEntry diaryEntry = new DiaryEntry(userId, new Date());

        // Mood
        if (checkinForm.getMood() != null && !checkinForm.getMood().isEmpty()) {
            diaryEntry.setMood(DiaryMood.valueOf(checkinForm.getMood()));
        }

        diaryEntry.setNotes(checkinForm.getNotes());

        // Photos
        if (photos != null && !photos.isEmpty()) {
            Set<DiaryPhoto> diaryPhotos = new HashSet<>();
            for (MultipartFile photo : photos) {
                String photoUrl = fileStorageService.storeFile(photo);
                Photo photoEntity = new Photo(photoUrl, new Date(), "", userId);
                photoRepository.save(photoEntity);
                DiaryPhoto diaryPhoto = new DiaryPhoto();
                diaryPhoto.setPhoto(photoEntity);
                diaryPhoto.setDiaryEntry(diaryEntry);
                diaryPhotos.add(diaryPhoto);
            }
            diaryEntry.setPhotos(diaryPhotos);
        }

        // Symptoms
        Set<DiarySymptom> diarySymptoms = new HashSet<>();
        if (checkinForm.getTroubleSleepingSeverity() != null) {
            Symptom symptom = new Symptom("Trouble sleeping", checkinForm.getTroubleSleepingSeverity(), new Date(), true, userId);
            symptomRepository.save(symptom);
            DiarySymptom diarySymptom = new DiarySymptom();
            diarySymptom.setSymptom(symptom);
            diarySymptom.setDiaryEntry(diaryEntry);
            diarySymptoms.add(diarySymptom);
        }
        if (checkinForm.getPainSeverity() != null) {
            Symptom symptom = new Symptom("Pain", checkinForm.getPainSeverity(), new Date(), true, userId);
            symptomRepository.save(symptom);
            DiarySymptom diarySymptom = new DiarySymptom();
            diarySymptom.setSymptom(symptom);
            diarySymptom.setDiaryEntry(diaryEntry);
            diarySymptoms.add(diarySymptom);
        }
        if (checkinForm.getNumbnessSeverity() != null) {
            Symptom symptom = new Symptom("Numbness", checkinForm.getNumbnessSeverity(), new Date(), true, userId);
            symptomRepository.save(symptom);
            DiarySymptom diarySymptom = new DiarySymptom();
            diarySymptom.setSymptom(symptom);
            diarySymptom.setDiaryEntry(diaryEntry);
            diarySymptoms.add(diarySymptom);
        }
        diaryEntry.setSymptoms(diarySymptoms);

        diaryEntryRepository.save(diaryEntry);
    }
}
