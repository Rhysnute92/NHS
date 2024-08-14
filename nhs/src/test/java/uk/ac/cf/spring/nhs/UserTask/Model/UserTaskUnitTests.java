package uk.ac.cf.spring.nhs.UserTask.Model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.cf.spring.nhs.Task.Model.Task;

public class UserTaskUnitTests {

    private UserTask userTask;
    private Task task;

    @BeforeEach
    public void setUp() {
        userTask = new UserTask();
        task = new Task();
        task.setId(1L);
    }

    @Test
    public void testSetAndGetId() {
        userTask.setId(1L);
        assertThat(userTask.getId()).isEqualTo(1L);
    }

    @Test
    public void testSetAndGetTask() {
        userTask.setTask(task);
        assertThat(userTask.getTask()).isEqualTo(task);
    }

    @Test
    public void testSetAndGetUserID() {
        userTask.setUserID(100L);
        assertThat(userTask.getUserID()).isEqualTo(100L);
    }

    @Test
    public void testSetAndGetBitmask() {
        userTask.setBitmask(15);
        assertThat(userTask.getBitmask()).isEqualTo(15);
    }

    @Test
    public void testEqualsAndHashCode_sameObject() {
        userTask.setId(1L);
        userTask.setTask(task);
        userTask.setUserID(100L);
        userTask.setBitmask(15);

        UserTask other = new UserTask();
        other.setId(1L);
        other.setTask(task);
        other.setUserID(100L);
        other.setBitmask(15);

        assertThat(userTask).isEqualTo(other);
        assertThat(userTask.hashCode()).isEqualTo(other.hashCode());
    }

    @Test
    public void testEqualsAndHashCode_differentObject() {
        userTask.setId(1L);
        userTask.setTask(task);
        userTask.setUserID(100L);
        userTask.setBitmask(15);

        UserTask other = new UserTask();
        other.setId(2L); // Different ID
        other.setTask(task);
        other.setUserID(100L);
        other.setBitmask(15);

        assertThat(userTask).isNotEqualTo(other);
        assertThat(userTask.hashCode()).isNotEqualTo(other.hashCode());
    }

    @Test
    public void testEquals_differentClass() {
        assertThat(userTask).isNotEqualTo(new Object());
    }

    @Test
    public void testEquals_nullObject() {
        assertThat(userTask).isNotEqualTo(null);
    }
}
