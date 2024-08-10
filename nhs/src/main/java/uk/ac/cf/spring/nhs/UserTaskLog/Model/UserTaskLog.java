package uk.ac.cf.spring.nhs.UserTaskLog.Model;

import java.time.LocalDateTime;

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
    private Long userId;

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
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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

}