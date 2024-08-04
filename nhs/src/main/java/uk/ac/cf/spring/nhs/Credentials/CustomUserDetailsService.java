package uk.ac.cf.spring.nhs.Credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Credentials.Admin.Admin;
import uk.ac.cf.spring.nhs.Credentials.Admin.AdminRepository;
import uk.ac.cf.spring.nhs.Credentials.Admin.AdminUserDetails;
import uk.ac.cf.spring.nhs.Credentials.PatientCredentials.PatientCredentials;
import uk.ac.cf.spring.nhs.Credentials.PatientCredentials.PatientCredentialsRepository;
import uk.ac.cf.spring.nhs.Credentials.PatientCredentials.PatientUserDetails;
import uk.ac.cf.spring.nhs.Credentials.ProviderCredentials.ProviderCredentials;
import uk.ac.cf.spring.nhs.Credentials.ProviderCredentials.ProviderCredentialsRepository;
import uk.ac.cf.spring.nhs.Credentials.ProviderCredentials.ProviderUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    private PatientCredentialsRepository patientCredentialsRepository;
    private ProviderCredentialsRepository providerCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Admin admin = adminRepository.getReferenceById(name);
        PatientCredentials patient = patientCredentialsRepository.findByUserName(name);
        ProviderCredentials provider = providerCredentialsRepository.findByProviderName(name);
        if(admin != null){
            return new AdminUserDetails(admin);
        }else if(patient != null){
            return new PatientUserDetails(patient);
        }else if(provider != null){
            return new ProviderUserDetails(provider);
        }else{
            throw new UsernameNotFoundException("username '" + name + "' not found");
        }
       
    }
}
