package uk.ac.cf.spring.nhs.Widget.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

/**
 * WidgetController handles HTTP requests for widget content.
 * It fetches the widget by its name and renders it using the widget's render
 * method.
 */
@Controller
public class WidgetController {

    /**
     * Handles GET requests for fetching and rendering a specific widget's content.
     *
     * @param widgetName the name of the widget to be fetched
     * @return the view name to be rendered
     */
    @GetMapping("/api/widgets/{widgetName}")
    public String getWidgetContent(@PathVariable String widgetName) {
        // Fetch the widget from the WidgetRegistry using the provided widget name
        Widget widget = WidgetRegistry.getWidget(widgetName);
        // Check if the widget exists and render it
        if (widget != null) {
            String content = widget.render();
            // If the render method returns null, treat it as an error
            if (content != null) {
                return content;
            }
        }
        // If the widget is not found or the render method returns null, return a 404
        // error view
        return "error/404";
    }

    @GetMapping("/api/widgets/{widgetName}/script")
    @ResponseBody
    public String getWidgetScript(@PathVariable String widgetName) {
        Widget widget = WidgetRegistry.getWidget(widgetName);
        if (widget != null && widget.getScript() != null) {
            return widget.getScript();
        }
        return null;
    }
}
