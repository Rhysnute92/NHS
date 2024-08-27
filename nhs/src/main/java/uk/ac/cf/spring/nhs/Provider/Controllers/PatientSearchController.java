package uk.ac.cf.spring.nhs.Provider.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;
import uk.ac.cf.spring.nhs.Provider.DTOs.SearchRequest;


@Controller
@RequestMapping("/provider")
public class PatientSearchController {
    
    @Autowired
    private PatientService patientService;

    @GetMapping("/search")
    public String patientProfileAdmin() {return "admin/desktop/search";}

    @PostMapping("/search/patientsearch")
    public String nhsSearch(@ModelAttribute SearchRequest request, RedirectAttributes redirect){
        if (request.getPatientNhsNumber() != null){
            Patient result = patientService.findPatientbyNHSNumber(request.getPatientNhsNumber());
            if(result != null){
                result = patientService.decryptPatient(result);
                redirect.addFlashAttribute("results", result);
            } else {
                String error = "Patient not found.";
                redirect.addFlashAttribute("error", error);
            }
        } else{
            List<Patient> result = patientService.patientGeneralSearch(request);
            if(result != null){
                for (Patient p : result){
                    p = patientService.decryptPatient(p);
                }
                redirect.addFlashAttribute("results", result);
            } else {
                String error = "No patients found matching search criteria";
                redirect.addFlashAttribute("error", error);
            }
        }
        return "redirect:/provider/search";
    }
    
}
