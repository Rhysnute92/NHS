package uk.ac.cf.spring.nhs.Widget.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;

public class TaskCompletionWidgetTest {

    private TaskCompletionWidget taskCompletionWidget;

    @BeforeEach
    public void setUp() {
        taskCompletionWidget = new TaskCompletionWidget();
    }

    @Test
    public void testGetId() {
        assertEquals("task-completion-widget", taskCompletionWidget.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Task Completion", taskCompletionWidget.getTitle());
    }

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
