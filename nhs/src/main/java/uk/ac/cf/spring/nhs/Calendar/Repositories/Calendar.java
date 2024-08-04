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

    public Calendar(String appointmentTime, String appointmentProvider) {
    }

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
    public String getAppointmentProvider() {

        return ApptProvider;
    }

    public String getAppointmentType() {

        return ApptType;
    }

    public String getAppointmentInfo() {
        return ApptInfo;
    }



}
