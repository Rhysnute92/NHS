package uk.ac.cf.spring.nhs.Diary.Service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Mood;
import uk.ac.cf.spring.nhs.Diary.Repository.DiaryEntryRepository;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;

import java.util.Date;
import java.util.List;
import java.util.Set;
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
    public DiaryEntry saveDiaryEntry(CheckinFormDTO checkinForm, Long userId) throws Exception {
        // Create the diary entry
        DiaryEntry diaryEntry = new DiaryEntry(userId, new Date());

        // Set mood and notes
        diaryEntry.setMood(checkinForm.getMood() != null ? Mood.valueOf(checkinForm.getMood()) : null);
        diaryEntry.setNotes(checkinForm.getNotes());

        // Save the diary entry first to generate the ID
        diaryEntry = diaryEntryRepository.save(diaryEntry);
        long diaryEntryId = diaryEntry.getId();

        // Photos
        if (!checkinForm.getPhotos().isEmpty()) {
            Set<Photo> photos = checkinForm.getPhotos().stream()
                    .map(photoDTO -> photoService.savePhoto(photoDTO, userId, diaryEntryId, "DiaryEntry"))
                    .collect(Collectors.toSet());
            diaryEntry.setPhotos(photos);
        }

        // Symptoms
        if (!checkinForm.getSymptoms().isEmpty()) {
            Set<Symptom> symptoms = checkinForm.getSymptoms().stream()
                    .filter(symptomDTO -> symptomDTO.getSeverity() != null)
                    .map(symptomDTO -> symptomService.saveSymptom(symptomDTO, userId, diaryEntryId, "DiaryEntry"))
                    .collect(Collectors.toSet());
            diaryEntry.setSymptoms(symptoms);
        }

        // Measurements
        if (checkinForm.getMeasurements() != null && !checkinForm.getMeasurements().isEmpty()) {
            Set<Measurement> measurements = checkinForm.getMeasurements().stream()
                    .map(measurementDTO -> measurementService.saveMeasurement(measurementDTO, userId, diaryEntryId, "DiaryEntry"))
                    .collect(Collectors.toSet());
            diaryEntry.setMeasurements(measurements);
        }

        // Save the diary entry again with related entities attached
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
