package uk.ac.cf.spring.nhs.Symptom.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Symptom.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Repository.SymptomRepository;

import java.util.Set;

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
                userId
        );
        return symptomRepository.save(symptom);
    }

    public void saveSymptoms(Set<Symptom> symptoms) {
        symptomRepository.saveAll(symptoms);
    }
}
