package uk.ac.cf.spring.nhs.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
 
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    public static final String[] FREE_ACCESS = { "/landing", "/guest/**"};
    public static final String[] PATIENT_ACCESS = { "/dashboard", "/diary/**", 
    "/information","/treatment","/cellulitis", "/resources","/treatmentSpec", "/calendar", "/mobileaddappt" };
    public static final String[] PROVIDER_ACCESS = { "/addpatient" };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http .authorizeHttpRequests(request -> request
                .requestMatchers(FREE_ACCESS).permitAll()
                .requestMatchers(PATIENT_ACCESS).hasRole("PATIENT")
                .requestMatchers(PROVIDER_ACCESS).hasRole("PROVIDER")
                .anyRequest().hasRole("ADMIN"))
            .csrf(csrf -> csrf.disable() )
            .formLogin(form -> form.loginPage("/login"). permitAll());

        return http.build();
    }
    
 
}
