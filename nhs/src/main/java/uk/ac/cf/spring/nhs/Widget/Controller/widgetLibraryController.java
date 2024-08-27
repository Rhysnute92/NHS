package uk.ac.cf.spring.nhs.Widget.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class widgetLibraryController {

    @GetMapping("/widgetLibrary")
    public String getWidgetLibrary() {
        return "dashboard/widgetLibrary/widgetLibrary";
    }

}
