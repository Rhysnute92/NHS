package uk.ac.cf.spring.nhs.Widget.Model;

public class TaskCompletionWidget implements Widget {
    /**
     * Returns a string representing the path to the fragment for rendering the task completion widget.
     *
     * @return a string representing the path to the fragment for rendering the task completion widget
     */
    @Override
    public String render() {
        return "fragments/widgets/taskCompletion";
    }

    @Override
    public String getScript() {
        return "taskCompletion";
    }
}
