package uk.ac.cf.spring.nhs.Provider.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;


@Controller
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
            new NavMenuItem("Patient", "/patientprofile", "fa-solid fa-user-check"),
            new NavMenuItem("Set plan", "", "fa-solid fa-book"),
            new NavMenuItem("Appointments", " ", "fa-solid fa-user-check"),
            new NavMenuItem("Questionnaires", " ", "fa-solid fa-book"),
            new NavMenuItem("Patient trends", " ", "fa-solid fa-user-check"),
            new NavMenuItem("Event log", " ", "fa-solid fa-book"),
            new NavMenuItem("Photos", " ", "fa-solid fa-camera"),
            new NavMenuItem("Email history", " ", "fa-solid fa-book")
        );
    }
    
}
