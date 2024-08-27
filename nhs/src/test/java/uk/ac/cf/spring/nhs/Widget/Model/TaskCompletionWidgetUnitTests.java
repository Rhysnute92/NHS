package uk.ac.cf.spring.nhs.Widget.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaskCompletionWidgetUnitTests {

    @Test
    void render_returnsCorrectFragmentPath() {
        // Arrange
        HealthActionTrackerWidget taskCompletionWidget = new HealthActionTrackerWidget();

        // Act
        String result = taskCompletionWidget.render();

        // Assert
        assertEquals("fragments/widgets/taskCompletion", result);
    }
}
