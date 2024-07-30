package uk.ac.cf.spring.nhs.Widget.Registry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.ac.cf.spring.nhs.Widget.Model.AppointmentsTrackerWidget;
import uk.ac.cf.spring.nhs.Widget.Model.TaskCompletionWidget;

@Configuration
public class WidgetRegistryConfig {

    @Bean
    public WidgetRegistry widgetRegistry() {
        WidgetRegistry registry = new WidgetRegistry();
        WidgetRegistry.registerWidget("task-completion", new TaskCompletionWidget());
        WidgetRegistry.registerWidget("Appointments-tracker", new AppointmentsTrackerWidget());
        return registry;
    }

}
