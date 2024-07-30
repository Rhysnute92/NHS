package uk.ac.cf.spring.nhs.Calendar.Repositories;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Calendar {

    private String appt_date;
    private String appt_time;
    private String appt_type;
    private String appt_provider;
    private String appt_info;

    @Override
    public String toString() {
        return "Calendar{" +
                appt_date + '\'' +
                appt_time + '\'' +
                appt_type + '\'' +
                appt_provider + '\'' +
                appt_info + '\'' +
                '}';
    }

    public String getAppointmentDate() {return appt_date;}
    public String getAppointmentTime() {return appt_time;}
    public String getAppointmentType() {return appt_type;}
    public String getAppointmentProvider() {return appt_provider;}
    public String getAppointmentInfo() {return appt_info;}
}
