package uk.ac.cf.spring.nhs.UserTask.Model;

import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "TaskID", nullable = false)
    private Task task;

    @Column(name = "UserID", nullable = false)
    private Long userID;

    @Column(name = "Bitmask", nullable = false)
    private int bitmask;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getBitmask() {
        return bitmask;
    }

    public void setBitmask(int bitmask) {
        this.bitmask = bitmask;
    }

    /**
     * Checks if the current UserTask object is equal to another object.
     *
     * @param o the object to compare with the current UserTask object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserTask userTask = (UserTask) o;
        return Objects.equals(id, userTask.id) &&
                Objects.equals(task, userTask.task) &&
                Objects.equals(userID, userTask.userID) &&
                Objects.equals(bitmask, userTask.bitmask);
    }

    /**
     * Generates a hash code for the object based on the values of its fields.
     *
     * @return the hash code for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, task, userID, bitmask);
    }
}
