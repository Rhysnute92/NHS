package uk.ac.cf.spring.nhs.Appointment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.nhs.Appointments.Controller.AppointmentController;
import uk.ac.cf.spring.nhs.Appointments.DTO.AppointmentDTO;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;

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

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

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
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
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

    @Test
    public void testCreateAppointment() throws Exception {
        // Given
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        Appointment savedAppointment = new Appointment();

        // When
        when(appointmentService.saveAppointment(appointmentDTO)).thenReturn(savedAppointment);
    }
}
