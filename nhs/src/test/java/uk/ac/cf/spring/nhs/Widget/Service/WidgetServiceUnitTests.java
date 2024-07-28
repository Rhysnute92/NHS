package uk.ac.cf.spring.nhs.Widget.Service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Widget.Factory.WidgetFactory;
import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Repository.WidgetRepositoryInterface;

public class WidgetServiceUnitTests {

    private WidgetService widgetService;
    private WidgetRepositoryInterface WidgetRepository;
    private WidgetFactory widgetFactory;

    @BeforeEach
    public void setUp() {
        widgetRepository = mock(WidgetRepositoryInterface.class);
        widgetFactory = mock(WidgetFactory.class);
        widgetService = new WidgetService(widgetRepository, widgetFactory);
    }

    @Test
    public void testGetAllWidgets() {
        Widget widget1 = mock(Widget.class);
        Widget widget2 = mock(Widget.class);

        when(widgetRepository.findall()).thenReturn(Arrays.asList(widget1, widget2));
    }
    
}
