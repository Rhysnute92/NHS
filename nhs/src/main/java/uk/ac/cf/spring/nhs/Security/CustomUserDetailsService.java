package uk.ac.cf.spring.nhs.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserCredentials user = userRepository.findByUserName(name);
        if(user != null){
            // return new CustomUserDetails(user);
            return User.withUsername(user.getUserName())
            .password(user.getUserPassword())
            .authorities(user.getUserRole())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
        }else{
            throw new UsernameNotFoundException("username '" + name + "' not found");
        }
       
    }
}
