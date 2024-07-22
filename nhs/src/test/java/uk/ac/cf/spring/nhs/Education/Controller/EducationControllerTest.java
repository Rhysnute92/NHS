package uk.ac.cf.spring.nhs.Education.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EducationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mobileInfopageTest() throws Exception {
        mockMvc.perform(get("/information")
                .header("User-Agent",
                        "Mozilla/5.0 (Linux; Android 10; Pixel 3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4076.0 Mobile Safari/537.36"))
                .andExpect(status().isOk())
                .andExpect(view().name("mobile/Education/information"));
    }

    @Test
    void desktopInfopageTest() throws Exception {
        mockMvc.perform(get("/information")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246"))
                .andExpect(status().isOk())
                .andExpect(view().name("desktop/Education/information"));
            }

    @Test
    void infopageTest() throws Exception {
        mockMvc.perform(get("/information"))
                .andExpect(status().isOk())
                .andExpect(view().name("desktop/Education/information"));
    }

    @Test
    void mobileTreatmentPageTest() throws Exception {
        mockMvc.perform(get("/treatment")
                .header("User-Agent",
                        "Mozilla/5.0 (Linux; Android 10; Pixel 3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4076.0 Mobile Safari/537.36"))
                .andExpect(status().isOk())
                .andExpect(view().name("mobile/Education/treatment"));
    }

    @Test
    void desktopTreatmentPageTest() throws Exception {
        mockMvc.perform(get("/treatment")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246"))
                .andExpect(status().isOk())
                .andExpect(view().name("desktop/Education/treatment"));
            }

    @Test
    void treatmentPageTest() throws Exception {
        mockMvc.perform(get("/treatment"))
                .andExpect(status().isOk())
                .andExpect(view().name("desktop/Education/treatment"));
    }
}
