/* package uk.ac.cf.spring.nhs.UserWidget.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaUserWidgetRepositoryUnitTests {

    @Autowired
    private JpaUserWidgetRepository userWidgetRepository;

    @Test
    void findAllByUserID_validUserId_returnsWidgets() {
        // Arrange
        UserWidget widget1 = new UserWidget();
        widget1.setUserID(1L);
        widget1.setWidgetName("Widget 1");
        widget1.setPosition(1);

        UserWidget widget2 = new UserWidget();
        widget2.setUserID(1L);
        widget2.setWidgetName("Widget 2");
        widget2.setPosition(2);

        userWidgetRepository.save(widget1);
        userWidgetRepository.save(widget2);

        // Act
        List<UserWidget> widgets = userWidgetRepository.findAllByUserID(1L);

        // Assert
        assertEquals(2, widgets.size());
        assertTrue(widgets.stream().anyMatch(widget -> widget.getWidgetName().equals("Widget 1")));
        assertTrue(widgets.stream().anyMatch(widget -> widget.getWidgetName().equals("Widget 2")));
    }

    @Test
    void findAllByUserID_invalidUserId_returnsEmptyList() {
        // Act
        List<UserWidget> widgets = userWidgetRepository.findAllByUserID(999L);

        // Assert
        assertTrue(widgets.isEmpty());
    }

    @Test
    void findAllByUserID_noWidgetsForUserId_returnsEmptyList() {
        // Arrange
        UserWidget widget = new UserWidget();
        widget.setUserID(2L);
        widget.setWidgetName("Widget 3");
        widget.setPosition(1);
        userWidgetRepository.save(widget);

        // Act
        List<UserWidget> widgets = userWidgetRepository.findAllByUserID(1L);

        // Assert
        assertTrue(widgets.isEmpty());
    }

    @Test
    void findAllByUserID_multipleUsers_returnsCorrectWidgets() {
        // Arrange
        UserWidget widget1 = new UserWidget();
        widget1.setUserID(1L);
        widget1.setWidgetName("Widget 1");
        widget1.setPosition(1);

        UserWidget widget2 = new UserWidget();
        widget2.setUserID(2L);
        widget2.setWidgetName("Widget 2");
        widget2.setPosition(2);

        userWidgetRepository.save(widget1);
        userWidgetRepository.save(widget2);

        // Act
        List<UserWidget> widgetsForUser1 = userWidgetRepository.findAllByUserID(1L);
        List<UserWidget> widgetsForUser2 = userWidgetRepository.findAllByUserID(2L);

        // Assert
        assertEquals(1, widgetsForUser1.size());
        assertEquals(1, widgetsForUser2.size());
        assertEquals("Widget 1", widgetsForUser1.get(0).getWidgetName());
        assertEquals("Widget 2", widgetsForUser2.get(0).getWidgetName());
    }
}
 */