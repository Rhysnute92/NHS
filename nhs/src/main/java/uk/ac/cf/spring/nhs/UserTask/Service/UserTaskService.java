package uk.ac.cf.spring.nhs.UserTask.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.cf.spring.nhs.Common.util.BitmaskUtility;
import uk.ac.cf.spring.nhs.Common.util.DateUtils;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Repository.JpaUserTaskRepository;
import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;

@Service
public class UserTaskService {

    @Autowired
    private JpaUserTaskRepository userTaskRepository;

    /**
     * Marks a specific task as completed for the current day.
     * 
     * <p>
     * This method updates the bitmask of a given {@link UserTask} to reflect that
     * the task was
     * completed on the current day of the month. The bitmask is an integer where
     * each bit represents
     * a day in the month (e.g., the 1st day is represented by the 1st bit, the 2nd
     * day by the 2nd bit, and so on).
     * </p>
     * 
     * <p>
     * For example, if today is the 5th day of the month and the user completes the
     * task,
     * the bitmask will be updated to mark the 5th bit as "1" (completed). This
     * bitmask is
     * then saved back to the database.
     * </p>
     * 
     * @param userTask The {@link UserTask} entity that needs to be marked as
     *                 completed for today.
     * 
     *                 <h3>Example Usage:</h3>
     * 
     *                 <pre>{@code
     * // Retrieve the UserTask object using the service
     * UserTask userTask = userTaskService.getUserTaskById(1L);
     * 
     * // Mark the task as completed for today
     * userTaskService.markTaskCompleted(userTask);
     * 
     * // If today is the 5th of the month, the 5th bit in the task's bitmask will be set to "1".
     * // For example, if the previous bitmask was 00000000000000000000000000000000 (binary for 0),
     * // it will now be 00000000000000000000000000010000 (binary for 16), where the 5th bit is set.
     * }</pre>
     */
    @Transactional
    public void markTaskCompleted(UserTask userTask) {
        int currentDay = DateUtils.getCurrentDayOfMonth();
        int updatedBitmask = BitmaskUtility.setBit(userTask.getBitmask(), currentDay);
        userTask.setBitmask(updatedBitmask);
        userTaskRepository.save(userTask);
    }

    @Transactional(readOnly = true)
    public boolean isTaskCompleted(UserTask userTask) {
        int currentDay = DateUtils.getCurrentDayOfMonth();
        return BitmaskUtility.isBitSet(userTask.getBitmask(), currentDay);
    }

    @Transactional
    public void archiveAndResetMonthlyBitmask(UserTask userTask) {
        String monthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        UserTaskLog log = new UserTaskLog();
        log.setUserTask(userTask);
        log.setBitmask(userTask.getBitmask());
        log.setMonthYear(monthYear);
        log.setCreatedAt(LocalDateTime.now());

        userTaskLogService.saveUserTaskLog(log);
        userTask.setBitmask(BitmaskUtility.resetBitmask());
        userTaskRepository.save(userTask);
    }
    // CRUD functions

    /**
     * Assigns a task to a user by saving the user task details in the database.
     *
     * @param userTask the user task details to be saved
     * @return the saved user task details
     */
    public UserTask assignTaskToUser(UserTask userTask) {
        return userTaskRepository.save(userTask); // TODO: add exception handling
    }

    /**
     * 
     * Retrieves a list of user tasks associated with the specified user ID.
     *
     * @param userID the ID of the user to retrieve tasks for
     * @return a list of user tasks for the specified user
     */
    @Transactional(readOnly = true)
    public List<UserTask> getTasksForUser(Long userID) {
        return userTaskRepository.findByUserID(userID); // TODO: add exception handling
    }

    /**
     * Retrieves a user task by its ID from the database.
     *
     * @param userTaskID the ID of the user task to retrieve
     * @return the user task with the specified ID, or throws an exception if not
     *         found
     */
    @Transactional(readOnly = true)
    public UserTask getUserTaskById(Long userTaskID) {
        return userTaskRepository.findById(userTaskID)
                .orElseThrow(() -> new NoSuchElementException("UserTask not found"));
    }

    /**
     * Updates a user task with the specified task details.
     *
     * @param userTaskID      the ID of the user task to update
     * @param userTaskDetails the updated task details
     * @return the updated user task
     */
    public UserTask updateUserTask(Long userTaskID, UserTask userTaskDetails) {
        UserTask userTask = getUserTaskById(userTaskID);
        userTask.setTask(userTaskDetails.getTask());
        userTask.setUserID(userTaskDetails.getUserID());
        userTask.setBitmask(userTaskDetails.getBitmask());
        return userTaskRepository.save(userTask); // TODO: should this return anything?
    }

    /**
     * Deletes a user task from the database.
     *
     * @param userTaskID the ID of the user task to delete
     */
    public void deleteUserTask(Long userTaskID) {
        UserTask userTask = getUserTaskById(userTaskID);
        userTaskRepository.delete(userTask); // TODO: add exception handling
    }

}
