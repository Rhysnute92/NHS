package uk.ac.cf.spring.nhs.Widget.Model;

public interface Widget {
    // Should return the path to the fragment for rendering the widget
    String render();

    // get associated script
    default String getScript() {
        // By default, no script is returned.
        return null;
    }
}
