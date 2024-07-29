package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import uk.ac.cf.spring.nhs.Widget.Model.TaskCompletionWidget;
import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Service.WidgetService;

public class WidgetControllerUnitTests {

    @Mock
    private WidgetService widgetService;

    @Mock
    private WidgetController widgetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllWidgets() {
        TaskCompletionWidget widget = new TaskCompletionWidget();
        when(widgetService.getAllWidgets()).thenReturn(List.of(widget));

        ResponseEntity<List<Widget>> response = widgetController.getAllWidgets();

        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetWidgetById() {
        TaskCompletionWidget widget = new TaskCompletionWidget();
        when(widgetService.getWidgetById("test-id")).thenReturn(Optional.of(widget));

        ResponseEntity<Widget> response = widgetController.getWidgetById("test-id");

        assertEquals(200, response.getStatusCode());
        assertEquals(widget.getId(), response.getBody().getId());
    }

    @Test
    public void testGetWidgetById_NotFound() {
        when(widgetService.getWidgetById("invalid-id")).thenReturn(Optional.empty());

        ResponseEntity<Widget> response = widgetController.getWidgetById("invalid-id");

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void testCreateWidget() {
        TaskCompletionWidget widget = new TaskCompletionWidget();
        when(widgetService.createWidget(any(Widget.class))).thenReturn(widget);

        ResponseEntity<Widget> response = widgetController.createWidget(widget);

        assertEquals(201, response.getStatusCode());
        assertEquals(widget.getId(), response.getBody().getId());
        verify(widgetService, times(1)).createWidget(any(Widget.class));
    }

    @Test
    public void testUpdateWidget() {
        TaskCompletionWidget widget = new TaskCompletionWidget();
        when(widgetService.updateWidget(any(String.class), any(Widget.class))).thenReturn(Optional.of(widget));

        ResponseEntity<Widget> response = widgetController.updateWidget("test-id", widget);

        assertEquals(200, response.getStatusCode());
        assertEquals(widget.getId(), response.getBody().getId());
    }

    @Test
    public void testUpdateWidget_NotFound() {
        TaskCompletionWidget widget = new TaskCompletionWidget();
        when(widgetService.updateWidget(any(String.class), any(Widget.class))).thenReturn(Optional.empty());

        ResponseEntity<Widget> response = widgetController.updateWidget("invalid-id", widget);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void testDeleteWidget() {
        when(widgetService.deleteWidget("test-id")).thenReturn(true);

        ResponseEntity<Void> response = widgetController.deleteWidget("test-id");

        assertEquals(200, response.getStatusCode());
        verify(widgetService, times(1)).deleteWidget("test-id");
    }

    @Test
    public void testDeleteWidget_NotFound() {
        when(widgetService.deleteWidget("invalid-id")).thenReturn(false);

        ResponseEntity<Void> response = widgetController.deleteWidget("invalid-id");

        assertEquals(404, response.getStatusCode());
    }

}
