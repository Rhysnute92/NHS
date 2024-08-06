package uk.ac.cf.spring.nhs.Guest.Controller;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class GuestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(context)
        .apply(springSecurity(springSecurityFilterChain))
        .build();
    }
    @Test
    public void testMobileGuestLanding() throws Exception {
        mockMvc.perform(get("/guest")
                        .header("User-Agent",
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/mobile/guestLanding"));
    }

    @Test
    public void testDesktopGuestLanding() throws Exception {
        mockMvc.perform(get("/guest")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/desktop/guestLanding"));
    }

    @Test
    public void testMobileLanding() throws Exception {
            mockStatic(DeviceDetector.class);
            when(DeviceDetector.isMobile(any(HttpServletRequest.class))).thenReturn(true);

            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("mobile/landing"));
        }


    @Test
    public void testDesktopLanding() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/desktop/guestLanding"));
    }

    @Test
    public void testGuestAppointment() throws Exception {
        mockMvc.perform(get("/guest/appointment"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/mobile/guestAppointment"));
    }

    @Test
    public void testGuestServices() throws Exception {
        mockMvc.perform(get("/guest/services"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/mobile/guestServices"));
    }

    @Test
    public void testGuestFindUs() throws Exception {
        mockMvc.perform(get("/guest/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/mobile/guestFindUs"));
    }
}
