package uk.ac.cf.spring.nhs.Credentials.ProviderCredentials;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ProviderUserDetails implements UserDetails {
    
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public ProviderUserDetails(ProviderCredentials provider){
        this.username = provider.getProviderName();
        this.password = provider.getProviderPassword();
        this.authorities = Arrays.stream(provider.getProviderRole().split(",")).map(SimpleGrantedAuthority::new)
                                            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
