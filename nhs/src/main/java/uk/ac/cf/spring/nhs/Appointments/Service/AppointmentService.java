package uk.ac.cf.spring.nhs.Appointments.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Appointments.DTO.AppointmentDTO;
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

    public List<Appointment> getAppointmentsByUserId(Integer userId) {
        return appointmentRepository.findByUserID(userId);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment saveAppointment(AppointmentDTO appointmentDTO) {
        // Combine date and time into a single LocalDateTime object
        LocalDate date = LocalDate.parse(appointmentDTO.getDate());
        LocalTime time = LocalTime.parse(appointmentDTO.getTime());
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        Appointment appointment = new Appointment();
        appointment.setApptDateTime(dateTime);
        appointment.setApptType(appointmentDTO.getAppointmentType());
        appointment.setApptProvider(appointmentDTO.getProvider());
        appointment.setApptInfo(appointmentDTO.getDescription());

        appointment.setUserID(1L); // Hardcoded for now

        return appointmentRepository.save(appointment);
    }

    public List<AppointmentDTO> getAppointmentsForDate(Long userId, LocalDate date) {
        return null;
    }
}
