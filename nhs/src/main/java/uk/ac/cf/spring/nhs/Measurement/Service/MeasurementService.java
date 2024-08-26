package uk.ac.cf.spring.nhs.Measurement.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Repository.MeasurementRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getMeasurementsByUserId(long userId) {
        return measurementRepository.findByUserId(userId);
    }

    @Transactional
    public Measurement saveMeasurement(MeasurementDTO measurementDTO, long userId) {
        Measurement measurement = new Measurement(
                measurementDTO.getType(),
                measurementDTO.getValue(),
                measurementDTO.getUnit(),
                userId,
                LocalDate.now(),
                measurementDTO.getLocation(),
                measurementDTO.getSide()
        );

        return measurementRepository.save(measurement);
    }

    public void saveAll(List<Measurement> measurements) {
        measurementRepository.saveAll(measurements);
    }

    // Fetch measurements by user ID, type, and date range
    public List<Measurement> getMeasurementsByUserIdTypeAndDateRange(long userId, String measurementType, LocalDate startDate, LocalDate endDate) {
        // If startDate or endDate are null, default to the earliest/latest possible dates
        if (startDate == null) {
            startDate = LocalDate.of(1900, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        return measurementRepository.findByUserIdAndTypeAndDateBetween(userId, measurementType, startDate, endDate);
    }


    public List<String> findDistinctMeasurementLocationsByUserId(Long userId) {
        return measurementRepository.findDistinctLocationsByUserId(userId);
    }
}
