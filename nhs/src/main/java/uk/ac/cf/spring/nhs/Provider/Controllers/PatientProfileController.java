package uk.ac.cf.spring.nhs.Provider.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import uk.ac.cf.spring.nhs.Account.PatientProfileDTO;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Event.Service.EventService;
import uk.ac.cf.spring.nhs.Diary.DTO.MoodDTO;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;

@Controller
@SessionAttributes("userID")
@RequestMapping("/patientprofile")
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private DiaryEntryService diaryEntryService;

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
                new NavMenuItem("Patient trends", "/patientprofile/trends", "fa-solid fa-user-check"),
                new NavMenuItem("Photos", "/patientprofile/photos", "fa-solid fa-camera"),
                new NavMenuItem("Event log", "/patientprofile/events", "fa-solid fa-receipt"));
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

    @GetMapping("/trends")
    public String patientTrends(@ModelAttribute("userID") Long userId, Model model) {
        // Fetch reported measurement types and symptom types for the user
        List<String> measurementTypes = measurementService.findDistinctMeasurementLocationsByUserId(userId);
        List<String> symptomTypes = symptomService.findDistinctSymptomTypesByUserId(userId);

        // Add them to the model
        model.addAttribute("measurementTypes", measurementTypes);
        model.addAttribute("symptomTypes", symptomTypes);

        // Return the view name
        return "patientprofile/patientTrends";
    }

    @GetMapping("/trends/measurements")
    public ResponseEntity<?> patientMeasurements(
            @ModelAttribute("userID") Long userId,
            @RequestParam(value = "type", required = false, defaultValue = "defaultType") String measurementType,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        List<Measurement> measurements = measurementService.getMeasurementsByUserIdTypeAndDateRange(userId, measurementType, startDate, endDate);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/trends/symptoms")
    public ResponseEntity<?> patientSymptoms(
            @ModelAttribute("userID") Long userId,
            @RequestParam(value = "type", required = false, defaultValue = "defaultType") String symptomType,  // Default value
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        System.out.println("Symptom type: " + symptomType);
        List<Symptom> symptoms = symptomService.getSymptomsByUserIdTypeAndDateRange(userId, symptomType, startDate, endDate);
        return ResponseEntity.ok(symptoms);
    }

    @GetMapping("/trends/mood")
    public ResponseEntity<?> patientMood(
            @ModelAttribute("userID") Long userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        List<MoodDTO> moods = diaryEntryService.getMoodByUserIdAndDateRange(userId, startDate, endDate);

        return ResponseEntity.ok(moods);
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
        // Add ObjectMapper to model so Thymeleaf can use it to convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
