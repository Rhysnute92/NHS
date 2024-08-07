package uk.ac.cf.spring.nhs.Diary.DTO;

public class MeasurementDTO {
    private String type;
    private Float value;
    private String unit;

    public MeasurementDTO() {
    }

    public MeasurementDTO(String type, Float value, String unit) {
        this.type = type;
        this.value = value;
        this.unit = unit;
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

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "type='" + type + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}
