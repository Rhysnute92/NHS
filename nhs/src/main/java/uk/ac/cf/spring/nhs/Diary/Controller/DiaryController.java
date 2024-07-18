package uk.ac.cf.spring.nhs.Diary.Controller;

import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class DiaryController {

    @GetMapping("/diary")
    public String diary(HttpServletRequest request) {
        if (DeviceDetector.isMobile(request)) {
            return "mobile/diary";
        } else {
            return "desktop/diary";
        }
    }
}   