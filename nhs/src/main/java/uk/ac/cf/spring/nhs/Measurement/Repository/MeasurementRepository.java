package uk.ac.cf.spring.nhs.Measurement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;

import java.time.LocalDate;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findByUserId(long userId);

    List<Measurement> findByUserIdAndTypeAndDateBetween(long userId, String measurementType, LocalDate startDate, LocalDate endDate);

    @Query("SELECT DISTINCT m.location FROM Measurement m WHERE m.userId = :userId")
    List<String> findDistinctLocationsByUserId(@Param("userId") Long userId);
}
