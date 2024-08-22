package uk.ac.cf.spring.nhs.Widget.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

@Component
public class WidgetRegistry implements WidgetRegistryInterface {
    private static final Logger logger = LoggerFactory.getLogger(WidgetRegistry.class);
    private final Map<String, Widget> widgets = new HashMap<>();

    @Override
    public void registerWidget(String name, Widget widget) {
        if (name == null || widget == null) {
            throw new IllegalArgumentException("Name and widget cannot be null");
        }
        String formattedName = formatWidgetName(name);
        widgets.put(formattedName, widget);
        logger.info("Registered widget: {} (formatted: {})", name, formattedName);
    }

    @Override
    public Widget getWidget(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        String formattedName = formatWidgetName(name);
        Widget widget = widgets.get(formattedName);
        logger.debug("Retrieved widget: {} (formatted: {})", name, formattedName);
        return widget;
    }

    @Override
    public String getWidgetScript(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        String formattedName = formatWidgetName(name);
        Widget widget = widgets.get(formattedName);
        if (widget != null && widget.getScript() != null) {
            return "/js/widgets/" + widget.getScript() + ".js";
        }
        return null;
    }

    private String formatWidgetName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Widget name cannot be null");
        }
        return name.toLowerCase().replaceAll("\\s+", "-").replaceAll("[^a-z0-9-]", "");
    }

    @Override
    public boolean hasWidget(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        String formattedName = formatWidgetName(name);
        return widgets.containsKey(formattedName);
    }

    @Override
    public Set<String> getRegisteredWidgetNames() {
        return new HashSet<>(widgets.keySet());
    }

}
