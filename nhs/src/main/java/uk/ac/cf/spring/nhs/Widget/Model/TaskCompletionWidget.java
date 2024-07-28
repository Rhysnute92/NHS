package uk.ac.cf.spring.nhs.Widget.Model;

import jakarta.servlet.http.HttpServletRequest;

public class TaskCompletionWidget implements Widget {

    @Override
    public String getId() {
        return "task-completion-widget";
    }

    @Override
    public String getTitle() {
        return "Task Completion tracker";
    }

    @Override
    public String getHtmlContent(HttpServletRequest request) {
        return "<div id='taskCompletion-widget' class='task-completion-widget'>" +
                "<div class='circle-container'>" +
                "<div class='circle'>" +
                "<span class='percentage'></span>" +
                "</div>" +
                "</div>" +
                "<button class='complete-task-button'>Complete Task</button>" +
                "</div>";
    }

    @Override
    public String getData() {
        // TODO: create fake JSON to return
        // TODO: later create logic to return data from database
        return null;
    }
}
