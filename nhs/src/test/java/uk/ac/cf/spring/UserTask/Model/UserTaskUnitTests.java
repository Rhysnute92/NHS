package uk.ac.cf.spring.UserTask.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;

public class UserTaskUnitTests {

    /**
     * Tests the creation of a UserTask object.
     * 
     */
    @Test
    public void testUserTaskCreation() {
        UserTask userTask = new UserTask();
        userTask.setId(1L);
        userTask.setUser(1L);
        userTask.setTaskIsCompleted(false);
        userTask.setTaskDuedate(LocalDateTime.of(2024, 8, 9, 0, 0));

        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        userTask.setTask(task);

        assertEquals(1L, userTask.getId());
        assertEquals(1L, userTask.getUser());
        assertEquals(false, userTask.getTaskIsCompleted());
        assertEquals(LocalDateTime.of(2024, 8, 9, 0, 0), userTask.getTaskDuedate());
        assertEquals(task, userTask.getTask());
    }

    /**
     * Tests the equality of two UserTask objects.
     *
     * This test creates two UserTask objects with the same ID, user, taskIsCompleted, and taskDuedate.
     * It also creates a Task object with the same ID and name.
     * The two UserTask objects are then set with the same Task object.
     * Finally, the test asserts that the two UserTask objects are equal and have the same hash code.
     *
     * @throws AssertionError if the two UserTask objects are not equal or have different hash codes
     */
    @Test
    public void testUserTaskEquality() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");

        UserTask userTask1 = new UserTask();
        userTask1.setId(1L);
        userTask1.setUser(1L);
        userTask1.setTaskIsCompleted(false);
        userTask1.setTaskDuedate(LocalDateTime.of(2024, 8, 9, 0, 0));
        userTask1.setTask(task);

        UserTask userTask2 = new UserTask();
        userTask2.setId(1L);
        userTask2.setUser(1L);
        userTask2.setTaskIsCompleted(false);
        userTask2.setTaskDuedate(LocalDateTime.of(2024, 8, 9, 0, 0));
        userTask2.setTask(task);

        assertEquals(userTask1, userTask2);
        assertEquals(userTask1.hashCode(), userTask2.hashCode());
    }

    /**
     * Tests the inequality of two UserTask objects.
     *
     * This test creates two UserTask objects with different tasks.
     * The tasks are set with different IDs and names.
     * The two UserTask objects are then set with the same ID, user, taskIsCompleted, and taskDuedate.
     * Finally, the test asserts that the two UserTask objects are not equal and have different hash codes.
     *
     * @throws AssertionError if the two UserTask objects are equal or have the same hash code
     */
    @Test
    public void testUserTaskInequality() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Test Task 2");

        UserTask userTask1 = new UserTask();
        userTask1.setId(1L);
        userTask1.setUser(1L);
        userTask1.setTaskIsCompleted(false);
        userTask1.setTaskDuedate(LocalDateTime.of(2024, 8, 9, 0, 0));
        userTask1.setTask(task);

        UserTask userTask2 = new UserTask();
        userTask2.setId(1L);
        userTask2.setUser(1L);
        userTask2.setTaskIsCompleted(false);
        userTask2.setTaskDuedate(LocalDateTime.of(2024, 8, 9, 0, 0));
        userTask2.setTask(task2);

        assertNotEquals(userTask1, userTask2);
        assertNotEquals(userTask1.hashCode(), userTask2.hashCode());
    }

    

}
