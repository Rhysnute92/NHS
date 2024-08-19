package uk.ac.cf.spring.nhs.Widget.Registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

class WidgetRegistryUnitTests {

    /**
     * Sets up the test environment before each test case.
     *
     * This method clears the registry by setting the "widgets" field of the
     * WidgetRegistry class to a new empty HashMap.
     * This ensures that each test starts with a clean registry.
     */
    @BeforeEach
    void setUp() {
        // Clear the registry before each test
        ReflectionTestUtils.setField(WidgetRegistry.class, "widgets", new HashMap<>());
    }

    /**
     * Tests the functionality of registering a valid widget in the WidgetRegistry.
     *
     * This test case arranges a valid widget with a name "testWidget" and a render
     * function that returns "testRender".
     * It then registers the widget using the registerWidget method of the
     * WidgetRegistry class.
     * After that, it retrieves the registered widget using the getWidget method of
     * the WidgetRegistry class.
     * Finally, it asserts that the retrieved widget is equal to the original widget
     * using the assertEquals method.
     *
     * @throws AssertionError if the retrieved widget is not equal to the original
     *                        widget
     */
    @Test
    void registerWidget_validWidget_registersSuccessfully() {
        // Arrange
        String widgetName = "testWidget";
        Widget widget = () -> "testRender";

        // Act
        WidgetRegistry.registerWidget(widgetName, widget);
        Widget result = WidgetRegistry.getWidget(widgetName);

        // Assert
        assertEquals(widget, result);
    }

    /**
     * Test case to verify that registering a widget with a null name throws an
     * IllegalArgumentException.
     *
     * @throws IllegalArgumentException if the widget name is null
     */
    @Test
    void registerWidget_nullName_throwsIllegalArgumentException() {
        // Arrange
        Widget widget = () -> "testRender";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.registerWidget(null, widget));
    }

    /**
     * Test case to verify that registering a widget with a null value throws an
     * IllegalArgumentException.
     *
     * @throws IllegalArgumentException if the widget is null
     */
    @Test
    void registerWidget_nullWidget_throwsIllegalArgumentException() {
        // Arrange
        String widgetName = "testWidget";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.registerWidget(widgetName, null));
    }

    /**
     * Test case to verify that registering a widget with a duplicate name
     * overwrites the existing widget.
     *
     * This test case arranges two widgets with the same name and registers them in
     * the WidgetRegistry.
     * It then retrieves the widget with the duplicate name and asserts that it is
     * equal to the second widget.
     *
     * @throws AssertionError if the retrieved widget is not equal to the second
     *                        widget
     */
    @Test
    void registerWidget_duplicateName_overwritesExistingWidget() {
        // Arrange
        String widgetName = "testWidget";
        Widget widget1 = () -> "testRender1";
        Widget widget2 = () -> "testRender2";

        // Act
        WidgetRegistry.registerWidget(widgetName, widget1);
        WidgetRegistry.registerWidget(widgetName, widget2);
        Widget result = WidgetRegistry.getWidget(widgetName);

        // Assert
        assertEquals(widget2, result); // Verify that the second widget overwrites the first
    }

    /**
     * Test case to verify that the getWidget method returns the correct widget when
     * given the name of an existing widget.
     *
     * @return void
     * @throws AssertionError if the retrieved widget is not equal to the expected
     *                        widget
     */
    @Test
    void getWidget_existingWidget_returnsWidget() {
        // Arrange
        String widgetName = "testWidget";
        Widget widget = () -> "testRender";
        WidgetRegistry.registerWidget(widgetName, widget);

        // Act
        Widget result = WidgetRegistry.getWidget(widgetName);

        // Assert
        assertEquals(widget, result);
    }

    /**
     * Test case to verify that the getWidget method returns null when given the
     * name of a non-existing widget.
     *
     * @return void
     * @throws AssertionError if the retrieved widget is not null
     */
    @Test
    void getWidget_nonExistingWidget_returnsNull() {
        // Act
        Widget result = WidgetRegistry.getWidget("nonExistingWidget");

        // Assert
        assertNull(result);
    }

    /**
     * Test case to verify that the getWidget method throws an
     * IllegalArgumentException
     * when given a null name.
     *
     * @throws IllegalArgumentException if the name is null
     */
    @Test
    void getWidget_nullName_throwsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.getWidget(null));
    }

    /**
     * Test case to verify that the getWidgetScript method returns null when the
     * widget has no associated script.
     *
     * This test arranges a widget with no script URL and verifies that the
     * getWidgetScript method returns null.
     */
    @Test
    void getWidgetScript_widgetWithoutScript_returnsNull() {
        // Arrange
        String widgetName = "testWidget";
        Widget widget = new Widget() {
            @Override
            public String render() {
                return "testRender";
            }

            @Override
            public String getScript() {
                return null; // No script associated
            }
        };
        WidgetRegistry.registerWidget(widgetName, widget);

        // Act
        String scriptUrl = WidgetRegistry.getWidgetScript(widgetName);

        // Assert
        assertNull(scriptUrl);
    }

    /**
     * Test case to verify that the getWidgetScript method returns null when the
     * widget does not exist.
     *
     * This test tries to get a script URL for a non-existing widget and verifies
     * that the method returns null.
     */
    @Test
    void getWidgetScript_nonExistingWidget_returnsNull() {
        // Act
        String scriptUrl = WidgetRegistry.getWidgetScript("nonExistingWidget");

        // Assert
        assertNull(scriptUrl);
    }

    /**
     * Test case to verify that the getWidgetScript method throws an
     * IllegalArgumentException when given a null name.
     *
     * This test tries to get a script URL with a null name and verifies that the
     * method throws an exception.
     */
    @Test
    void getWidgetScript_nullName_throwsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.getWidgetScript(null));
    }
}
