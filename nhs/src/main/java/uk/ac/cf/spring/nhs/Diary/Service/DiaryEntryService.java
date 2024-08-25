package uk.ac.cf.spring.nhs.Diary.Service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Mood;
import uk.ac.cf.spring.nhs.Diary.Repository.DiaryEntryRepository;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoDTO;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Symptom.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryEntryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final PhotoService photoService;
    private final SymptomService symptomService;
    private final MeasurementService measurementService;
    private final FileStorageService fileStorageService;

    public DiaryEntryService(DiaryEntryRepository diaryEntryRepository,
                             PhotoService photoService,
                             SymptomService symptomService,
                             MeasurementService measurementService,
                             FileStorageService fileStorageService) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.photoService = photoService;
        this.symptomService = symptomService;
        this.measurementService = measurementService;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public DiaryEntry saveDiaryEntry(CheckinFormDTO checkinForm, Long userId) throws Exception {
        // Create the diary entry
        DiaryEntry diaryEntry = createDiaryEntry(userId, checkinForm.getMood(), checkinForm.getNotes());
        DiaryEntry savedDiaryEntry = diaryEntryRepository.save(diaryEntry);

        // Associate related entities with the saved diary entry
        List<Photo> photos = processPhotos(checkinForm.getPhotos(), userId, savedDiaryEntry);
        List<Symptom> symptoms = processSymptoms(checkinForm.getSymptoms(), userId, savedDiaryEntry);
        List<Measurement> measurements = processMeasurements(checkinForm.getMeasurements(), userId, savedDiaryEntry);

        // Batch save the related entities
        if (!photos.isEmpty()) {
            photoService.saveAll(photos);
        }

        if (!symptoms.isEmpty()) {
            symptomService.saveAll(symptoms);
        }

        if (!measurements.isEmpty()) {
            measurementService.saveAll(measurements);
        }

        return savedDiaryEntry;
    }

    private DiaryEntry createDiaryEntry(Long userId, String mood, String notes) {
        DiaryEntry diaryEntry = new DiaryEntry(userId, LocalDate.now());
        diaryEntry.setMood(mood != null ? Mood.valueOf(mood) : null);
        diaryEntry.setNotes(notes);
        return diaryEntry;
    }

    private List<Photo> processPhotos(List<PhotoDTO> photoDTOs, Long userId, DiaryEntry diaryEntry) {
        return photoDTOs.stream()
                .map(photoDTO -> {
                    String photoUrl = fileStorageService.storeFile(photoDTO.getFile());
                    String bodyPart = photoDTO.getBodyPart();
                    Photo photo = new Photo(photoUrl, bodyPart, userId, LocalDate.now());
                    photo.setDiaryEntry(diaryEntry);
                    return photo;
                })
                .collect(Collectors.toList());
    }

    private List<Symptom> processSymptoms(List<SymptomDTO> symptomDTOs, Long userId, DiaryEntry diaryEntry) {
        return symptomDTOs.stream()
                .filter(symptomDTO -> symptomDTO.getSeverity() != null)
                .map(symptomDTO -> {
                    Symptom symptom = new Symptom(symptomDTO.getName(), symptomDTO.getSeverity(), userId, LocalDate.now());
                    symptom.setDiaryEntry(diaryEntry);
                    return symptom;
                })
                .collect(Collectors.toList());
    }

    private List<Measurement> processMeasurements(List<MeasurementDTO> measurementDTOs, Long userId, DiaryEntry diaryEntry) {
        return measurementDTOs.stream()
                .map(measurementDTO -> {
                    Measurement measurement = new Measurement(
                            measurementDTO.getType(),
                            measurementDTO.getValue(),
                            measurementDTO.getUnit(),
                            userId,
                            LocalDate.now(),
                            measurementDTO.getLocation(),
                            measurementDTO.getSide()
                    );
                    measurement.setDiaryEntry(diaryEntry);
                    return measurement;
                })
                .collect(Collectors.toList());
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

    public List<DiaryEntry> getDiaryEntriesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return diaryEntryRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
}
