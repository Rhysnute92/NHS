package uk.ac.cf.spring.nhs.UserTask.Model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import uk.ac.cf.spring.nhs.Task.Model.Task;

@Entity
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserTaskID")
    private Long id;

    @NotNull
    @Column(name = "TaskIsCompleted")
    private Boolean taskIsCompleted;

    @NotNull
    @FutureOrPresent
    @Column(name = "TaskDuedate")
    private java.time.LocalDateTime taskDuedate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TaskID", nullable = false)
    private Task task;

    @NotNull
    @JoinColumn(name = "UserID", nullable = false)
    private Long userID;

    // Getters and Setters
    
    /**
     * Retrieves the ID of the object.
     *
     * @return the ID of the object as a Long value
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the object.
     *
     * @param  id  the new ID value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return the completion status of the task
     */
    public Boolean getTaskIsCompleted() {
        return taskIsCompleted;
    }

    /**
     * Sets the value of the taskIsCompleted field.
     *
     * @param  taskIsCompleted  the new value for the taskIsCompleted field
     */
    public void setTaskIsCompleted(Boolean taskIsCompleted) {
        this.taskIsCompleted = taskIsCompleted;
    }

    /**
     * Retrieves the due date of the task.
     *
     * @return the due date of the task as a LocalDateTime object
     */
    public java.time.LocalDateTime getTaskDuedate() {
        return taskDuedate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param  taskDuedate  the new due date of the task
     */
    public void setTaskDuedate(java.time.LocalDateTime taskDuedate) {
        this.taskDuedate = taskDuedate;
    }

    /**
     * Retrieves the task associated with this user task.
     *
     * @return          the task associated with this user task
     */
    public Task getTask() {
        return task;
    }

    /**
     * Sets the task associated with this user task.
     *
     * @param  task  the new task to associate with this user task
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Retrieves the user ID associated with this user task.
     *
     * @return the user ID as a Long value
     */
    public Long getUser() {
        return userID;
    }

    /**
     * Sets the user ID for the object.
     *
     * @param  userID   the new user ID to set
     */
    public void setUser(Long userID) {
        this.userID = userID;
    }

    /**
     * Checks if this object is equal to another object. Overrides the default
     * comparison operator.
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        UserTask userTask = (UserTask) object;
        return Objects.equals(id, userTask.id) &&
                Objects.equals(taskIsCompleted, userTask.taskIsCompleted) &&
                Objects.equals(taskDuedate, userTask.taskDuedate) &&
                Objects.equals(task, userTask.task) &&
                Objects.equals(userID, userTask.userID);
    }

    /**
     * A description of the hashCode function. Overrides the default hash code
     * operator.
     *
     * @return description of the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, taskIsCompleted, taskDuedate, task, userID);
    }
}
