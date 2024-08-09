package uk.ac.cf.spring.nhs.UserTask.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Repository.JpaTaskRepository;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class JpaUserTaskRepositoryUnitTests {

    @Autowired
    private JpaUserTaskRepository userTaskRepository;

    @Autowired
    private JpaTaskRepository taskRepository;

    private UserTask userTask1;
    private UserTask userTask2;
    private Task task1;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        // Mocking CustomUserDetails
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(1L);
        userCredentials.setUserName("testuser");
        userCredentials.setUserPassword("password");
        userCredentials.setUserRole("ROLE_USER");
        userDetails = CustomUserDetails.build(userCredentials);

        // Create and save a Task
        task1 = new Task();
        task1.setName("Test Task 1");
        task1.setDescription("Test Description 1");
        task1.setPeriodicity("Test Periodicity 1");
        taskRepository.save(task1);

        // Create and save UserTask 1
        userTask1 = new UserTask();
        userTask1.setTaskIsCompleted(false);
        userTask1.setTaskDuedate(LocalDateTime.of(2024, 8, 8, 12, 0));
        userTask1.setTask(task1);
        userTask1.setUserID(userCredentials.getUserId());

        // Create and save UserTask 2
        userTask2 = new UserTask();
        userTask2.setTaskIsCompleted(true);
        userTask2.setTaskDuedate(LocalDateTime.of(2024, 8, 9, 12, 0));
        userTask2.setTask(task1);
        userTask2.setUserID(userCredentials.getUserId());

        userTaskRepository.save(userTask1);
        userTaskRepository.save(userTask2);
    }

    @Test
    public void testFindById() {
        UserTask foundUserTask = userTaskRepository.findById(userTask1.getId()).orElse(null);
        assertNotNull(foundUserTask);
        assertEquals(userTask1.getTaskDuedate(), foundUserTask.getTaskDuedate());
    }

    @Test
    public void testFindAll() {
        List<UserTask> userTasks = userTaskRepository.findAll();
        assertEquals(2, userTasks.size());
    }

    @Test
    public void testSave() {
        UserTask userTask = new UserTask();
        userTask.setTaskIsCompleted(false);
        userTask.setTaskDuedate(LocalDateTime.of(2024, 8, 10, 12, 0));
        userTask.setTask(task1);
        userTask.setUserID(((CustomUserDetails) userDetails).getUserId());
        UserTask savedUserTask = userTaskRepository.save(userTask);
        assertNotNull(savedUserTask);
        assertEquals(userTask.getTaskDuedate(), savedUserTask.getTaskDuedate());
    }

    @Test
    public void testDeleteById() {
        userTaskRepository.deleteById(userTask1.getId());
        UserTask deletedUserTask = userTaskRepository.findById(userTask1.getId()).orElse(null);
        assertNull(deletedUserTask);
    }
}
