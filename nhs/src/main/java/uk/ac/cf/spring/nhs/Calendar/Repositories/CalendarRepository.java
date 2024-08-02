package uk.ac.cf.spring.nhs.Calendar.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
public interface CalendarRepository  {
    List<Calendar> getAllAppointments();

    void addAppointment(Calendar cal);}
