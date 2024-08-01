package uk.ac.cf.spring.nhs.Calendar.Repositories;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Calendar {

    private String ApptTime;
    private String ApptType;
    private String ApptProvider;
    private String ApptInfo;

    @Override
    public String toString() {
        return "Calendar{" +
                ApptTime + '\'' +
                ApptType + '\'' +
                ApptProvider + '\'' +
                ApptInfo + '\'' +
                '}';
    }

    public String getAppointmentTime() {
        return ApptTime;
    }
    public String getAppointmentType() {
        return ApptType;
    }
    public String getAppointmentProvider() {
        return ApptProvider;
    }
    public String getAppointmentInfo() {
        return ApptInfo;
    }
}
