package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.*;
import uk.ac.cf.spring.nhs.Diary.Repository.*;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiaryEntryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final PhotoService photoService;
    private final SymptomService symptomService;
    private final MeasurementService measurementService;

    public DiaryEntryService(DiaryEntryRepository diaryEntryRepository,
                             PhotoService photoService,
                             SymptomService symptomService,
                             MeasurementService measurementService) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.photoService = photoService;
        this.symptomService = symptomService;
        this.measurementService = measurementService;
    }

    @Transactional
    public DiaryEntry createAndSaveDiaryEntry(CheckinFormDTO checkinForm, Long userId) throws Exception {
        DiaryEntry diaryEntry = new DiaryEntry(userId, new Date());

        // Mood
        diaryEntry.setMood(checkinForm.getMood() != null ? Mood.valueOf(checkinForm.getMood()) : null);

        // Notes
        diaryEntry.setNotes(checkinForm.getNotes());

        // Photos
        if (!checkinForm.getPhotos().isEmpty()) {
            Set<Photo> photos = checkinForm.getPhotos().stream()
                    .map(photoDTO -> photoService.savePhoto(photoDTO, userId))
                    .collect(Collectors.toSet());
            diaryEntry.setPhotos(photos);
        }

        // Symptoms
        if (!checkinForm.getSymptoms().isEmpty()) {
            Set<Symptom> symptoms = checkinForm.getSymptoms().stream()
                    .filter(symptomDTO -> symptomDTO.getSeverity() != null)
                    .map(symptomDTO -> symptomService.saveSymptom(symptomDTO, userId))
                    .collect(Collectors.toSet());
            diaryEntry.setSymptoms(symptoms);
        }

        // Measurements
        if (checkinForm.getMeasurements() != null && !checkinForm.getMeasurements().isEmpty()) {
            Set<Measurement> measurements = checkinForm.getMeasurements().stream()
                    .map(measurementDTO -> measurementService.saveMeasurement(measurementDTO, userId))
                    .collect(Collectors.toSet());
            diaryEntry.setMeasurements(measurements);
        }

        return diaryEntryRepository.save(diaryEntry);
    }

    public void deleteDiaryEntryById(int id) {
        diaryEntryRepository.deleteById(id);
    }

    public DiaryEntry getDiaryEntryById(int id) {
        return diaryEntryRepository.findById(id).orElse(null);
    }

    public List<DiaryEntry> getAllDiaryEntries() {
        return diaryEntryRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<DiaryEntry> getDiaryEntriesByUserId(long userId) {
        return diaryEntryRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
    }
}