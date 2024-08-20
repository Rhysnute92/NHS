package uk.ac.cf.spring.nhs.Measurement.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Repository.MeasurementRepository;

import java.util.List;
import java.util.Set;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Transactional
    public Measurement saveMeasurement(MeasurementDTO measurementDTO, long userId) {
        Measurement measurement = new Measurement(
                measurementDTO.getType(),
                measurementDTO.getValue(),
                measurementDTO.getUnit(),
                userId
        );

        return measurementRepository.save(measurement);
    }

    public void saveAll(List<Measurement> measurements) {
        measurementRepository.saveAll(measurements);
    }
}
