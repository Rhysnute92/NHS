package uk.ac.cf.spring.nhs.Provider.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import uk.ac.cf.spring.nhs.AddPatient.Service.PatientService;


@Controller
public class PatientProfileController {

    @Autowired
    private PatientService patientService;
    
}
