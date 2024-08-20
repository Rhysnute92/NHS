package uk.ac.cf.spring.nhs.Appointment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.nhs.Appointments.Controller.AppointmentController;
import uk.ac.cf.spring.nhs.Appointments.DTO.AppointmentDTO;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;
import uk.ac.cf.spring.nhs.Calendar.Controller.CalendarController;
import uk.ac.cf.spring.nhs.Security.AuthenticationFacade;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.*;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mariadb.jdbc.client.tls.HostnameVerifier.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AppointmentControllerTest {

    @MockBean
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Set up security before each test
     */
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and security context
        closeable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(1L, "patient", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, "password", customUserDetails.getAuthorities());
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * Test to see if getAllAppointments can be achieved
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", roles = {"PATIENT", "ADMIN"})
    public void testGetAllAppointments() throws Exception {
        mockMvc.perform(get("/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test to see if getAllAppointments can be achieved by a certain appointmentId
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", roles = {"PATIENT", "ADMIN"})
    public void testGetAppointmentById() throws Exception {
        mockMvc.perform(get("/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for the createAppointment method in the AppointmentController.
     *
     * This test verifies that when a valid AppointmentDTO is submitted via a POST request,
     * the AppointmentService correctly processes the request and returns a saved Appointment object.
     * The response is expected to have a status of 201 (Created) and contain the correct
     * appointment details in JSON format.
     *
     * @throws Exception if the request or response processing fails.
     */
//    @Test
//    public void testCreateAppointment() throws Exception {
//        // Given
//        AppointmentDTO appointmentDTO = new AppointmentDTO();
//        Appointment savedAppointment = new Appointment();
//
//        // When
//        when(appointmentService.saveAppointment(appointmentDTO)).thenReturn(savedAppointment);
//    }
}
