package uk.ac.cf.spring.nhs.Widget.Registry;

import java.util.Set;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public interface WidgetRegistryInterface {
    void registerWidget(String name, Widget widget);

    Widget getWidget(String name);

    String getWidgetScript(String name);

    Set<String> getRegisteredWidgetNames();

    boolean hasWidget(String name);
}