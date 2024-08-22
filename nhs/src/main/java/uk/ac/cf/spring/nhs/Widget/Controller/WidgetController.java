package uk.ac.cf.spring.nhs.Widget.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

/**
 * WidgetController handles HTTP requests for widget content.
 * It fetches the widget by its name and renders it using the widget's render
 * method.
 */
@Controller
public class WidgetController {

    @Autowired
    private WidgetRegistry widgetRegistry;

    /**
     * Handles GET requests for fetching and rendering a specific widget's content.
     *
     * @param widgetName the name of the widget to be fetched
     * @return the view name to be rendered
     */
    @GetMapping("/api/widgets/{widgetName}")
    public String getWidgetContent(@PathVariable String widgetName, Model model) {
        // Fetch the widget from the WidgetRegistry using the provided widget name
        Widget widget = widgetRegistry.getWidget(widgetName);

        // Check if the widget exists
        if (widget != null) {
            String content = widget.render();
            if (content != null) {
                model.addAttribute("widget", widget);
                /* model.addAttribute("script", widgetScriptUrl); */
                return content;
            }
        }
        return "error/404"; // Return a 404 error view if the widget is not found or render is null
    }
}