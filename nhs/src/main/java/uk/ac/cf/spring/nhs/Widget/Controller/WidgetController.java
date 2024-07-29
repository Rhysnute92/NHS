package uk.ac.cf.spring.nhs.Widget.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;
import uk.ac.cf.spring.nhs.Widget.Service.WidgetService;

@RestController
@RequestMapping("/api/widgets")
public class WidgetController {
    @Autowired
    private WidgetService widgetService;

    @GetMapping
    public ResponseEntity<List<Widget>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Widget> getWidgetById(@PathVariable String id) {
        return widgetService.getWidgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
