package uk.ac.cf.spring.nhs.UserTask.Model;

import java.util.Objects;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import uk.ac.cf.spring.nhs.Task.Model.Task;

@Entity
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserTaskID")
    private Long id;

    @Column(name = "TaskIsCompleted")
    private Boolean taskIsCompleted;

    @Column(name = "TaskDuedate")
    private LocalDateTime taskDuedate;

    @ManyToOne
    @JoinColumn(name = "TaskID", nullable = false)
    private Task task;

    @Column(name = "UserID", nullable = false)
    private Long userID;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTaskIsCompleted() {
        return taskIsCompleted;
    }

    public void setTaskIsCompleted(Boolean taskIsCompleted) {
        this.taskIsCompleted = taskIsCompleted;
    }

    public LocalDateTime getTaskDuedate() {
        return taskDuedate;
    }

    public void setTaskDuedate(LocalDateTime taskDuedate) {
        this.taskDuedate = taskDuedate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserTask userTask = (UserTask) o;
        return Objects.equals(id, userTask.id) &&
                Objects.equals(taskIsCompleted, userTask.taskIsCompleted) &&
                Objects.equals(taskDuedate, userTask.taskDuedate) &&
                Objects.equals(task, userTask.task) &&
                Objects.equals(userID, userTask.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskIsCompleted, taskDuedate, task, userID);
    }
}
