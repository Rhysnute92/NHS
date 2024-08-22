package uk.ac.cf.spring.nhs.Provider.Controllers;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import uk.ac.cf.spring.nhs.Account.PatientProfileDTO;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Event.Service.EventService;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;

@Controller
@SessionAttributes("userID")
@RequestMapping("/patientprofile")
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EventService eventService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Patient", "/patientprofile/info", "fa-solid fa-user-check"),
                new NavMenuItem("Set plan", "/patientprofile/plan", "fa-solid fa-book"),
                new NavMenuItem("Questionnaires", "/patientprofile/questionnairehub",
                        "fa-solid fa-book"),
                new NavMenuItem("Photos", "/patientprofile/photos", "fa-solid fa-camera"),
                new NavMenuItem("Events", "/patientprofile/events", "fa-solid fa-receipt"));
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
    public String patientProfileAdmin(@ModelAttribute("userID") Long userID, Model model) {
        PatientProfileDTO profile = patientService.profile(userID);
        model.addAttribute("patient", profile);
        return "patientprofile/profileInfo";
    }

    @GetMapping("/questionnairehub")
    public String showQuestionnaireHub(Model model) {
        return "patientprofile/questionnaireHub";
    }

    @GetMapping("/photos")
    public String showPhoto(Model model, @ModelAttribute("userID") Long userID) {
        model.addAttribute("objectMapper", objectMapper);
        model.addAttribute("photos", photoService.getPhotosByUserId(userID));
        return "patientprofile/photos";
    }

    @GetMapping("/events")
    public String showEvents(Model model, @ModelAttribute("userID") Long userID) {
        model.addAttribute("objectMapper", objectMapper);
        model.addAttribute("events", eventService.getEventsByUserId(userID));
        return "patientprofile/events";
    }

    @GetMapping("/plan")
    public String treatmentPlanPage(){
        return "patientprofile/treatmentPlan";
    }

    @GetMapping("/setplan")
    public String treatmentPlanSet(){
        return "patientprofile/setPlan";
    }

}
