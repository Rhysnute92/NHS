package uk.ac.cf.spring.nhs.Provider.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;


@Controller
public class PatientSearchController {
    
    @Autowired
    private PatientService patientService;
    
}
