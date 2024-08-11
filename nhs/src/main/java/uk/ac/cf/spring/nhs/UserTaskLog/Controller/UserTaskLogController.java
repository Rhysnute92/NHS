package uk.ac.cf.spring.nhs.UserTaskLog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Service.UserTaskLogService;

@RestController
@RequestMapping("/api/user-task-logs")
public class UserTaskLogController {

    @Autowired
    private UserTaskLogService userTaskLogService;

    /**
     * Creates a new {@link UserTaskLog} entry.
     *
     * @param userTaskLog The {@link UserTaskLog} to create.
     * @return The created {@link UserTaskLog}.
     */
    @PostMapping
    public ResponseEntity<UserTaskLog> createUserTaskLog(@RequestBody UserTaskLog userTaskLog) {
        UserTaskLog createdLog = userTaskLogService.createUserTaskLog(userTaskLog);
        return ResponseEntity.ok(createdLog);
    }

    /**
     * Retrieves a {@link UserTaskLog} by its ID.
     *
     * @param id The ID of the {@link UserTaskLog} to retrieve.
     * @return The retrieved {@link UserTaskLog}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserTaskLog> getUserTaskLogById(@PathVariable Long id) {
        UserTaskLog log = userTaskLogService.getUserTaskLogById(id);
        return ResponseEntity.ok(log);
    }

    /**
     * Retrieves all {@link UserTaskLog} entries.
     *
     * @return A list of all {@link UserTaskLog} entries.
     */
    @GetMapping
    public ResponseEntity<List<UserTaskLog>> getAllUserTaskLogs() {
        List<UserTaskLog> logs = userTaskLogService.getAllUserTaskLogs();
        return ResponseEntity.ok(logs);
    }

    /**
     * Updates an existing {@link UserTaskLog}.
     *
     * @param id         The ID of the {@link UserTaskLog} to update.
     * @param updatedLog The updated {@link UserTaskLog} data.
     * @return The updated {@link UserTaskLog}.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserTaskLog> updateUserTaskLog(
            @PathVariable Long id,
            @RequestBody UserTaskLog updatedLog) {
        UserTaskLog log = userTaskLogService.updateUserTaskLog(id, updatedLog);
        return ResponseEntity.ok(log);
    }

    /**
     * Deletes a {@link UserTaskLog} by its ID.
     *
     * @param id The ID of the {@link UserTaskLog} to delete.
     * @return HTTP response with no content if successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserTaskLog(@PathVariable Long id) {
        userTaskLogService.deleteUserTaskLog(id);
        return ResponseEntity.noContent().build();
    }
}
