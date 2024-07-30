package uk.ac.cf.spring.nhs.Widget.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Repository.WidgetRepository;

@Service
public class WidgetService {
    @Autowired
    private WidgetRepository widgetRepository;

    public List<Widget> getAllWidgets() {
        return widgetRepository.findAll();
    }

    public Optional<Widget> getWidgetById(String id) {
        return widgetRepository.findById(id);
    }

    public void createWidget(Widget widget) {
        widgetRepository.save(widget);
    }

    /**
     * Updates the widget in the repository.
     *
     * @param widget The widget to update. The ID of the widget is used to identify which widget to update.
     */
/*     public void updateWidget(Widget widget) {
        // Find the widget with the same ID as the one passed in (if it exists)
        widgetRepository.findById(widget.getId())
                // If it exists, update it with the new data
                .ifPresent(w -> {
                    w.setName(widget.getTitle());
                    w.setDescription(widget.getDescription());
                    widgetRepository.save(w);
                });
        widgetRepository.update(widget);
    } */

    /**
     * Deletes the widget with the given ID from the repository.
     * If no widget with the given ID exists, this method does nothing.
     *
     * @param id the ID of the widget to delete
     */
    public void deleteWidget(String id) {
        // Find the widget with the given ID (if it exists)
        widgetRepository.findById(id)
                // If it exists, delete it from the repository
                .ifPresent(widgetRepository::delete);
    }
}
