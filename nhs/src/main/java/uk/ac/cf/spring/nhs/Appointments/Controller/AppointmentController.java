package uk.ac.cf.spring.nhs.Appointments.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import uk.ac.cf.spring.nhs.Appointments.DTO.AppointmentDTO;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAppointmentsByUserId(1L);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Integer id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentDTO,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUserId();
        Appointment savedAppointment = appointmentService.saveAppointment(appointmentDTO, userId);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
    }
}
