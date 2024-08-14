package uk.ac.cf.spring.nhs.Calendar.Controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class CalendarControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Setup security before each test
     */
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(context)
        .apply(springSecurity())
        .build();
    }

    /**
     * Test to check if calendar works on mobile devices
     * @throws Exception
     */
    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void testCalendarForMobile() throws Exception {
        mockMvc.perform(get("/addappointment")
                        .header("User-Agent",
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1"))
                .andExpect(status().isOk())
                .andExpect(view().name("calendar/mobile/addappointment"));
    }

    /**
     * Test to check that the calendar works on desktop
     * @throws Exception
     */
    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void testCalendarForDesktop() throws Exception {
        mockMvc.perform(get("/calendar")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"))
                .andExpect(status().isOk())
                .andExpect(view().name("calendar/desktop/calendar"));
    }
}

