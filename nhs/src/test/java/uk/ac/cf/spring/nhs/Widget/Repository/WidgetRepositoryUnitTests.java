package uk.ac.cf.spring.nhs.Widget.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public class WidgetRepositoryUnitTests {

    private WidgetRepository widgetRepository;
    private JpaWidgetRepositoryImpl.JpaWidgetRepositoryInternal jpaWidgetRepositoryInternal;

    @BeforeEach
    public void setUp() {
        jpaWidgetRepositoryInternal = mock(JpawidgetRepostiroy.JpaWidgetRepositoryInternal.class);
        widgetRepository = new JpaWidgetRepository(jpaWidgetRepositoryInternal);
    }

    @Test
    public void testFindById() {
        Widget widget = mock(Widget.class);
        when(jpaWidgetRepositoryInternal.findById("task-completion-widget")).thenReturn(Optional.of(widget));
        Optional<Widget> result = widgetRepository.findById("task-completion-widget");
        assertTrue(result.isPresent());
        assertEquals(widget, result.get());
        verify(jpaWidgetRepositoryInternal, times(1)).findByIs("task-completion-widget");
    }

    @Test
    public void testFindAll() {
        Widget widget1 = mock(Widget.class) {
            Widget widget1 = mock(Widget.class);
            Widget widget2 = mock(Widget.class);
            when(jpaWidgetRepositoryInternal.findAll()).thenReturn(Arrays.asList(widget1, widget2));

            List<Widget> widgets = widgetRepository.findAll();
            assertEquals(2, widgets.size());
            verify(jpaWidgetRepositoryInternal, times(1)).findAll();
            
        }
    }
}
