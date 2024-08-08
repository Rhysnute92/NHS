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

    @GetMapping("/{userID}")
    public ResponseEntity<List<UserTask>> getTasksForUser(@PathVariable("userID") Long userID) {
        List<UserTask> userTasks = userTaskService.getTasksForUser(userID);
        return ResponseEntity.ok(userTasks);
    }

    @PostMapping
    public ResponseEntity<UserTask> assignTaskToUser(@RequestBody UserTask userTask) {
        UserTask createdUserTask = userTaskService.assignTaskToUser(userTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserTask);
    }

    @GetMapping("/{userTaskID}")
    public ResponseEntity<UserTask> getUserTaskById(@PathVariable("userTaskID") Long userTaskID) {
        UserTask userTask = userTaskService.getUserTaskById(userTaskID);
        return ResponseEntity.ok(userTask);
    }

    @PutMapping("{userTaskID}")
    public ResponseEntity<UserTask> updateUserTask(@PathVariable("userTaskID") Long userTaskID,
            @RequestBody UserTask userTaskDetails) {
        UserTask updatedUserTask = userTaskService.updateUserTask(userTaskID, userTaskDetails);
        return ResponseEntity.ok(updatedUserTask);
    }

    @DeleteMapping("{userTaskID}")
    public ResponseEntity<Void> deleteUserTask(@PathVariable("userTaskID") Long userTaskID) {
        userTaskService.deleteUserTask(userTaskID);
        return ResponseEntity.noContent().build();
    }

}
