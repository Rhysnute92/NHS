package uk.ac.cf.spring.nhs.Measurement.DTO;

import java.time.LocalDate;

public class MeasurementDTO {
    private String type;
    private Float value;
    private String unit;
    private String location;
    private String side;

    public MeasurementDTO() {
    }

    public MeasurementDTO(String type, Float value, String unit, String location, String side) {
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.location = location;
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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
