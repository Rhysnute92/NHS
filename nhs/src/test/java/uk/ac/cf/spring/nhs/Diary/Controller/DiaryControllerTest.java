package uk.ac.cf.spring.nhs.Diary.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@WebMvcTest(DiaryController.class)
public class DiaryControllerTest {

    @InjectMocks
    private DiaryController diaryController;

    @MockBean
    private DiaryService diaryService;

    private MockHttpServletRequest request;

    private AutoCloseable closeable;

    private MockedStatic<DeviceDetector> mockedStatic;

    @Autowired
    private MockMvc mockMvc;

    private List<DiaryEntry> dummyEntries;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        mockedStatic = mockStatic(DeviceDetector.class);

        dummyEntries = Arrays.asList(
                new DiaryEntry(new Date(), "Formatted Date 1", "Content 1"),
                new DiaryEntry(new Date(), "Formatted Date 2", "Content 2")
        );

        when(diaryService.getDiaryEntries()).thenReturn(dummyEntries);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
        mockedStatic.close();
    }

    @Test
    public void diaryReturnsCorrectView() {
        ModelAndView modelAndView = diaryController.diary(request);
        assertEquals("diary/diary", modelAndView.getViewName());
        assertEquals(dummyEntries, modelAndView.getModel().get("diaryEntries"));
    }

    @Test
    public void checkinReturnsCorrectView() {
        ModelAndView modelAndView = diaryController.checkin(request);
        assertEquals("diary/checkin", modelAndView.getViewName());
        assertEquals(dummyEntries, modelAndView.getModel().get("diaryEntries"));
    }

    @Test
    public void testDiary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/diary"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("diary/diary"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("diaryEntries"))
                .andExpect(MockMvcResultMatchers.model().attribute("diaryEntries", dummyEntries));
    }
}
