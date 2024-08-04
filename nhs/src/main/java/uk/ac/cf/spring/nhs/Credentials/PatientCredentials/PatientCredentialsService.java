package uk.ac.cf.spring.nhs.Credentials.PatientCredentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientCredentialsService {
    
    private final PatientCredentialsRepository patientCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientCredentialsService(PatientCredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder) {
        this.patientCredentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

}
