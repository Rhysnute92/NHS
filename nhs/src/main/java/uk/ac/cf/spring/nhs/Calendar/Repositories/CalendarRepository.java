package uk.ac.cf.spring.nhs.Calendar.Repositories;

import java.util.List;
public interface CalendarRepository {
    List<Calendar> getAllAppointments();

    void addAppointment(Calendar cal);}
