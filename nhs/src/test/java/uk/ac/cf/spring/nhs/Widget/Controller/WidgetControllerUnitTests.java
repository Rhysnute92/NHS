package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

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


}
