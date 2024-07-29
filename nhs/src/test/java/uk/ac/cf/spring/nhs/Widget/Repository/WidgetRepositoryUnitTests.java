import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Service.WidgetService;

public class WidgetRepositoryUnitTests {

    @Mock
    private WidgetRepositoryInterface widgetRepository;

    @InjectMocks
    private WidgetService widgetService;

    public WidgetRepositoryUnitTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Widget> widgets = Arrays.asList(new Widget("1", "Widget 1"), new Widget("2", "Widget 2"));
        when(widgetRepository.findAll()).thenReturn(widgets);

        List<Widget> result = widgetService.getAllWidgets();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        Widget widget = new Widget("1", "Widget 1");
        when(widgetRepository.findById("1")).thenReturn(Optional.of(widget));

        Widget result = widgetService.getWidgetById("1");
        assertNotNull(result);
        assertEquals("Widget 1", result.getName());
    }
}
