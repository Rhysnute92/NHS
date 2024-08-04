package uk.ac.cf.spring.nhs.Appointments.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Appointments.Model.Appointments;
import uk.ac.cf.spring.nhs.Appointments.Repository.AppointmentsRepository;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentsRepository appointmentRepository;

    public List<Appointments> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointments getAppointmentById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointments saveAppointment(Appointments appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }
}
