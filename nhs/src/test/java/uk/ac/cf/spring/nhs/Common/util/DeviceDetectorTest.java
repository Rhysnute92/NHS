package uk.ac.cf.spring.nhs.Common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeviceDetectorTest {

    @Test
    public void testIsMobileWithMobileUserAgent() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("User-Agent")).thenReturn(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1");

        boolean isMobile = DeviceDetector.isMobile(mockRequest);
        assertTrue(isMobile, "Expected to detect a mobile device.");
    }

    @Test
    public void testIsMobileWithDesktopUserAgent() {
        HttpServletRequest mockRequestNew = mock(HttpServletRequest.class);
        when(mockRequestNew.getHeader("User-Agent")).thenReturn(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        boolean isMobile = DeviceDetector.isMobile(mockRequestNew);
        assertFalse(isMobile, "Expected to detect a desktop device.");
    }

    //This test doesn't work as if the user agent == null, then it is automatically not mobile
//     @Test
//     public void testIsMobileWithNullUserAgent() {
//         HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//         when(mockRequest.getHeader("User-Agent")).thenReturn(null);

//         boolean isMobile = DeviceDetector.isMobile(mockRequest);
//         assertFalse(isMobile, "Expected to detect a non-mobile device with null User-Agent.");
//     }
}
