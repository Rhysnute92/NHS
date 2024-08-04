package uk.ac.cf.spring.nhs.Credentials.ProviderCredentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProviderCredentialsService {
        
    private final ProviderCredentialsRepository providerCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProviderCredentialsService(ProviderCredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder) {
        this.providerCredentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

}
