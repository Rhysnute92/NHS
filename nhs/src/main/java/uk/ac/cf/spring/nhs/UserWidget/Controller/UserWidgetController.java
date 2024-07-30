package uk.ac.cf.spring.nhs.UserWidget.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;

@RestController
@RequestMapping("/api/user-widgets")
public class UserWidgetController {

    @Autowired
    private UserWidgetService userWidgetService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserWidget>> getUserWidgets(@PathVariable String userId) {
        return ResponseEntity.ok(userWidgetService.getUserWidgets(userId));
    }

    @PostMapping
    public ResponseEntity<UserWidget> addUserWidget(@RequestBody UserWidget userWidget) {
        return ResponseEntity.ok(userWidgetService.addUserWidget(userWidget));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserWidget(@PathVariable String id) {
        userWidgetService.removeUserWidget(id);
        return ResponseEntity.ok().build();
    }
}
