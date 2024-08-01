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

    @BeforeEach
    void setUp() {
        // Clear the registry before each test
        ReflectionTestUtils.setField(WidgetRegistry.class, "widgets", new HashMap<>());
    }

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

    @Test
    void registerWidget_nullName_throwsIllegalArgumentException() {
        // Arrange
        Widget widget = () -> "testRender";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.registerWidget(null, widget));
    }

    @Test
    void registerWidget_nullWidget_throwsIllegalArgumentException() {
        // Arrange
        String widgetName = "testWidget";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.registerWidget(widgetName, null));
    }

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
        assertEquals(widget2, result);  // Verify that the second widget overwrites the first
    }

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

    @Test
    void getWidget_nonExistingWidget_returnsNull() {
        // Act
        Widget result = WidgetRegistry.getWidget("nonExistingWidget");

        // Assert
        assertNull(result);
    }

    @Test
    void getWidget_nullName_throwsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> WidgetRegistry.getWidget(null));
    }
}
