package uk.ac.cf.spring.nhs.Provider.ControllerTests;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientSearchControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(context)
        .apply(springSecurity())
        .build();
    }

@Test
@WithMockUser(username="admin",roles={"PATIENT"})
void patientSearchUserTest() throws Exception {
    mockMvc.perform(get("/provider/search"))
            .andExpect(status().is4xxClientError());
}
@Test
@WithMockUser(username="admin",roles={"ADMIN"})
void adminSearchUserTest() throws Exception {
    mockMvc.perform(get("/provider/search"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/desktop/search"));
}
@Test
@WithMockUser(username="admin",roles={"PROVIDER"})
void providerSearchUserTest() throws Exception {
    mockMvc.perform(get("/provider/search"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/desktop/search"));
}

}
