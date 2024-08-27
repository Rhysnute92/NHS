package uk.ac.cf.spring.nhs.Symptom.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Symptom.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Repository.SymptomRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    @Transactional
    public Symptom saveSymptom(SymptomDTO symptomDTO, long userId) {
        Symptom symptom = new Symptom(
                symptomDTO.getName(),
                symptomDTO.getSeverity(),
                userId,
                LocalDate.now()
        );
        return symptomRepository.save(symptom);
    }

    public void saveAll(List<Symptom> symptoms) {
        symptomRepository.saveAll(symptoms);
    }

    public List<Symptom> getSymptomsByUserIdTypeAndDateRange(Long userId, String symptomName, LocalDate startDate, LocalDate endDate) {
        // If startDate or endDate are null, default to the earliest/latest possible dates
        if (startDate == null) {
            startDate = LocalDate.of(1900, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        return symptomRepository.findByUserIdAndNameAndDateBetween(userId, symptomName, startDate, endDate);
    }

    public List<String> findDistinctSymptomTypesByUserId(Long userId) {
        return symptomRepository.findDistinctNamesByUserId(userId);
    }
}
