package uk.ac.cf.spring.nhs.Measurement.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class MeasurementDTO {
    private String type;
    private Float value;
    private String unit;
    private String location;
    private String side;
    private LocalDate date;

    public MeasurementDTO() {
    }

    public MeasurementDTO(String type, Float value, String unit, String location, String side, LocalDate date) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.location = location;
        this.side = side;
        this.date = date;
    }
    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "type='" + type + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", location='" + location + '\'' +
                ", side='" + side + '\'' +
                '}';
    }

}
