package uk.ac.cf.spring.nhs.Photo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoDTO;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoListDTO;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class PhotoControllerTest {

    @MockBean
    private PhotoService photoService;

    @MockBean
    private AuthenticationInterface authenticationFacade;

    private AutoCloseable closeable;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

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
    public void testPhotos() throws Exception {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("photo1.jpg", new Date(), "Arm", 1L));
        photos.add(new Photo("photo2.jpg", new Date(), "Neck", 1L));

        when(photoService.getPhotosByUserId(1L)).thenReturn(photos);

        mockMvc.perform(get("/diary/photos"))
                .andExpect(status().isOk())
                .andExpect(view().name("diary/photos"))
                .andExpect(model().attributeExists("photos"))
                .andExpect(model().attribute("photos", photos));
    }

    @Test
    public void testUploadPhotosSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "photos[0].file",
                "photo.jpg",
                "image/jpeg",
                new byte[]{1, 2, 3, 4});

        when(photoService.savePhoto(any(PhotoDTO.class), eq(1L)))
                .thenReturn(new Photo("photo.jpg", new Date(), "leg", 1L));

        // Perform the mock request with the file and bodyPart as form parameters
        mockMvc.perform(multipart("/diary/photos")
                        .file(file)
                        .param("photos[0].bodyPart", "leg")
                        .with(csrf()))
                .andExpect(status().isOk());

        // Verify that the service method was called once
        verify(photoService, times(1)).savePhoto(any(PhotoDTO.class), eq(1L));
    }

    @Test
    public void testUploadPhotosFailure() throws Exception {
        MockMultipartFile file = new MockMultipartFile("photos[0].file", "photo.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        // Mock the behavior of the service to throw an exception
        doThrow(new RuntimeException("Error")).when(photoService).savePhoto(any(PhotoDTO.class), eq(1L));

        // Perform the request and expect an internal server error
        mockMvc.perform(multipart("/diary/photos")
                        .file(file)
                        .param("photos[0].bodyPart", "leg")
                        .with(csrf()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An error occurred: Error"));
    }

}

