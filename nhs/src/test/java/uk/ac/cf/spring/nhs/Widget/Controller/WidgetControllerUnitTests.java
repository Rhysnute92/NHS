package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

class WidgetControllerUnitTests {

    @Mock
    private WidgetRegistry widgetRegistry;

    @Mock
    private Model model;

    @InjectMocks
    private WidgetController widgetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWidgetContent_returnsWidgetContent_whenWidgetExists() {
        Widget mockWidget = mock(Widget.class);
        when(mockWidget.render()).thenReturn("widget-content");
        when(widgetRegistry.getWidget("test-widget")).thenReturn(mockWidget);

        String result = widgetController.getWidgetContent("test-widget", model);

        assertEquals("widget-content", result);
        verify(model).addAttribute("widget", mockWidget);
    }

    @Test
    void getWidgetContent_returns404_whenWidgetDoesNotExist() {
        when(widgetRegistry.getWidget("non-existent-widget")).thenReturn(null);

        String result = widgetController.getWidgetContent("non-existent-widget", model);

        assertEquals("error/404", result);
    }
}