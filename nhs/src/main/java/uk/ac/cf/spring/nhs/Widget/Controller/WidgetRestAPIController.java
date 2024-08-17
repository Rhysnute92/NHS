package uk.ac.cf.spring.nhs.Widget.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserTask.DTO.UserTaskDTO;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@RestController
public class WidgetRestAPIController {

    @Autowired
    private AuthenticationInterface authenticationFacade;

    @Autowired
    private UserTaskService userTaskService;

    /**
     * Endpoint to fetch task completion data for the task widget.
     *
     * @param userId The ID of the user.
     * @param day    The day of the week (optional, defaults to today).
     * @return A UserTaskDTO with task data.
     */
    @GetMapping("/api/widgets/task-completion/data")
    public UserTaskDTO getTaskCompletionData(
            @RequestParam(name = "day", required = false) Integer day) {

        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        Long userId = ((CustomUserDetails) principal).getUserId();

        if (day == null) {
            day = LocalDate.now().getDayOfMonth();
            System.out.println("Defaulted to current day: " + day);
        } else {
            System.out.println("Requested day: " + day);
        }

        int totalTasks = userTaskService.getTasksForUser(userId).size();
        int completedTasks = userTaskService.countCompletedTasksForday(userId, day);

        System.out.println("Total tasks for user " + userId + ": " + totalTasks);
        System.out.println("Completed tasks for user " + userId + " on day " + day + ": " + completedTasks);

        return new UserTaskDTO(totalTasks, completedTasks);
    }

}