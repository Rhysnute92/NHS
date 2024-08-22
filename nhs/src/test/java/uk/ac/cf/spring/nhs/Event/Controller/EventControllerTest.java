package uk.ac.cf.spring.nhs.Event.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.nhs.Event.Service.EventService;
import uk.ac.cf.spring.nhs.Event.DTO.EventDTO;
import uk.ac.cf.spring.nhs.Event.Entity.Event;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Mock
    private CustomUserDetails userDetails;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationInterface authenticationFacade;

    private AutoCloseable closeable;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
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
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetEvents() throws Exception {
        Event event1 = new Event(LocalDateTime.now().toLocalDate(), 2, 1L);
        Event event2 = new Event(LocalDateTime.now().toLocalDate(), 3, 1L);

        when(userDetails.getUserId()).thenReturn(1L);
        when(eventService.getEventsByUserId(anyLong())).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/diary/events")
                        .contentType(MediaType.TEXT_HTML)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/events"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attribute("events", hasSize(2)));
    }

    @Test
    void testCreateEvent() throws Exception {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(LocalDateTime.now().toLocalDate());
        eventDTO.setDuration(2);
        eventDTO.setSymptoms(List.of());
        eventDTO.setTreatments(List.of());

        Event savedEvent = new Event(LocalDateTime.now().toLocalDate(), 2, 1L);

        when(eventService.saveEvent(any(EventDTO.class), anyLong())).thenReturn(savedEvent);

        mockMvc.perform(post("/diary/events")
                        .flashAttr("eventDTO", eventDTO)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();
    }
}
