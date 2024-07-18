package uk.ac.cf.spring.nhs.Diary.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class DiaryControllerTest {


    @InjectMocks
    private DiaryController diaryController;

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
        mockedStatic.when(() -> DeviceDetector.isMobile(request)).thenReturn(true);
        String viewName = diaryController.diary(request);
        assertEquals("mobile/diary", viewName);
    }

    @Test
    public void returnsDesktopViewForNonMobileDevices() {
        mockedStatic.when(() -> DeviceDetector.isMobile(request)).thenReturn(false);
        String viewName = diaryController.diary(request);
        assertEquals("desktop/diary", viewName);
    }
}
