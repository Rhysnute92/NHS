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

    /**
     * Retrieves a list of user widgets for a given user ID.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity containing a list of UserWidgets, or a bad request
     *         response if the user ID is null or less than or equal to zero
     */
    @GetMapping("/api/user-widgets/{userId}")
    public ResponseEntity<List<UserWidget>> getUserWidgets(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        List<UserWidget> userWidgets = userWidgetService.getUserWidgets(userId);
        System.out.println("Fetched user widgets for user " + userId + ": " + userWidgets);
        return ResponseEntity.ok(userWidgets);
    }
}
