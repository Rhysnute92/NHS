package uk.ac.cf.spring.nhs.UserTask.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Repository.JpaUserTaskRepository;

@Service
public class UserTaskService {

    @Autowired
    private JpaUserTaskRepository userTaskRepository;

    public UserTask assignTaskToUser(UserTask userTask) {
        return userTaskRepository.save(userTask);
    }

    public List<UserTask> getTasksForUser(Long userID) {
        return userTaskRepository.findByUserId(userID);
    }

    public UserTask getUserTaskById(Long userTaskID) {
        return userTaskRepository.findById(userTaskID)
                .orElseThrow(() -> new ResourceNotFoundException("UserTask not found"));
    }

    public UserTask updateUserTask(Long userTaskID, UserTask userTaskDetails) {
        UserTask userTask = getUserTaskById(userTaskID);
        userTask.setTask(userTaskDetails.getTask());
        userTask.setUser(userTaskDetails.getUser());
        userTask.setTaskIsCompleted(userTaskDetails.getTaskIsCompleted());
        userTask.setTaskDuedate(userTaskDetails.getTaskDuedate());
        return userTaskRepository.save(userTask);
    }


    public void deleteUserTask(Long userTaskID) {
        UserTask userTask = getUserTaskById(userTaskID);
        userTaskRepository.delete(userTask);
    }

}
