package uk.ac.cf.spring.nhs.Widget.Factory;

import uk.ac.cf.spring.nhs.Widget.Model.TaskCompletionWidget;
import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public class WidgetFactory {

    public static Widget getWidget(String widgetType) {
        if ("task-completion".equalsIgnoreCase(widgetType)) {
            return new TaskCompletionWidget();
        }
        // Add further widget types here
        
        return null;
    }
}