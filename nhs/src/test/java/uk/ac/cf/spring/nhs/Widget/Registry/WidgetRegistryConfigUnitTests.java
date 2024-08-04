package uk.ac.cf.spring.nhs.Widget.Registry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import uk.ac.cf.spring.nhs.Widget.Model.AppointmentsTrackerWidget;
import uk.ac.cf.spring.nhs.Widget.Model.TaskCompletionWidget;
import uk.ac.cf.spring.nhs.Widget.Model.Widget;

@SpringBootTest
@ContextConfiguration(classes = WidgetRegistryConfig.class)
class WidgetRegistryConfigUnitTests {

    @Autowired
    private ApplicationContext context;

    /**
     * Sets up the test environment before each test case.
     *
     * @param  None
     * @return None
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This JUnit test method verifies that the application context loads successfully.
     * It does so by asserting that the 'context' object is not null.
     *
     * @return  void
     */
    @Test
    void contextLoads() {
        // Verify that the application context loads
        assertNotNull(context);
    }

    /**
     * Tests if the WidgetRegistry bean exists.
     *
     * @return void
     */
    @Test
    void widgetRegistryBeanExists() {
        // Verify that the WidgetRegistry bean is created
        WidgetRegistry widgetRegistry = context.getBean(WidgetRegistry.class);
        assertNotNull(widgetRegistry);
    }

    /**
     * Verifies that the TaskCompletionWidget is registered in the WidgetRegistry.
     *
     * @return void
     */
    @Test
    void taskCompletionWidgetRegistered() {
        // Verify that the TaskCompletionWidget is registered in the WidgetRegistry
        Widget widget = WidgetRegistry.getWidget("task-completion");
        assertNotNull(widget);
        assertTrue(widget instanceof TaskCompletionWidget);
    }

    /**
     * Verify that the AppointmentsTrackerWidget is registered in the WidgetRegistry.
     *
     * This test method retrieves the widget with the name "Appointments-tracker" from the WidgetRegistry and
     * asserts that it is not null. It then checks if the retrieved widget is an instance of the
     * AppointmentsTrackerWidget class using the instanceof operator.
     *
     * @throws AssertionError if the widget is null or not an instance of AppointmentsTrackerWidget
     */
    @Test
    void appointmentsTrackerWidgetRegistered() {
        // Verify that the AppointmentsTrackerWidget is registered in the WidgetRegistry
        Widget widget = WidgetRegistry.getWidget("Appointments-tracker");
        assertNotNull(widget);
        assertTrue(widget instanceof AppointmentsTrackerWidget);
    }

    /**
     * Verify that registering a widget with null name throws an IllegalArgumentException.
     *
     * @throws IllegalArgumentException if the widget name is null
     */
    @Test
    void registerWidget_withNullName_throwsException() {
        // Verify that registering a widget with null name throws an
        // IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            WidgetRegistry.registerWidget(null, new TaskCompletionWidget());
        });
    }

    /**
     * Verify that registering a null widget throws an IllegalArgumentException.
     *
     * @throws IllegalArgumentException if the widget is null
     */
    @Test
    void registerWidget_withNullWidget_throwsException() {
        // Verify that registering a null widget throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            WidgetRegistry.registerWidget("nullWidget", null);
        });
    }

    /**
     * Verify that registering a widget with a duplicate name overrides the existing widget.
     *
     * This test method registers a widget with the name "duplicateWidget" using the TaskCompletionWidget class.
     * Then it registers another widget with the same name using the AppointmentsTrackerWidget class.
     * Finally, it retrieves the widget with the name "duplicateWidget" from the WidgetRegistry and
     * asserts that it is not null and that it is an instance of the AppointmentsTrackerWidget class.
     *
     * @throws AssertionError if the widget is null or not an instance of AppointmentsTrackerWidget
     */
    @Test
    void registerWidget_withDuplicateName_overridesWidget() {
        // Verify that registering a widget with a duplicate name overrides the existing
        // widget
        WidgetRegistry.registerWidget("duplicateWidget", new TaskCompletionWidget());
        WidgetRegistry.registerWidget("duplicateWidget", new AppointmentsTrackerWidget());

        Widget widget = WidgetRegistry.getWidget("duplicateWidget");
        assertNotNull(widget);
        assertTrue(widget instanceof AppointmentsTrackerWidget);
    }

    /**
     * Test case to verify that attempting to retrieve an unregistered widget returns null.
     *
     * @return void
     */
    @Test
    void getWidget_withUnregisteredName_returnsNull() {
        // Verify that attempting to retrieve an unregistered widget returns null
        Widget widget = WidgetRegistry.getWidget("unregisteredWidget");
        assertNull(widget);
    }
}
