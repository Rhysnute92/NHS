package uk.ac.cf.spring.nhs.Widget.Registry;

import java.util.HashMap;
import java.util.Map;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public class WidgetRegistry {
    private static Map<String, Widget> widgets = new HashMap<>();

    public static void registerWidget(String name, Widget widget) {
        if (name == null || widget == null) {
            throw new IllegalArgumentException("Name and widget cannot be null");
        }
        widgets.put(name, widget);
    }

    public static Widget getWidget(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        Widget widget = widgets.get(name);
        if (widget == null) {
            throw new IllegalArgumentException("Widget with name " + name + " not found");
        }
        return widget;
    }
}
