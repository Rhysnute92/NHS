package uk.ac.cf.spring.nhs.UserTask.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import uk.ac.cf.spring.nhs.Common.util.BitmaskUtility;
import uk.ac.cf.spring.nhs.Common.util.DateUtils;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Repository.JpaUserTaskRepository;

@Service
public class UserTaskService {

    @Autowired
    private JpaUserTaskRepository userTaskRepository;

    @Transactional
    public void markTaskCompleted(UserTask userTask) {
        int currentDay = DateUtils.getCurrentDayOfMonth();
        int updatedBitmask = BitmaskUtility.setBit(userTask.getBitmask(), currentDay);
        userTask.setBitmask(updatedBitmask);
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
     * Retrieves a list of user tasks associated with the specified user ID.
     *
     * @param userID the ID of the user to retrieve tasks for
     * @return a list of user tasks for the specified user
     */
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
