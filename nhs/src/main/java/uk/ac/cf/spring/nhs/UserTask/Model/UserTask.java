package uk.ac.cf.spring.nhs.UserTask.Model;

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
    private java.time.LocalDateTime taskDuedate;

    @ManyToOne
    @JoinColumn(name = "TaskID", nullable = false)
    private Task task;

    @JoinColumn(name = "UserID", nullable = false)
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

    public java.time.LocalDateTime getTaskDuedate() {
        return taskDuedate;
    }

    public void setTaskDuedate(java.time.LocalDateTime taskDuedate) {
        this.taskDuedate = taskDuedate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getUser() {
        return userID;
    }

    public void setUser(Long userID) {
        this.userID = userID;
    }
}
