package uk.ac.cf.spring.nhs.Widget.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

@Controller
public class WidgetController {

    @GetMapping("/api/widgets/{widgetName}")
    public String getWidgetContent(@PathVariable String widgetName, Model model) {
        Widget widget = WidgetRegistry.getWidget(widgetName);
        if (widget != null) {
            model.addAttribute("CompletedTasks", 5);
            return widget.render();
        } else {
            return "error/404";
        }
    }

}
