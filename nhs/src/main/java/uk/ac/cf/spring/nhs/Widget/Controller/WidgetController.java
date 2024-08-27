package uk.ac.cf.spring.nhs.Widget.Controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uk.ac.cf.spring.nhs.Security.AuthenticationFacade;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;
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

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    private UserWidgetService userWidgetService;

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

    @GetMapping("/api/widgets/available")
    public ResponseEntity<Set<String>> getAvailableWidgets() {
        // Get all registered widget names
        Set<String> allWidgets = widgetRegistry.getRegisteredWidgetNames();

        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Get user widgets and convert to a set of widget names
        Set<String> userWidgetNames = userWidgetService.getUserWidgets(userId).stream()
                .map(UserWidgets::getWidgetName)
                .collect(Collectors.toSet());

        // Remove user widgets from the set of all widgets to get available widgets
        allWidgets.removeAll(userWidgetNames);
        return ResponseEntity.ok(allWidgets);
    }

}