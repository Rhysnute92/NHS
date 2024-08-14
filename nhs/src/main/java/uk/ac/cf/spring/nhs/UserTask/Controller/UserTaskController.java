package uk.ac.cf.spring.nhs.UserTask.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@RestController
@RequestMapping("/usertask")
public class UserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    /**
     * Retrieves a list of user tasks associated with the specified user ID.
     *
     * @param userID the ID of the user to retrieve tasks for
     * @return a list of user tasks for the specified user
     */
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<UserTask>> getTasksForUser(@PathVariable("userID") Long userID) {
        List<UserTask> userTasks = userTaskService.getTasksForUser(userID);
        return ResponseEntity.ok(userTasks);
    }

    /**
     * Assigns a task to a user.
     *
     * @param userTask the user task details to be assigned
     * @return the created user task details
     */
    @PostMapping
    public ResponseEntity<UserTask> assignTaskToUser(@RequestBody UserTask userTask) {
        UserTask createdUserTask = userTaskService.assignTaskToUser(userTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserTask);
    }

    /**
     * Retrieves a user task by its ID from the database.
     *
     * @param userTaskID the ID of the user task to retrieve
     * @return the user task with the specified ID, wrapped in a ResponseEntity
     */
    @GetMapping("/task/{userTaskID}")
    public ResponseEntity<UserTask> getUserTaskById(@PathVariable("userTaskID") Long userTaskID) {
        UserTask userTask = userTaskService.getUserTaskById(userTaskID);
        return ResponseEntity.ok(userTask);
    }

    /**
     * Updates an existing user task with the provided task details.
     *
     * @param userTaskID      the ID of the user task to update
     * @param userTaskDetails the updated task details
     * @return the updated user task
     */
    @PutMapping("/task-update/{userTaskID}")
    public ResponseEntity<UserTask> updateUserTask(@PathVariable("userTaskID") Long userTaskID,
            @RequestBody UserTask userTaskDetails) {
        UserTask updatedUserTask = userTaskService.updateUserTask(userTaskID, userTaskDetails);
        return ResponseEntity.ok(updatedUserTask);
    }

    @PutMapping("/task-toggle/{userTaskID}")
    public ResponseEntity<Void> toggleTaskCompletion(@PathVariable("userTaskID") Long userTaskID) {
        UserTask userTask = userTaskService.getUserTaskById(userTaskID);
        userTaskService.toggleTaskCompletion(userTask);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a user task from the database.
     *
     * @param userTaskID the ID of the user task to delete
     * @return an empty response entity indicating success
     */
    @DeleteMapping("/task-delete/{userTaskID}")
    public ResponseEntity<Void> deleteUserTask(@PathVariable("userTaskID") Long userTaskID) {
        userTaskService.deleteUserTask(userTaskID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets the count of tasks completed by a user on a specific day.
     *
     * @param userId The ID of the user.
     * @param day    The day of the month (1-based index).
     * @return The number of tasks completed by the user on the given day.
     */
    @GetMapping("/{userId}/completed-tasks/{day}")
    public ResponseEntity<Integer> countCompletedTasksForDay(
            @PathVariable Long userId,
            @PathVariable int day) {
        int completedTasks = userTaskService.countCompletedTasksForday(userId, day);
        return ResponseEntity.ok(completedTasks);
    }

}
