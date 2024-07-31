package uk.ac.cf.spring.nhs.Widget.Model;

public class TaskCompletionWidget implements Widget {
    @Override
    public String render() {
        return "fragments/widgets/taskCompletion.html";
    }
}
