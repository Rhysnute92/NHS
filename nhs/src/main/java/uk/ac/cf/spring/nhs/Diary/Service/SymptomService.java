package uk.ac.cf.spring.nhs.Diary.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Diary.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.Symptom;
import uk.ac.cf.spring.nhs.Diary.Repository.SymptomRepository;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    @Transactional
    public Symptom saveSymptom(SymptomDTO symptomDTO, long userId) {
        Symptom symptom = new Symptom();
        symptom.setName(symptomDTO.getName());
        symptom.setSeverity(symptomDTO.getSeverity());

        symptom.setUserId(userId);
        return symptomRepository.save(symptom);
    }
}
