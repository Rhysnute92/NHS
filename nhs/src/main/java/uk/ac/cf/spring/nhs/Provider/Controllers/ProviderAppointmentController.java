package uk.ac.cf.spring.nhs.Provider.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Repository.PatientRepository;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/provider-appointments")
public class ProviderAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientRepository patientRepository;

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Patient", "/patientprofile/info", "fa-solid fa-user-check"),
                new NavMenuItem("Set plan", "", "fa-solid fa-book"),
                new NavMenuItem("Appointments", "/providerCalendar",
                        "fa-solid fa-user-check"),
                new NavMenuItem("Questionnaires", "/questionnairehub",
                        "fa-solid fa-book"),
                new NavMenuItem("Patient trends", " ", "fa-solid fa-user-check"),
                new NavMenuItem("Event log", " ", "fa-solid fa-book"),
                new NavMenuItem("Photos", " ", "fa-solid fa-camera"),
                new NavMenuItem("Email history", " ", "fa-solid fa-book"));
    }
    @PostMapping("/patients/{id}/add-lymphoedema-appointment")
    public String addLymphoedemaAppointment(@PathVariable("id") Long patientId,
                                            @RequestParam("date") String date,
                                            @RequestParam("time") String time,
                                            @RequestParam("location") String location) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient != null) {
            LocalDate localDate = LocalDate.parse(date);
            LocalTime localTime = LocalTime.parse(time);

            LocalDateTime apptDateTime = LocalDateTime.of(localDate, localTime);

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setApptDateTime(apptDateTime);
            appointment.setApptType("Lymphoedema appointment");
            appointment.setApptLocation(location);
            appointment.setIsDeletable(false);
            appointmentService.saveAppointment(appointment);
        }
        return "redirect:/patients/" + patientId + "/appointments";
    }

    @PostMapping("/patients/{patientId}/appointments/{appointmentsId}/delete")
    public String deleteAppointment(@PathVariable("patientId") Long patientId,
                                    @PathVariable("appointmentId") Long appointmentsId) {
        Appointment appointment = appointmentService.findById(appointmentsId).orElse(null);
        if (appointment != null && appointment.getPatient().getUserId().equals(patientId) && appointment.getIsDeletable()){
            appointmentService.deleteAppointment(3);
        }
        return "redirect:/patients/" + patientId + "/appointments";
    }

    @GetMapping
    public String showProviderAddAppointment() {
        return ("patientprofile/appointmentFormProvider");
    }
}
