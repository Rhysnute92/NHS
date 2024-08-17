package uk.ac.cf.spring.nhs.Widget.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;
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
    private UserTaskService userTaskService;

    /**
     * Handles GET requests for fetching and rendering a specific widget's content.
     *
     * @param widgetName the name of the widget to be fetched
     * @return the view name to be rendered
     */
    @GetMapping("/api/widgets/{widgetName}")
    public String getWidgetContent(@PathVariable String widgetName, Model model) {
        // Fetch the widget from the WidgetRegistry using the provided widget name
        Widget widget = WidgetRegistry.getWidget(widgetName);
/*         String widgetScriptUrl = WidgetRegistry.getWidgetScript(widgetName);
        System.out.println("Script URL: " + widgetScriptUrl); */

        // Check if the widget exists
        if (widget != null) {
            String content = widget.render();
            if (content != null) {
                model.addAttribute("widget", widget);
/*                 model.addAttribute("script", widgetScriptUrl); */
                return content;
            }
        }
        return "error/404"; // Return a 404 error view if the widget is not found or render is null
    }

/*     @GetMapping("/api/widgets/{widgetName}/script")
    @ResponseBody
    public String getWidgetScript(@PathVariable String widgetName) {
        Widget widget = WidgetRegistry.getWidget(widgetName);
        if (widget != null && widget.getScript() != null) {
            return widget.getScript();
        }
        return null;
    } */

    @GetMapping("/api/widgets/task-completion/data/{userId}/{day}")
    @ResponseBody
    public Map<String, Integer> getTaskCompletionData(@PathVariable Long userId, @PathVariable int day) {
        int totalTasks = userTaskService.getTasksForUser(userId).size();
        int completedTasks = userTaskService.countCompletedTasksForday(userId, day);

        Map<String, Integer> response = new HashMap<>();
        response.put("totalTasks", totalTasks > 0 ? totalTasks : 1);
        response.put("completedTasks", completedTasks);
        return response;
    }
}