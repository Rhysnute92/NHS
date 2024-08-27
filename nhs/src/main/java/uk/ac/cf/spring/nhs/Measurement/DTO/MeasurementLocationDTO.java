package uk.ac.cf.spring.nhs.Measurement.DTO;

import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;

@Getter
@Setter
public class MeasurementLocationDTO {
    private String location;
    private Measurement leftMeasurement;
    private Measurement rightMeasurement;

    public MeasurementLocationDTO(String location, Measurement leftMeasurement, Measurement rightMeasurement) {
        this.location = location;
        this.leftMeasurement = leftMeasurement;
        this.rightMeasurement = rightMeasurement;
    }
}