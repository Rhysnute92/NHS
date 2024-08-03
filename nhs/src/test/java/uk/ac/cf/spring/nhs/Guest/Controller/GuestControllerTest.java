package uk.ac.cf.spring.nhs.Guest.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void testGuestLanding() throws Exception {
        mockMvc.perform(get("/guest"))
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
