package uk.ac.cf.spring.nhs.Appointments.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uk.ac.cf.spring.nhs.Appointments.DTO.AppointmentDTO;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    //TODO: also url paths here should be changed to avoid conflicts 
    @GetMapping // change to something like @GetMapping("/user/{userId}") then put userId in the pathvariable like in getAppointmentById
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAppointmentsByUserId(1); //TODO: userID is 2 now
    }

    /**
     * Retrieves a list of appointments for a given specific user.
     *
     * @return a ResponseEntity containing a list of appointments by ID
     */

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Integer id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Appointment savedAppointment = appointmentService.saveAppointment(appointmentDTO);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
    }
}

