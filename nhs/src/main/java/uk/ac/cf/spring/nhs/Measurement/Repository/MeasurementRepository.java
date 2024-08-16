package uk.ac.cf.spring.nhs.Measurement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
