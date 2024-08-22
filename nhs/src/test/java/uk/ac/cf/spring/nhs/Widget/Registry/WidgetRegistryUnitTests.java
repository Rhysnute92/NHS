package uk.ac.cf.spring.nhs.Widget.Registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

class WidgetRegistryUnitTests {

    private WidgetRegistry widgetRegistry;

    @BeforeEach
    void setUp() {
        widgetRegistry = new WidgetRegistry();
    }

    @Test
    void registerWidget_validWidget_registersSuccessfully() {

        String widgetName = "Test Widget";
        Widget widget = () -> "testRender";

        widgetRegistry.registerWidget(widgetName, widget);
        Widget result = widgetRegistry.getWidget(widgetName);

        assertEquals(widget, result);
    }

    @Test
    void registerWidget_nullName_throwsIllegalArgumentException() {

        Widget widget = () -> "testRender";

        assertThrows(IllegalArgumentException.class, () -> widgetRegistry.registerWidget(null, widget));
    }

    @Test
    void registerWidget_nullWidget_throwsIllegalArgumentException() {

        String widgetName = "Test Widget";

        assertThrows(IllegalArgumentException.class, () -> widgetRegistry.registerWidget(widgetName, null));
    }

    @Test
    void registerWidget_duplicateName_overwritesExistingWidget() {

        String widgetName = "Test Widget";
        Widget widget1 = () -> "testRender1";
        Widget widget2 = () -> "testRender2";

        widgetRegistry.registerWidget(widgetName, widget1);
        widgetRegistry.registerWidget(widgetName, widget2);
        Widget result = widgetRegistry.getWidget(widgetName);

        assertEquals(widget2, result);
    }

    @Test
    void getWidget_existingWidget_returnsWidget() {

        String widgetName = "Test Widget";
        Widget widget = () -> "testRender";
        widgetRegistry.registerWidget(widgetName, widget);

        Widget result = widgetRegistry.getWidget(widgetName);

        assertEquals(widget, result);
    }

    @Test
    void getWidget_nonExistingWidget_returnsNull() {

        Widget result = widgetRegistry.getWidget("Non-existing Widget");

        assertNull(result);
    }

    @Test
    void getWidget_nullName_throwsIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> widgetRegistry.getWidget(null));
    }

    @Test
    void getWidgetScript_widgetWithScript_returnsScriptPath() {

        String widgetName = "Test Widget";
        Widget widget = new Widget() {
            @Override
            public String render() {
                return "testRender";
            }

            @Override
            public String getScript() {
                return "testScript";
            }
        };
        widgetRegistry.registerWidget(widgetName, widget);

        String scriptPath = widgetRegistry.getWidgetScript(widgetName);

        assertEquals("/js/widgets/testScript.js", scriptPath);
    }

    @Test
    void getWidgetScript_widgetWithoutScript_returnsNull() {

        String widgetName = "Test Widget";
        Widget widget = () -> "testRender";
        widgetRegistry.registerWidget(widgetName, widget);

        String scriptPath = widgetRegistry.getWidgetScript(widgetName);

        assertNull(scriptPath);
    }

    @Test
    void getWidgetScript_nonExistingWidget_returnsNull() {

        String scriptPath = widgetRegistry.getWidgetScript("Non-existing Widget");

        assertNull(scriptPath);
    }

    @Test
    void getWidgetScript_nullName_throwsIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> widgetRegistry.getWidgetScript(null));
    }

    @Test
    void hasWidget_existingWidget_returnsTrue() {

        String widgetName = "Test Widget";
        Widget widget = () -> "testRender";
        widgetRegistry.registerWidget(widgetName, widget);

        boolean result = widgetRegistry.hasWidget(widgetName);

        assertTrue(result);
    }

    @Test
    void hasWidget_nonExistingWidget_returnsFalse() {

        boolean result = widgetRegistry.hasWidget("Non-existing Widget");

        assertFalse(result);
    }

    @Test
    void getRegisteredWidgetNames_returnsCorrectSet() {

        widgetRegistry.registerWidget("Widget 1", () -> "render1");
        widgetRegistry.registerWidget("Widget 2", () -> "render2");

        Set<String> names = widgetRegistry.getRegisteredWidgetNames();

        assertEquals(2, names.size());
        assertTrue(names.contains("widget-1"));
        assertTrue(names.contains("widget-2"));
    }

    @Test
    void formatWidgetName_correctlyFormatsName() {

        String widgetName = "Test Widget 123!@#";
        Widget widget = () -> "testRender";

        widgetRegistry.registerWidget(widgetName, widget);
        Set<String> names = widgetRegistry.getRegisteredWidgetNames();

        assertTrue(names.contains("test-widget-123"));
    }
}