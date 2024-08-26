package uk.ac.cf.spring.nhs.Measurement.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeasurementGroupDTO {
    private String type;
    private List<MeasurementLocationDTO> locations;

}
