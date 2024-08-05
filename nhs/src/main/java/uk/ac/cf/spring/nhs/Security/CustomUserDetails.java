package uk.ac.cf.spring.nhs.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;

public class CustomUserDetails implements UserDetails{
    
    private Long userId;
    private String username;
    private String password;
    List<GrantedAuthority> authorities;

  public CustomUserDetails(Long userId, String username, String password,
      List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
  }

  public static CustomUserDetails build(UserCredentials user) {
    List<GrantedAuthority> authorities = Stream.of(user.getUserRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return new CustomUserDetails(
        user.getUserId(),
        user.getUserName(), 
        user.getUserPassword(), 
        authorities);
    }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Long getUserID() {
    return userId;
  }
}
