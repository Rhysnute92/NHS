package uk.ac.cf.spring.nhs.Calendar.Repositories;

import java.util.List;
public interface CalendarRepository {
    List<Calendar> getAllAppointments();
    void addAppointment(Calendar calendar);

    void addAppointment(String testAppt_Date, String testAppt_Time, String testAppt_Type, String testAppt_Provider, String testAppt_Info);
}
