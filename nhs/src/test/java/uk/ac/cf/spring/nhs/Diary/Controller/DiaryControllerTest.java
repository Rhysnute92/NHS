package uk.ac.cf.spring.nhs.Diary.Controller;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Diary.Service.PhotoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(DiaryController.class)
public class DiaryControllerTest {

    @InjectMocks
    private DiaryController diaryController;

    @MockBean
    private DiaryEntryService diaryEntryService;

    @MockBean
    private PhotoService photoService;

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

    private AutoCloseable closeable;
    private List<DiaryEntry> dummyEntries;
    private List<Photo> dummyPhotos;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        dummyEntries = new ArrayList<>(Arrays.asList(
                new DiaryEntry(1, new Date()),
                new DiaryEntry(2, new Date())
        ));

        dummyPhotos = new ArrayList<>(Arrays.asList(
                new Photo("photo1.jpg", new Date(), "", 1),
                new Photo("photo2.jpg", new Date(), "", 1)
        ));

        when(diaryEntryService.getDiaryEntriesByUserId(1)).thenReturn(dummyEntries);
        when(photoService.getPhotosByUserId(1)).thenReturn(dummyPhotos);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void diaryReturnsCorrectView() {
        Model model = mock(Model.class);
        String viewName = diaryController.diary(model);
        assertEquals("diary/diary", viewName);
        verify(model).addAttribute(eq("diaryEntries"), eq(dummyEntries));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void checkinReturnsCorrectView() {
        Model model = mock(Model.class);
        String viewName = diaryController.checkin(model);
        assertEquals("diary/checkin", viewName);
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void testDiary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/diary"))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/diary"))
                .andExpect(model().attributeExists("diaryEntries"))
                .andExpect(model().attribute("diaryEntries", dummyEntries));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void testCheckinSuccess() throws Exception {
        CheckinForm checkinForm = new CheckinForm();
        MockMultipartFile photo = new MockMultipartFile("photos", "photo.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        // Mock the createAndSaveDiaryEntry method to not throw an exception
        doNothing().when(diaryEntryService).createAndSaveDiaryEntry(any(CheckinForm.class));

        mockMvc.perform(multipart("/diary/checkin")
                        .file(photo)
                        .with(csrf())
                        .flashAttr("checkinForm", checkinForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/diary"));

        verify(diaryEntryService, times(1)).createAndSaveDiaryEntry(any(CheckinForm.class));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void testCheckinFailure() throws Exception {
        CheckinForm checkinForm = new CheckinForm();
        MockMultipartFile photo = new MockMultipartFile("photos", "photo.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        doThrow(new RuntimeException("Error")).when(diaryEntryService).createAndSaveDiaryEntry(any(CheckinForm.class));

        mockMvc.perform(multipart("/diary/checkin")
                        .file(photo)
                        .with(csrf())
                        .flashAttr("checkinForm", checkinForm))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/checkin"));

        verify(diaryEntryService, times(1)).createAndSaveDiaryEntry(any(CheckinForm.class));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    public void testPhotos() throws Exception {
        // Ensure the method returns dummy photos
        when(photoService.getPhotosByUserId(1)).thenReturn(dummyPhotos);

        mockMvc.perform(MockMvcRequestBuilders.get("/diary/photos"))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/photos"))
                .andExpect(model().attributeExists("photos"))
                .andExpect(model().attribute("photos", dummyPhotos));
    }

    @Test
    @WithMockUser
    public void testUploadPhotosSuccess() throws Exception {
        MockMultipartFile photo = new MockMultipartFile("photos", "photo.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        when(photoService.savePhoto(any(MultipartFile.class), eq(1))).thenReturn(null);

        mockMvc.perform(multipart("/diary/photos")
                        .file(photo).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/diary/photos"));

        verify(photoService, times(1)).savePhoto(any(MultipartFile.class), eq(1));
    }

    @Test
    @WithMockUser
    public void testUploadPhotosFailure() throws Exception {
        MockMultipartFile photo = new MockMultipartFile("photos", "photo.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        doThrow(new RuntimeException("Error")).when(photoService).savePhoto(any(MultipartFile.class), eq(1));

        mockMvc.perform(multipart("/diary/photos")
                        .file(photo).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/diary/photos"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @WithMockUser(username="admin",roles={"PATIENT","ADMIN"})
    void testNavMenuItems() {
        List<NavMenuItem> expectedNavMenuItems = List.of(
                new NavMenuItem("Diary", "/diary", "fa-solid fa-book"),
                new NavMenuItem("Check-in", "/diary/checkin", "fa-solid fa-user-check"),
                new NavMenuItem("Photos", "/diary/photos", "fa-solid fa-camera")
        );

        List<NavMenuItem> actualNavMenuItems = diaryController.navMenuItems();

        assertEquals(expectedNavMenuItems.size(), actualNavMenuItems.size());

        for (int i = 0; i < expectedNavMenuItems.size(); i++) {
            assertEquals(expectedNavMenuItems.get(i).getTitle(), actualNavMenuItems.get(i).getTitle());
            assertEquals(expectedNavMenuItems.get(i).getUrl(), actualNavMenuItems.get(i).getUrl());
            assertEquals(expectedNavMenuItems.get(i).getIcon(), actualNavMenuItems.get(i).getIcon());
        }
    }
}
