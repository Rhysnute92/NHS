package uk.ac.cf.spring.nhs.Widget.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Registry.WidgetRegistry;

@RestController
public class WidgetController {

    @GetMapping("/api/widgets/{widgetName}")
    public ResponseEntity<String> getWidgetContent(@PathVariable String widgetName) {
        Widget widget = WidgetRegistry.getWidget(widgetName);
        if (widget != null) {
            return ResponseEntity.ok(widget.render());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
