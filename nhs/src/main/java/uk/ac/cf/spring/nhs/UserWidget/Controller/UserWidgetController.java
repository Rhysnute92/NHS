package uk.ac.cf.spring.nhs.UserWidget.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;

@RestController
public class UserWidgetController {
    @Autowired
    private UserWidgetService userWidgetService;

    /**
     * Retrieves a list of user widgets for a given user ID.
     *
     * @param UserID the ID of the user
     * @return a ResponseEntity containing a list of UserWidgets, or a bad request
     *         response if the user ID is null or less than or equal to zero
     */
    @GetMapping("/api/user-widgets/{UserID}")
    public ResponseEntity<List<UserWidgets>> getUserWidgets(@PathVariable Long UserID) {
        if (UserID == null || UserID <= 0) {
            return ResponseEntity.badRequest().build();
        }
        List<UserWidgets> userWidgets = userWidgetService.getUserWidgets(UserID);
        System.out.println("Fetched user widgets for user " + UserID + ": " + userWidgets);
        return ResponseEntity.ok(userWidgets);
    }
}
