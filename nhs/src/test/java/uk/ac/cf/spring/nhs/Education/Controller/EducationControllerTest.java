package uk.ac.cf.spring.nhs.Education.Controller;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EducationControllerTest {

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
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void mobileInfopageTest() throws Exception {
        mockMvc.perform(get("/information")
                .header("User-Agent",
                        "Mozilla/5.0 (Linux; Android 10; Pixel 3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4076.0 Mobile Safari/537.36"))
                .andExpect(status().isOk())
                .andExpect(view().name("education/mobile/information"));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void desktopInfopageTest() throws Exception {
        mockMvc.perform(get("/information")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246"))
                .andExpect(status().isOk())
                .andExpect(view().name("education/desktop/information"));
            }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void infopageTest() throws Exception {
        mockMvc.perform(get("/information"))
                .andExpect(status().isOk())
                .andExpect(view().name("education/desktop/information"));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void mobileTreatmentPageTest() throws Exception {
        mockMvc.perform(get("/treatment")
                .header("User-Agent",
                        "Mozilla/5.0 (Linux; Android 10; Pixel 3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4076.0 Mobile Safari/537.36"))
                .andExpect(status().isOk())
                .andExpect(view().name("education/mobile/treatment"));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void desktopTreatmentPageTest() throws Exception {
        mockMvc.perform(get("/treatment")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246"))
                .andExpect(status().isOk())
                .andExpect(view().name("education/desktop/treatment"));
            }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void treatmentPageTest() throws Exception {
        mockMvc.perform(get("/treatment"))
                .andExpect(status().isOk())
                .andExpect(view().name("education/desktop/treatment"));
    }
}
