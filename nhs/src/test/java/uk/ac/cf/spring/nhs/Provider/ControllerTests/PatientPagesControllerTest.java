package uk.ac.cf.spring.nhs.Provider.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Provider.Controllers.PatientProfileController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientPagesControllerTest {

    @InjectMocks
    private PatientProfileController profileController;

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

//    @Test
//    @WithMockUser(username = "admin", roles = { "PATIENT", "ADMIN" })
//    void testNavMenuItems() {
//        List<NavMenuItem> expectedNavMenuItems = List.of(
//                new NavMenuItem("Patient", "/patientprofile/info", "fa-solid fa-user-check"),
//                new NavMenuItem("Set plan", "", "fa-solid fa-book"),
//                new NavMenuItem("Appointments", " ", "fa-solid fa-user-check"),
//                new NavMenuItem("Questionnaires", "/patientprofile/questionnairehub", "fa-solid fa-book"),
//                new NavMenuItem("Patient trends", " ", "fa-solid fa-user-check"),
//                new NavMenuItem("Event log", " ", "fa-solid fa-book"),
//                new NavMenuItem("Photos", " ", "fa-solid fa-camera"),
//                new NavMenuItem("Email history", " ", "fa-solid fa-book"));
//
//        List<NavMenuItem> actualNavMenuItems = profileController.navMenuItems();
//
//        assertEquals(expectedNavMenuItems.size(), actualNavMenuItems.size());
//
//        for (int i = 0; i < expectedNavMenuItems.size(); i++) {
//            assertEquals(expectedNavMenuItems.get(i).getTitle(), actualNavMenuItems.get(i).getTitle());
//            assertEquals(expectedNavMenuItems.get(i).getUrl(), actualNavMenuItems.get(i).getUrl());
//            assertEquals(expectedNavMenuItems.get(i).getIcon(), actualNavMenuItems.get(i).getIcon());
//        }
//    }

}
