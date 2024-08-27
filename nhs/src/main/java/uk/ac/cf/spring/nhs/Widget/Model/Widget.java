package uk.ac.cf.spring.nhs.Widget.Model;

public interface Widget {
    // Should return the path to the fragment for rendering the widget
    String render();

    // Should return the path to the icon for the widget
    String getIconPath();
}
