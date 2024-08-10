package uk.ac.cf.spring.nhs.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;

//Service for User Details object that interacts directly with SecurityConfig authentication functions
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserCredentials user = userRepository.findByUserName(name);
        if(user != null){
            // return new CustomUserDetails(user);
            return CustomUserDetails.build(user);
        }else{
            throw new UsernameNotFoundException("username '" + name + "' not found");
        }
       
    }
}
