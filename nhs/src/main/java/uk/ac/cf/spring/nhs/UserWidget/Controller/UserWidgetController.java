package uk.ac.cf.spring.nhs.UserWidget.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;

@RestController
public class UserWidgetController {
    @Autowired
    private UserWidgetService userWidgetService;

    @Autowired
    private AuthenticationInterface authenticationFacade;

    /**
     * Retrieves a list of user widgets for a given user ID.
     *
     * @param UserID the ID of the user
     * @return a ResponseEntity containing a list of UserWidgets, or a bad request
     *         response if the user ID is null or less than or equal to zero
     */
    @GetMapping("/api/user-widgets")
    public ResponseEntity<List<UserWidgets>> getUserWidgets() {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        List<UserWidgets> userWidgets = userWidgetService.getUserWidgets(userId);
        System.out.println("Fetched user widgets for user " + userId + ": " + userWidgets);
        return ResponseEntity.ok(userWidgets);
    }

    @DeleteMapping("/api/user-widgets/delete")
    public ResponseEntity<Void> deleteUserWidgets(@RequestBody List<Long> widgetIds) {
        if (widgetIds == null || widgetIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        userWidgetService.deleteUserWidgetsByIdList(widgetIds);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a list of user widgets for a given user ID.
     *
     * @param widgetNames a list of widget names to be added
     * @return a ResponseEntity containing no content if the operation was
     *         successful,
     *         a bad request response if the user ID is null or less than or equal
     *         to zero,
     *         or a bad request response if the widgetNames list is null or empty
     */
    @PostMapping("/api/user-widgets/add")
    public ResponseEntity<Void> addUserWidgets(@RequestBody List<String> widgetNames) {
        if (widgetNames == null || widgetNames.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();

        widgetNames.forEach(widgetName -> {
            UserWidgets userWidget = new UserWidgets();
            userWidget.setUserID(userId);
            userWidget.setWidgetName(widgetName);
            userWidgetService.saveUserWidget(userWidget);
        });

        return ResponseEntity.noContent().build();
    }

}
