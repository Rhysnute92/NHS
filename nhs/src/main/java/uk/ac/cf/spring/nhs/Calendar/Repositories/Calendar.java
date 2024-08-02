package uk.ac.cf.spring.nhs.Calendar.Repositories;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
                ApptProvider + '\'' +
                '}';
    }

    public String getAppointmentTime() {
        return ApptTime;
    }
    public String getAppointmentProvider() {
        return ApptProvider;
    }

    public String getApptType() {
        return ApptType;
    }

    public String getApptInfo() {
        return ApptInfo;
    }



}
