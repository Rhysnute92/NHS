package uk.ac.cf.spring.nhs.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    public static final String[] FREE_ACCESS = { "/landing", "/guest/**"};
    public static final String[] PATIENT_ACCESS = { "/dashboard", "/diary/**", 
    "/information","/treatment","/cellulitis", "/resources","/treatmentSpec", "/calendar", "/mobileaddappt" };
    public static final String[] PROVIDER_ACCESS = { "/addpatient" };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authProvider())
        .authorizeHttpRequests(request -> request
                .requestMatchers(FREE_ACCESS).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
                .requestMatchers(PATIENT_ACCESS).hasAnyRole("PATIENT", "ADMIN")
                .requestMatchers(PROVIDER_ACCESS).hasAnyRole("PROVIDER", "ADMIN")
                )
            .formLogin(form -> form
            .loginPage("/login")
            .successHandler(customSuccessHandler())
            .permitAll());
        http.csrf((csrf) -> csrf.disable());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**");
}
    
 
}
