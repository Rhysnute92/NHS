package uk.ac.cf.spring.nhs.Task.Repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.ac.cf.spring.nhs.Task.Model.Task;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class JpaTaskRepositoryUnitTests {

    @Autowired
    private JpaTaskRepository taskRepository;

    private Task task1;

    private Task task2;

    @BeforeEach
    public void setUp() {
        task1 = new Task();
        task2 = new Task();

        task1.setName("Test Task 1");
        task1.setDescription("Test Description 1");
        task1.setPeriodicity("Test Periodicity 1");

        task2.setName("Test Task 2");
        task2.setDescription("Test Description 2");
        task2.setPeriodicity("Test Periodicity 2");

        taskRepository.save(task1);
        taskRepository.save(task2);
    }

    @Test
    public void testFindById() {
        Task foundTask = taskRepository.findById(task1.getId()).orElse(null);
        assertNotNull(foundTask);
        assertEquals(task1.getName(), foundTask.getName());
    }

    @Test
    public void testFindAll() {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        assertEquals(2, tasks.size());
    }

    @Test
    public void testSave() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setPeriodicity("Test Periodicity");
        Task savedTask = taskRepository.save(task);
        assertNotNull(savedTask);
        assertEquals(task.getName(), savedTask.getName());
    }

    @Test
    public void testDeleteById() {
        taskRepository.deleteById(task1.getId());
        Task deletedTask = taskRepository.findById(task1.getId()).orElse(null);
        assertNull(deletedTask);
    }

}
