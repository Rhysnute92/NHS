package uk.ac.cf.spring.nhs.Task.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

public class TaskUnitTests {

    @Test
    public void testTaskEntity() {

        Task task = new Task();

        task.setId(1L);
        task.setName("Test Task");
        task.setStatus("Test Status");
        task.setDueDate("Test Date");
        task.setPriority(1);
        task.setDescription("Test Description");
        task.setPeriodicity("Test Periodicity");

        assertEquals(1L, task.getId());
        assertEquals("Test Task", task.getName());
        assertEquals("Test Status", task.getStatus());
        assertEquals("Test Date", task.getDueDate());
        assertEquals(1, task.getPriority());
        assertEquals("Test Description", task.getDescription());
        assertEquals("Test Periodicity", task.getPeriodicity());
    }

    @Test
    public void testTaskDefaultConstructor() {
        Task task = new Task();
        assertNotNull(task);
    }

}
