package uk.ac.cf.spring.nhs.Widget.Factory;

import java.util.HashMap;
import java.util.Map;

import uk.ac.cf.spring.nhs.Widget.Model.TaskCompletionWidget;
import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public class WidgetFactory {

    private static Map<String, Widget> widgets = new HashMap<>();

    static {
        widgets.put("task-completion-widget", new TaskCompletionWidget());
    }

    public static Widget getWidget(String widgetIs) {
        
    }
    
}
