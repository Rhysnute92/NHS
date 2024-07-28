package uk.ac.cf.spring.nhs.Widget.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;

public class TaskCompletionWidgetUnitTests {

    private TaskCompletionWidget taskCompletionWidget;

    /**
     * Sets up the TaskCompletionWidget object before each test.
     *
     * @return void
     */
    @BeforeEach
    public void setUp() {
        taskCompletionWidget = new TaskCompletionWidget();
    }

    /**
     * Tests the getId method of the TaskCompletionWidget class.
     *
     * @return void
     */
    @Test
    public void testGetId() {
        assertEquals("task-completion-widget", taskCompletionWidget.getId());
    }

    /**
     * Tests the getTitle method of the TaskCompletionWidget class.
     *
     * @return void
     */
    @Test
    public void testGetTitle() {
        assertEquals("Task Completion", taskCompletionWidget.getTitle());
    }

    /**
     * Tests the getHtmlContent method of the TaskCompletionWidget class.
     *
     * @return void
     */
    @Test
    public void testGetHtmlContent() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        String expectedHtml = "<div id='taskCompletion-widget' class='task-completion-widget'>" +
                "<div class='circle-container'>" +
                "<div class='circle'>" +
                "<span class='percentage'></span>" +
                "</div>" +
                "</div>" +
                "<button class='complete-task-button'>Complete Task</button>" +
                "</div>";
        assertEquals(expectedHtml, taskCompletionWidget.getHtmlContent(mockRequest));
    }
}
