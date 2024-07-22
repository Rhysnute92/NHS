package uk.ac.cf.spring.nhs.Calendar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.cf.spring.nhs.CalendarContoller.calendarController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(calendarController.class)
public class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

<<<<<<< HEAD
     @Test
    public void testMobileCalendar() throws Exception {
        mockMvc.perform(get("/calendar")
                        .header("User-Agent",
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1"))
=======
    @Test
    public void testCalendarForMobile() throws Exception {
        mockMvc.perform(get("/calendar")
                        .header("User-Agent",
                                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1"))
>>>>>>> 84028f2d3aa939543d33c4985355f841b215a8cf
                .andExpect(status().isOk())
                .andExpect(view().name("mobile/calendar"));
    }

<<<<<<< HEAD
=======
    @Test
    public void testCalendarForDesktop() throws Exception {
        mockMvc.perform(get("/calendar")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"))
                .andExpect(status().isOk())
                .andExpect(view().name("desktop/calendar"));
    }
>>>>>>> 84028f2d3aa939543d33c4985355f841b215a8cf
}

