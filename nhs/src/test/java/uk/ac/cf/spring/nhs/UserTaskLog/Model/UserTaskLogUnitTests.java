package uk.ac.cf.spring.nhs.UserTaskLog.Model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;

public class UserTaskLogUnitTests {

    private UserTaskLog log1;
    private UserTaskLog log2;
    private UserTask userTask;

    @BeforeEach
    public void setUp() {
        log1 = new UserTaskLog();
        log2 = new UserTaskLog();
        userTask = new UserTask();
        userTask.setId(1L);

        Long id = 1L;
        Long userID = 123L;
        int bitmask = 31;
        String monthYear = "2024-08";
        LocalDateTime createdAt = LocalDateTime.now();

        log1.setId(id);
        log1.setUserID(userID);
        log1.setBitmask(bitmask);
        log1.setMonthYear(monthYear);
        log1.setCreatedAt(createdAt);
        log1.setUserTask(userTask);

        log2.setId(id);
        log2.setUserID(userID);
        log2.setBitmask(bitmask);
        log2.setMonthYear(monthYear);
        log2.setCreatedAt(createdAt);
        log2.setUserTask(userTask);
    }

    /**
     * Tests the equals method of the UserTaskLog class by comparing two objects and verifying that they are equal when all fields are identical, and not equal when any field is different.
     *
     * @return         	void
     */
    @Test
    public void testEquals() {
        assertThat(log1).isEqualTo(log2);

        log2.setId(2L);
        assertThat(log1).isNotEqualTo(log2);

        log2.setId(log1.getId());
        log2.setUserID(999L);
        assertThat(log1).isNotEqualTo(log2);

        log2.setUserID(log1.getUserID());
        log2.setBitmask(99);
        assertThat(log1).isNotEqualTo(log2);

        log2.setBitmask(log1.getBitmask());
        log2.setMonthYear("2025-09");
        assertThat(log1).isNotEqualTo(log2);

        log2.setMonthYear(log1.getMonthYear());
        log2.setCreatedAt(LocalDateTime.now().minusDays(1));
        assertThat(log1).isNotEqualTo(log2);

        log2.setCreatedAt(log1.getCreatedAt());
        log2.setUserTask(new UserTask()); // Different UserTask object
        assertThat(log1).isNotEqualTo(log2);
    }

    /**
     * Tests the hashCode method of the UserTaskLog class.
     *
     * This test case checks that the hashCode of two UserTaskLog objects is equal
     * when all their fields are equal, and not equal when any of their fields are different.
     *
     * @return         	void
     */
    @Test
    public void testHashCode() {
        assertThat(log1.hashCode()).isEqualTo(log2.hashCode());

        log2.setId(2L);
        assertThat(log1.hashCode()).isNotEqualTo(log2.hashCode());

        log2.setId(log1.getId());
        log2.setUserID(999L);
        assertThat(log1.hashCode()).isNotEqualTo(log2.hashCode());

        log2.setUserID(log1.getUserID());
        log2.setBitmask(99);
        assertThat(log1.hashCode()).isNotEqualTo(log2.hashCode());

        log2.setBitmask(log1.getBitmask());
        log2.setMonthYear("2025-09");
        assertThat(log1.hashCode()).isNotEqualTo(log2.hashCode());

        log2.setMonthYear(log1.getMonthYear());
        log2.setCreatedAt(LocalDateTime.now().minusDays(1));
        assertThat(log1.hashCode()).isNotEqualTo(log2.hashCode());

        log2.setCreatedAt(log1.getCreatedAt());
        log2.setUserTask(new UserTask()); // Different UserTask object
        assertThat(log1.hashCode()).isNotEqualTo(log2.hashCode());
    }

    /**
     * Tests the setters and getters of the UserTaskLog class.
     *
     * Verifies that the setters correctly update the object's state and the getters return the expected values.
     *
     * @return none
     */
    @Test
    public void testSettersAndGetters() {
        log1.setId(2L);
        assertThat(log1.getId()).isEqualTo(2L);

        log1.setUserID(999L);
        assertThat(log1.getUserID()).isEqualTo(999L);

        log1.setBitmask(99);
        assertThat(log1.getBitmask()).isEqualTo(99);

        String newMonthYear = "2025-09";
        log1.setMonthYear(newMonthYear);
        assertThat(log1.getMonthYear()).isEqualTo(newMonthYear);

        LocalDateTime newCreatedAt = LocalDateTime.now().minusDays(1);
        log1.setCreatedAt(newCreatedAt);
        assertThat(log1.getCreatedAt()).isEqualTo(newCreatedAt);

        UserTask newUserTask = new UserTask();
        newUserTask.setId(2L);
        log1.setUserTask(newUserTask);
        assertThat(log1.getUserTask()).isEqualTo(newUserTask);
    }

    /**
     * Tests the toString method of the UserTaskLog class by comparing the string representation of a UserTaskLog object with an expected string.
     *
     * @return         	void
     */
    @Test
    public void testToString() {
        UserTask userTask = new UserTask();
        userTask.setId(1L);

        LocalDateTime createdAt = LocalDateTime.now(); // Capture the exact time
        UserTaskLog log1 = new UserTaskLog();
        log1.setId(1L);
        log1.setUserID(123L);
        log1.setBitmask(31);
        log1.setMonthYear("2024-08");
        log1.setCreatedAt(createdAt);
        log1.setUserTask(userTask);

        String expectedString = "UserTaskLog{" +
                "id=1" +
                ", userID=123" +
                ", userTask=UserTask{id=1, task=null, userID=null, bitmask=0}" +
                ", bitmask=31" +
                ", monthYear='2024-08'" +
                ", createdAt=" + createdAt +
                '}';
        String actualString = log1.toString();
        assertThat(actualString).isEqualTo(expectedString);
    }

    /**
     * Tests the edge cases of the UserTaskLog class by comparing two objects and verifying that they are equal when certain fields are null or empty.
     *
     * @return         	void
     */
    @Test
    public void testEdgeCases() {
        // Test null UserTask
        log1.setUserTask(null);
        log2.setUserTask(null);
        assertThat(log1).isEqualTo(log2);

        // Test empty monthYear
        log1.setMonthYear("");
        log2.setMonthYear("");
        assertThat(log1).isEqualTo(log2);

        // Test null createdAt
        log1.setCreatedAt(null);
        log2.setCreatedAt(null);
        assertThat(log1).isEqualTo(log2);
    }
}
