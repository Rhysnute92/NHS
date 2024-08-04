package uk.ac.cf.spring.nhs.Credentials.Admin;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AdminUserDetails implements UserDetails{
    
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public AdminUserDetails(Admin admin){
        this.username = admin.getAdminName();
        this.password = admin.getAdminPassword();
        this.authorities = Arrays.stream(admin.getAdminRole().split(",")).map(SimpleGrantedAuthority::new)
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
