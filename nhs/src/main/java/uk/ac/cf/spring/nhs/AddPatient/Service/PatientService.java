package uk.ac.cf.spring.nhs.AddPatient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.AddPatient.DTO.RegisterRequest;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Repository.PatientRepository;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;

import java.util.Random;

@Service
@EnableWebSecurity
public class PatientService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String registerPatient(RegisterRequest request) {
        String genericPassword = generateGenericPassword();

        String encodedPassword = passwordEncoder.encode(genericPassword);

        // Create UserCredentials
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(request.getPatientEmail());
        userCredentials.setUserPassword(encodedPassword);
        userCredentials.setUserRole("ROLE_PATIENT");
        userCredentialsRepository.save(userCredentials);

        Long userId = userCredentials.getUserId();

        // Create Patient
        Patient patient = new Patient();
        patient.setUserId(userId);
        patient.setPatientEmail(request.getPatientEmail());
        patient.setPatientMobile(request.getPatientMobile());
        patient.setNhsNumber(request.getNhsNumber());
        patient.setPatientDOB(request.getPatientDOB());
        patient.setPatientName(request.getPatientName());
        patient.setPatientLastName(request.getPatientLastName());
        patient.setPatientTitle(request.getPatientTitle());
        patient.setUserCredentials(userCredentials);
        patientRepository.save(patient);

        return patient.getPatientName();
    }
    private String generateGenericPassword() {
        // Simple generic password generator for now
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}
