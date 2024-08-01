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

    /**
     * Service for user widgets.
     */
    @Autowired
    private UserWidgetService userWidgetService;

    /**
     * Retrieves user widgets for a given user ID.
     * 
     * @param userId the ID of the user
     * @return the list of user widgets
     */
    @GetMapping("/api/user-widgets/{userId}")
    public ResponseEntity<List<UserWidget>> getUserWidgets(@PathVariable Long userId) {
        List<UserWidget> userWidgets = userWidgetService.getUserWidgets(userId);
        System.out.println("Fetched user widgets for user " + userId + ": " + userWidgets);
        return ResponseEntity.ok(userWidgets);
    }
}
