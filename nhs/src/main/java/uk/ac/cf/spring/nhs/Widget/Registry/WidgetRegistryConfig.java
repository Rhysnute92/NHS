package uk.ac.cf.spring.nhs.Widget.Registry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.ac.cf.spring.nhs.Widget.Model.HealthActionTrackerWidget;

@Configuration
public class WidgetRegistryConfig {

    /**
     * Creates and configures a new instance of the WidgetRegistry bean.
     *
     * @return the newly created WidgetRegistry bean
     */
    @Bean
    public WidgetRegistry widgetRegistry() {
        WidgetRegistry registry = new WidgetRegistry();
        registry.registerWidget("health-action-tracker", new HealthActionTrackerWidget());
        /*
         * registry.registerWidget("Appointments-tracker", new
         * AppointmentsTrackerWidget());
         */
        return registry;
    }

}
