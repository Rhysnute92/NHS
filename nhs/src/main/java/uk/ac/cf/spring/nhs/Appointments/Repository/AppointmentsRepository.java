package uk.ac.cf.spring.nhs.Appointments.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Appointments.Model.Appointments;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
}
