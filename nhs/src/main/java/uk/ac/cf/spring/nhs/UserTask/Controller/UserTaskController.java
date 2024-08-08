package uk.ac.cf.spring.nhs.UserTask.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@RestController
@RequestMapping("/usertask")
public class UserTaskController {

    @Autowired
    private UserTaskService usertaskService;

    @GetMapping("/{userID}")
    public ResponseEntity<List<UserTask>> getTasksForUser(@PathVariable("userID") Long userID) {
        List<UserTask> userTasks = usertaskService.getTasksForUser(userID);
        return ResponseEntity.ok(userTasks);
    }

    @PostMapping
    public ResponseEntity<UserTask> assignTaskToUser(@RequestBody UserTask userTask) {
        UserTask savedUserTask = usertaskService.assignTaskToUser(userTask);
        return ResponseEntity.ok(savedUserTask);
    }

}
