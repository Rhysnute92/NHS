package uk.ac.cf.spring.nhs.Widget.Repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    }
}
