package uk.ac.cf.spring.nhs.Treatment.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;
import uk.ac.cf.spring.nhs.Treatment.DTO.TreatmentDTO;
import uk.ac.cf.spring.nhs.Treatment.Repository.TreatmentRepository;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @Transactional
    public Treatment saveTreatment(TreatmentDTO treatmentDTO, long userId) {
        Treatment treatment = new Treatment(
                treatmentDTO.getType(),
                treatmentDTO.getDetails(),
                userId
        );

        return treatmentRepository.save(treatment);
    }

    @Transactional
    public Treatment saveTreatment(TreatmentDTO treatmentDTO, long userId, Long relatedEntityId, String relatedEntityType) {
        Treatment treatment = new Treatment(
                treatmentDTO.getType(),
                treatmentDTO.getDetails(),
                userId,
                relatedEntityId,
                relatedEntityType
        );

        return treatmentRepository.save(treatment);
    }
}
