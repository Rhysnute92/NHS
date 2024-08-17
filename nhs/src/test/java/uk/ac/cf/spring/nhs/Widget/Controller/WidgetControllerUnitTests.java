package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.test.util.ReflectionTestUtils;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

class WidgetControllerUnitTests {

    @InjectMocks
    private WidgetController widgetController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
 
        String viewName = widgetController.getWidgetContent("testWidget", model);

       
        assertEquals("widgetContent", viewName);
    }

    @Test
    void getWidgetContent_widgetDoesNotExist_returnsErrorView() {
        
        String viewName = widgetController.getWidgetContent("nonExistentWidget", model);

       
        assertEquals("error/404", viewName);
    }

    @Test
    void getWidgetContent_widgetWithNoRenderMethod_returnsErrorView() {
        
        Widget widgetWithNoRenderMethod = mock(Widget.class);
        Map<String, Widget> mockWidgets = new HashMap<>();
        mockWidgets.put("noRenderWidget", widgetWithNoRenderMethod);
        ReflectionTestUtils.setField(WidgetRegistry.class, "widgets", mockWidgets);

        
        String viewName = widgetController.getWidgetContent("noRenderWidget", model);

        
        assertEquals("error/404", viewName);
    }
}
