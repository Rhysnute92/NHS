package uk.ac.cf.spring.nhs.Diary.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(DiaryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DiaryControllerTest {

    @MockBean
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiaryEntryService diaryEntryService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and security context
        closeable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(1L, "patient", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, "password", customUserDetails.getAuthorities());
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void providerAccountTest() throws Exception {
        mockMvc.perform(get("/diary"))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/diary"));
    }

    @Test
    public void testDiary() throws Exception {
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        diaryEntries.add(new DiaryEntry(1L, LocalDateTime.now()));
        diaryEntries.add(new DiaryEntry(2L, LocalDateTime.now()));

        when(diaryEntryService.getDiaryEntriesByUserId(1L)).thenReturn(diaryEntries);

        mockMvc.perform(get("/diary"))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/diary"));

        SecurityContextHolder.clearContext();
    }

    @Test
    public void testCheckinSuccess() throws Exception {
        CheckinFormDTO checkinForm = new CheckinFormDTO();
        DiaryEntry dummyDiaryEntry = new DiaryEntry(1L, LocalDateTime.now());

        when(diaryEntryService.saveDiaryEntry(any(CheckinFormDTO.class), eq(1L))).thenReturn(dummyDiaryEntry);

        mockMvc.perform(multipart("/diary/checkin")
                        .flashAttr("checkinForm", checkinForm)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Check-in successful"));

        ArgumentCaptor<CheckinFormDTO> formCaptor = ArgumentCaptor.forClass(CheckinFormDTO.class);
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(diaryEntryService).saveDiaryEntry(formCaptor.capture(), userIdCaptor.capture());

        assertEquals(1L, userIdCaptor.getValue());
    }

    @Test
    public void testCheckinFailure() throws Exception {
        CheckinFormDTO checkinForm = new CheckinFormDTO();

        doThrow(new RuntimeException("Error")).when(diaryEntryService).saveDiaryEntry(any(CheckinFormDTO.class), eq(1L));

        mockMvc.perform(multipart("/diary/checkin")
                        .flashAttr("checkinForm", checkinForm)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Error"));

        verify(diaryEntryService, times(1)).saveDiaryEntry(any(CheckinFormDTO.class), eq(1L));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"PATIENT", "ADMIN"})
    void testNavMenuItems() {
        List<NavMenuItem> expectedNavMenuItems = List.of(
                new NavMenuItem("Diary", "/diary", "fa-solid fa-book"),
                new NavMenuItem("Check-in", "/diary/checkin", "fa-solid fa-user-check"),
                new NavMenuItem("Photos", "/diary/photos", "fa-solid fa-camera")
        );

        List<NavMenuItem> actualNavMenuItems = new ArrayList<>(expectedNavMenuItems);

        assertEquals(expectedNavMenuItems.size(), actualNavMenuItems.size());

        for (int i = 0; i < expectedNavMenuItems.size(); i++) {
            assertEquals(expectedNavMenuItems.get(i).getTitle(), actualNavMenuItems.get(i).getTitle());
            assertEquals(expectedNavMenuItems.get(i).getUrl(), actualNavMenuItems.get(i).getUrl());
            assertEquals(expectedNavMenuItems.get(i).getIcon(), actualNavMenuItems.get(i).getIcon());
        }
    }
}
