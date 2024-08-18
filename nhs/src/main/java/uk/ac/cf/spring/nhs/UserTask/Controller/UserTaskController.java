package uk.ac.cf.spring.nhs.UserTask.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.Security.AuthenticationFacade;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@RestController
@RequestMapping("/usertask")
public class UserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private AuthenticationFacade authenticationFacade; // To access the current authenticated user

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserTask>> getTasksForUser() {
        Long userId = getCurrentUserId();
        List<UserTask> userTasks = userTaskService.getTasksForUser(userId);
        return ResponseEntity.ok(userTasks);
    }

    @PostMapping
    public ResponseEntity<UserTask> assignTaskToUser(@RequestBody UserTask userTask) {
        userTask.setUserID(getCurrentUserId()); // Set the current user ID to the task userID //TODO: will need to make
                                                // sure this is changed for provider assigned userTask
        UserTask createdUserTask = userTaskService.assignTaskToUser(userTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserTask);
    }

    @GetMapping("/task/{userTaskID}")
    public ResponseEntity<UserTask> getUserTaskById(@PathVariable("userTaskID") Long userTaskID) {
        UserTask userTask = userTaskService.getUserTaskById(userTaskID);
        return ResponseEntity.ok(userTask);
    }

    @PutMapping("/task-update/{userTaskID}")
    public ResponseEntity<UserTask> updateUserTask(@PathVariable("userTaskID") Long userTaskID,
            @RequestBody UserTask userTaskDetails) {
        userTaskDetails.setUserID(getCurrentUserId()); // Ensure the task belongs to the current user
        UserTask updatedUserTask = userTaskService.updateUserTask(userTaskID, userTaskDetails);
        return ResponseEntity.ok(updatedUserTask);
    }

    @PutMapping("/task-toggle/{userTaskID}")
    public ResponseEntity<Map<String, String>> toggleTaskCompletion(@PathVariable("userTaskID") Long userTaskID) {
        UserTask userTask = userTaskService.getUserTaskById(userTaskID);
        userTaskService.toggleTaskCompletion(userTask);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/task-delete/{userTaskID}")
    public ResponseEntity<Void> deleteUserTask(@PathVariable("userTaskID") Long userTaskID) {
        userTaskService.deleteUserTask(userTaskID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed-tasks/{day}")
    public ResponseEntity<Integer> countCompletedTasksForDay(@PathVariable int day) {
        Long userId = getCurrentUserId();
        int completedTasks = userTaskService.countCompletedTasksForday(userId, day);
        return ResponseEntity.ok(completedTasks);
    }

    private Long getCurrentUserId() {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        return ((CustomUserDetails) principal).getUserId();
    }
}
