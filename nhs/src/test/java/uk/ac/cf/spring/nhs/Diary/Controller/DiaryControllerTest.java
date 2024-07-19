package uk.ac.cf.spring.nhs.Diary.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Diary.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class DiaryControllerTest {

    @InjectMocks
    private DiaryController diaryController;

    @Mock
    private DiaryService diaryService;

    private MockHttpServletRequest request;

    private AutoCloseable closeable;

    private MockedStatic<DeviceDetector> mockedStatic;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        mockedStatic = mockStatic(DeviceDetector.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
        mockedStatic.close();
    }

    @Test
    public void returnsMobileViewForMobileDevices() {
        // Mock DeviceDetector to return true for isMobile
        mockedStatic.when(() -> DeviceDetector.isMobile(request)).thenReturn(true);

        // Mock diaryService to return an empty list
        List<DiaryEntry> mockDiaryEntries = new ArrayList<>();
        when(diaryService.getDiaryEntries()).thenReturn(mockDiaryEntries);

        // Call the method and check the view name
        ModelAndView modelAndView = diaryController.diary(request);
        assertEquals("mobile/diary", modelAndView.getViewName());
        assertEquals(mockDiaryEntries, modelAndView.getModel().get("diaryEntries"));
    }

    @Test
    public void returnsDesktopViewForNonMobileDevices() {
        // Mock DeviceDetector to return false for isMobile
        mockedStatic.when(() -> DeviceDetector.isMobile(request)).thenReturn(false);

        // Mock diaryService to return an empty list
        List<DiaryEntry> mockDiaryEntries = new ArrayList<>();
        when(diaryService.getDiaryEntries()).thenReturn(mockDiaryEntries);

        // Call the method and check the view name
        ModelAndView modelAndView = diaryController.diary(request);
        assertEquals("desktop/diary", modelAndView.getViewName());
        assertEquals(mockDiaryEntries, modelAndView.getModel().get("diaryEntries"));
    }
}
