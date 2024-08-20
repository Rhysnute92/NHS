package uk.ac.cf.spring.nhs.Provider.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;

@Controller
@SessionAttributes("userID")
@RequestMapping("/patientprofile")
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Patient", "/patientprofile/info", "fa-solid fa-user-check"),
                new NavMenuItem("Set plan", "", "fa-solid fa-book"),
                new NavMenuItem("Appointments", " ", "fa-solid fa-user-check"),
                new NavMenuItem("Questionnaires", "/patientprofile/questionnairehub",
                        "fa-solid fa-book"),
                new NavMenuItem("Patient trends", " ", "fa-solid fa-user-check"),
                new NavMenuItem("Event log", " ", "fa-solid fa-book"),
                new NavMenuItem("Photos", " ", "fa-solid fa-camera"),
                new NavMenuItem("Email history", " ", "fa-solid fa-book"));
    }

    @ModelAttribute("userID")
    public Long profileUser(){
        Long userId = 0L;
        return userId;
    }

    @GetMapping("/{id}")
    public String openProfile(@PathVariable("id") int id, Map<String, Object> model) {
        Long userID = Long.valueOf(id);
        model.put("userID", userID);
        return "redirect:/patientprofile/info";
    } 

    @GetMapping("/info")
    public String patientProfileAdmin() {
        return "patientprofile/profileInfo";
    }

    @GetMapping("/questionnairehub")
    public String showQuestionnaireHub(Model model) {
        return "patientprofile/questionnaireHub";
    }

}
