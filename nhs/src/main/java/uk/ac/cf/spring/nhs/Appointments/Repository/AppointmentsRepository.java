package uk.ac.cf.spring.nhs.Appointments.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Appointments.Model.Appointments;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
    List<Appointments> findByCalendar_CalendarID(Integer calendarID);
}
