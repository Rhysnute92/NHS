package uk.ac.cf.spring.nhs.Widget.Registry;

import java.util.HashMap;
import java.util.Map;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public class WidgetRegistry {
    private static Map<String, Widget> widgets = new HashMap<>();

    /**
     * Registers a widget with the given name in the WidgetRegistry.
     *
     * @param name   the name of the widget
     * @param widget the widget to be registered
     * @throws IllegalArgumentException if the name or widget is null
     */
    public static void registerWidget(String name, Widget widget) {
        if (name == null || widget == null) {
            throw new IllegalArgumentException("Name and widget cannot be null");
        }
        widgets.put(name, widget);
    }

    /**
     * Retrieves a widget from the widget registry based on its name.
     *
     * @param name the name of the widget to retrieve
     * @return the widget with the specified name, or null if not found
     * @throws IllegalArgumentException if the name is null
     */
    public static Widget getWidget(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        return widgets.get(name);
    }

    public static String getWidgetScript(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        Widget widget = widgets.get(name);
        if (widget != null) {
            String script = widget.getScript();
            if (script != null) {
                return "/js/widgets/" + script + ".js"; // Full path to the static resource
            }
        }
        return null;
    }
}
