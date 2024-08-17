package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

class WidgetControllerUnitTests {

    private WidgetController widgetController;

    @BeforeEach
    void setUp() {
        widgetController = new WidgetController();
        mockWidgetRegistry();
    }

    private void mockWidgetRegistry() {
        Map<String, Widget> mockWidgets = new HashMap<>();
        Widget testWidget = mock(Widget.class);
        when(testWidget.render()).thenReturn("widgetContent");
        mockWidgets.put("testWidget", testWidget);

        // Use ReflectionTestUtils to set the private static field
        ReflectionTestUtils.setField(WidgetRegistry.class, "widgets", mockWidgets);
    }

    @Test
    void getWidgetContent_widgetExists_returnsWidgetContent() {
        // Act
        String viewName = widgetController.getWidgetContent("testWidget");

        // Assert
        assertEquals("widgetContent", viewName);
    }

    @Test
    void getWidgetContent_widgetDoesNotExist_returnsErrorView() {
        // Act
        String viewName = widgetController.getWidgetContent("nonExistentWidget");

        // Assert
        assertEquals("error/404", viewName);
    }

    @Test
    void getWidgetContent_nullWidgetName_throwsIllegalArgumentException() {
        // Act and Assert
        try {
            widgetController.getWidgetContent(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null", e.getMessage());
        }
    }

    @Test
    void getWidgetContent_emptyWidgetName_returnsErrorView() {
        // Act
        String viewName = widgetController.getWidgetContent("");

        // Assert
        assertEquals("error/404", viewName);
    }

    @Test
    void getWidgetContent_specialCharactersInWidgetName_returnsErrorView() {
        // Act
        String viewName = widgetController.getWidgetContent("!@#$%^&*()");

        // Assert
        assertEquals("error/404", viewName);
    }

    @Test
    void getWidgetContent_widgetWithNoRenderMethod_returnsErrorView() {
        // Arrange
        Widget widgetWithNoRenderMethod = mock(Widget.class);
        Map<String, Widget> mockWidgets = new HashMap<>();
        mockWidgets.put("noRenderWidget", widgetWithNoRenderMethod);
        ReflectionTestUtils.setField(WidgetRegistry.class, "widgets", mockWidgets);

        // Act
        String viewName = widgetController.getWidgetContent("noRenderWidget");

        // Assert
        assertEquals("error/404", viewName);
    }

    @Test
    void getWidgetScript_widgetWithScript_returnsScriptUrl() {
        // Arrange
        Widget testWidgetWithScript = mock(Widget.class);
        when(testWidgetWithScript.getScript()).thenReturn("/static/js/widgets/testWidget.js");

        // Manually add the widget to the registry using reflection
        Map<String, Widget> mockWidgets = new HashMap<>();
        mockWidgets.put("testWidgetWithScript", testWidgetWithScript);
        ReflectionTestUtils.setField(WidgetRegistry.class, "widgets", mockWidgets);

        // Act
        String scriptUrl = widgetController.getWidgetScript("testWidgetWithScript");

        // Assert
        assertEquals("/static/js/widgets/testWidget.js", scriptUrl);
    }

    @Test
    void getWidgetScript_widgetWithoutScript_returnsNull() {
        // Act
        String scriptUrl = widgetController.getWidgetScript("testWidgetWithoutScript");

        // Assert
        assertNull(scriptUrl);
    }

    @Test
    void getWidgetScript_widgetDoesNotExist_returnsNull() {
        // Act
        String scriptUrl = widgetController.getWidgetScript("nonExistentWidget");

        // Assert
        assertNull(scriptUrl);
    }

    @Test
    void getWidgetScript_nullWidgetName_throwsIllegalArgumentException() {
        // Act and Assert
        try {
            widgetController.getWidgetScript(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null", e.getMessage());
        }
    }
}
