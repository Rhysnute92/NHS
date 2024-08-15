package uk.ac.cf.spring.nhs.Provider.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;


@Controller
@RequestMapping("/provider")
public class PatientSearchController {
    
    @Autowired
    private PatientService patientService;

    @GetMapping("/search")
    public String patientProfileAdmin() {return "admin/desktop/search";}
    
}
