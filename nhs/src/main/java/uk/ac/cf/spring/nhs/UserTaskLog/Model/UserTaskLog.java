package uk.ac.cf.spring.nhs.UserTaskLog.Model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;

@Entity
public class UserTaskLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserTaskLogID")
    private Long id;

    @Column(name = "UserID", nullable = false)
    private Long userID;

    @ManyToOne
    @JoinColumn(name = "UserTaskID", nullable = false)
    private UserTask userTask;

    @Column(name = "Bitmask", nullable = false)
    private int bitmask;

    @Column(name = "MonthYear", nullable = false)
    private String monthYear; // YYYY-MM

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Checks if the current UserTaskLog object is equal to the provided object.
     *
     * @param o the object to compare with the current UserTaskLog object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserTaskLog that = (UserTaskLog) o;
        return bitmask == that.bitmask &&
                Objects.equals(id, that.id) &&
                Objects.equals(userID, that.userID) &&
                Objects.equals(userTask, that.userTask) &&
                Objects.equals(monthYear, that.monthYear) &&
                Objects.equals(createdAt, that.createdAt);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, userID, userTask, bitmask, monthYear, createdAt);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userID
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * @return the userTask
     */
    public UserTask getUserTask() {
        return userTask;
    }

    /**
     * @param userTask the userTask to set
     */
    public void setUserTask(UserTask userTask) {
        this.userTask = userTask;
    }

    /**
     * @return the bitmask
     */
    public int getBitmask() {
        return bitmask;
    }

    /**
     * @param bitmask the bitmask to set
     */
    public void setBitmask(int bitmask) {
        this.bitmask = bitmask;
    }

    /**
     * @return the monthYear
     */
    public String getMonthYear() {
        return monthYear;
    }

    /**
     * @param monthYear the monthYear to set
     */
    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserTaskLog{" +
                "id=" + id +
                ", userID=" + userID +
                ", userTask=" + (userTask != null ? userTask.toString() : "null") +
                ", bitmask=" + bitmask +
                ", monthYear='" + monthYear + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
