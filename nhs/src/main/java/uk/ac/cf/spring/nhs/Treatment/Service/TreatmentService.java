package uk.ac.cf.spring.nhs.Treatment.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;
import uk.ac.cf.spring.nhs.Treatment.DTO.TreatmentDTO;
import uk.ac.cf.spring.nhs.Treatment.Repository.TreatmentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
                userId,
                LocalDate.now()
        );

        return treatmentRepository.save(treatment);
    }

    public void saveAll(List<Treatment> treatments) {
        treatmentRepository.saveAll(treatments);
    }

}
