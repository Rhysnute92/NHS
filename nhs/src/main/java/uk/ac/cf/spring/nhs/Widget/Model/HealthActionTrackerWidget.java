package uk.ac.cf.spring.nhs.Widget.Model;

public class HealthActionTrackerWidget implements Widget {

    private String iconPath;

    public HealthActionTrackerWidget() {
        this.iconPath = "/images/widget_icons/health-action-tracker.jpg";
    }

    /**
     * Returns a string representing the path to the fragment for rendering the task
     * completion widget.
     *
     * @return a string representing the path to the fragment for rendering the task
     *         completion widget
     */
    @Override
    public String render() {
        return "fragments/widgets/taskCompletion";
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

}
