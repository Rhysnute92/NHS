package uk.ac.cf.spring.nhs.UserWidget.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;

@RestController
public class UserWidgetController {

    @Autowired
    private UserWidgetService userWidgetService;

    @GetMapping("/api/user-widgets/{userId}")
    public ResponseEntity<List<UserWidget>> getUserWidgets(@PathVariable Long userId) {
        return ResponseEntity.ok(userWidgetService.getUserWidgets(userId));
    }
}