package uk.ac.cf.spring.nhs.Account;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountContollerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    // private void setUser(){
    //     List<GrantedAuthority> authoritiesTest =  new ArrayList<>();
    //     authoritiesTest.add(0, new SimpleGrantedAuthority("ROLE_ADMIN"));
    //     Long id = 1L;
    //     CustomUserDetails customUser = new CustomUserDetails(id,"username", "pw", authoritiesTest );
    // }


    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(context)
        .apply(springSecurity(springSecurityFilterChain))
        .build();
    }

@Test
@WithMockUser(username="admin",roles={"PATIENT"})
void patientAccountTest() throws Exception {
    mockMvc.perform(get("/account"))
            .andExpect(status().isOk())
            .andExpect(view().name("account/account"));
}
@Test
@WithMockUser(username="admin",roles={"ADMIN"})
void adminAccountTest() throws Exception {
    mockMvc.perform(get("/account"))
            .andExpect(status().isOk())
            .andExpect(view().name("account/account"));
}
@Test
@WithMockUser(username="admin",roles={"PROVIDER"})
void providerAccountTest() throws Exception {
    mockMvc.perform(get("/account"))
            .andExpect(status().isOk())
            .andExpect(view().name("account/account"));
}

// @Test
// @WithUserDetails("username")
// void userDetailsTest() throws Exception {
//     mockMvc.perform(get("/userFullName"))
//             .andExpect(status().isOk())
//             .andExpect(content().string(containsString("username")));
    
// }
}
