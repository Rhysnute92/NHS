package uk.ac.cf.spring.nhs.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//If you need to debug or amend anything in this file please 
//be careful about what references you're looking at. Any code that uses 
//'extends WebSecurityConfigurerAdapter' is DEPRECATED and will not work correctly. 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // BCrypt password encoder. Used to salt + hash passwords for the DB
    // and decrypt them during authentification.
    // When creating new users the function needs to use BCrypt to hash the provided
    // password
    // Ex. @Autowire PasswordEncoder
    // newUser.setPassword(passwordEncoder.encode(rawPassword));
    // When adding new users manually to the DB use any online bcrypt salt+hasher @
    // strength 10
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Custom authenticator to process provided user credentials
    // using custom UserDetails service and the encoder set above
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Custom handler for successfull login actions (redirects, etc.)
    // Any changes can be made in the associated
    // CustomAuthenticationSuccessHandler.java file
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    // Pre-set lists of pages accessible by different user roles
    // Any new page that requires specific access should be added here
    // GetMapper/RequestMapper links that are only used for
    // fetching data to populated pages DON'T need to be specified.
    // "/pagename/**" grants access to 'pagename' and all its sub-pages

    // Access privileges
    // FREE_ACCESS: anyone regardless of login status
    // PATIENT_ACCESS: only patients and admin
    // PROVIDER_ACCESS: only providers and admin
    // AUTH_ACCESS: any authenticated user

    public static final String[] FREE_ACCESS = { "/", "/landing", "/guest/**", "/reset-password/**" };
    public static final String[] PATIENT_ACCESS = { "/dashboard", "/diary/**",
            "/information", "/treatment", "/cellulitis", "/resources", "/treatmentSpec",
            "/calendar", "/mobileaddappt", "/managment" };
    public static final String[] PROVIDER_ACCESS = { "/addpatient", "/provider/**", "/patientprofile/**" };
    public static final String[] AUTH_ACCESS = { "/account" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authProvider())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(FREE_ACCESS).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
                        .requestMatchers(PATIENT_ACCESS).hasAnyRole("PATIENT", "ADMIN")
                        .requestMatchers(PROVIDER_ACCESS).hasAnyRole("PROVIDER", "ADMIN")
                        .requestMatchers(AUTH_ACCESS).hasAnyRole("PATIENT", "PROVIDER", "ADMIN")
                        .anyRequest().authenticated() // Ensures all other requests can be done by any authenticated
                                                      // user
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Limits each user to 1 concurrent session
                // If re-logged in the previous session is automatically closed
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler())
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/landing") // Successfull logout currently leads to the landing page
                        .invalidateHttpSession(true)
                        .deleteCookies("remove"));
        http.csrf((csrf) -> csrf.disable());
        return http.build();
    }

    // Allows calls to static resources, so they can be used on pages
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**");
    }

}
