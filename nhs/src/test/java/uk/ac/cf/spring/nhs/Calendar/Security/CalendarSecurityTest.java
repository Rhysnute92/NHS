package uk.ac.cf.spring.nhs.Calendar.Security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CalendarSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addAppt() throws Exception {
        this.mockMvc.perform(get("/mobileaddappt")
                .with(SecurityMockMVCRequestPostProcessors.appointmentType("apptList").roles("LYMPHOEDEMA")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content()
                .string(containsString("Lymphoedema")));
    }
}
