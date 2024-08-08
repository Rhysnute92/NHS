package uk.ac.cf.spring.nhs.Appointments.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }

}
