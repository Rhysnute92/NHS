/* package uk.ac.cf.spring.nhs.Task.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

public class TaskUnitTests {

    @Test
    public void testTaskEntity() {

        Task task = new Task();

        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setPeriodicity("Test Periodicity");

        assertEquals(1L, task.getId());
        assertEquals("Test Task", task.getName());
        assertEquals("Test Description", task.getDescription());
        assertEquals("Test Periodicity", task.getPeriodicity());
    }
 
    @Test
    public void testTaskDefaultConstructor() {
        Task task = new Task();
        assertNotNull(task);
    }

}
 */