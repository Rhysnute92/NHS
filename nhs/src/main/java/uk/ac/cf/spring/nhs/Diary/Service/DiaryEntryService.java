package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Diary.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.*;
import uk.ac.cf.spring.nhs.Diary.Repository.*;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;

import java.util.*;

@Service
public class DiaryEntryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final PhotoRepository photoRepository;
    private final SymptomRepository symptomRepository;
    private final MeasurementRepository measurementRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public DiaryEntryService(DiaryEntryRepository diaryEntryRepository,
                             PhotoRepository photoRepository,
                             SymptomRepository symptomRepository,
                             MeasurementRepository measurementRepository,
                             FileStorageService fileStorageService) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.photoRepository = photoRepository;
        this.symptomRepository = symptomRepository;
        this.measurementRepository = measurementRepository;
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
        return diaryEntryRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
    }

    @Transactional
    public void deleteDiaryEntryById(int id) {
        diaryEntryRepository.deleteById(id);
    }

    @Transactional
    public void createAndSaveDiaryEntry(CheckinForm checkinForm) throws Exception {

        long userId = 1L;

        DiaryEntry diaryEntry = new DiaryEntry(userId, new Date());

        // Mood
        if (checkinForm.getMood() != null && !checkinForm.getMood().isEmpty()) {
            diaryEntry.setMood(DiaryMood.valueOf(checkinForm.getMood()));
        }

        // Notes
        if (checkinForm.getNotes() != null && !checkinForm.getNotes().isEmpty()) {
            diaryEntry.setNotes(checkinForm.getNotes());
        }

        // Photos
        List<MultipartFile> photos = checkinForm.getPhotos();
        if (photos != null && !photos.isEmpty()) {
            Set<DiaryPhoto> diaryPhotos = new HashSet<>();
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String photoUrl = fileStorageService.storeFile(photo);
                    Photo photoEntity = new Photo(photoUrl, new Date(), "", userId);
                    photoRepository.save(photoEntity);
                    DiaryPhoto diaryPhoto = new DiaryPhoto();
                    diaryPhoto.setPhoto(photoEntity);
                    diaryPhoto.setDiaryEntry(diaryEntry);
                    diaryPhotos.add(diaryPhoto);
                }
            }
            diaryEntry.setPhotos(diaryPhotos);
        }

        // Symptoms
        if (checkinForm.getSymptoms() != null && !checkinForm.getSymptoms().isEmpty()) {
            Set<DiarySymptom> diarySymptoms = new HashSet<>();
            for (SymptomDTO symptomDTO : checkinForm.getSymptoms()) {
                if (symptomDTO.getName() != null && symptomDTO.getSeverity() != null) { // Check if name and severity are not null
                    Symptom symptom = new Symptom(symptomDTO.getName(), symptomDTO.getSeverity(), new Date(), true, userId);
                    symptomRepository.save(symptom);
                    DiarySymptom diarySymptom = new DiarySymptom();
                    diarySymptom.setSymptom(symptom);
                    diarySymptom.setDiaryEntry(diaryEntry);
                    diarySymptoms.add(diarySymptom);
                }
            }
            diaryEntry.setSymptoms(diarySymptoms);
        }

        // Measurements
        if (checkinForm.getMeasurements() != null && !checkinForm.getMeasurements().isEmpty()) {
            Set<DiaryMeasurement> diaryMeasurements = new HashSet<>();
            for (MeasurementDTO measurementDTO : checkinForm.getMeasurements()) {
                if (measurementDTO.getType() != null && measurementDTO.getValue() != null && measurementDTO.getUnit() != null) { // Check if type, value, and unit are not null
                    Measurement measurement = new Measurement(measurementDTO.getType(), measurementDTO.getValue(), measurementDTO.getUnit(), userId);
                    measurementRepository.save(measurement);
                    DiaryMeasurement diaryMeasurement = new DiaryMeasurement();
                    diaryMeasurement.setMeasurement(measurement);
                    diaryMeasurement.setDiaryEntry(diaryEntry);
                    diaryMeasurements.add(diaryMeasurement);
                }
            }
            diaryEntry.setMeasurements(diaryMeasurements);
        }

        diaryEntryRepository.save(diaryEntry);
    }
}
