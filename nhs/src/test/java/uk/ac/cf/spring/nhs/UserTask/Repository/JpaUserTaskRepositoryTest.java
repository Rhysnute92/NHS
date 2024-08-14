/* package uk.ac.cf.spring.nhs.UserTask.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;
import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Repository.JpaTaskRepository;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaUserTaskRepositoryTest {

    @Autowired
    private JpaUserTaskRepository userTaskRepository;

    @Autowired
    private JpaTaskRepository taskRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    private UserCredentials userCredentials;
    private Task task;

    @BeforeEach
    public void setUp() {
        // Set up a UserCredentials entity
        userCredentials = new UserCredentials();
        userCredentials.setUserName("testUser");
        userCredentials.setUserPassword("password");
        userCredentials.setUserRole("ROLE_PATIENT");
        userCredentials = userCredentialsRepository.saveAndFlush(userCredentials);

        // Set up a Task entity
        task = new Task();
        task.setName("Daily Exercise");
        task.setType("Exercise");
        task.setDescription("Perform daily exercise");
        task.setPeriodicity(Task.Model.Enum.Periodicity.DAILY);
        task = taskRepository.saveAndFlush(task);
    }

    @Test
    public void testSaveUserTask() {
        // Create a new UserTask
        UserTask userTask = new UserTask();
        userTask.setUserID(userCredentials.getUserId());
        userTask.setTask(task);
        userTask.setBitmask(1);

        // Save the UserTask to the repository
        UserTask savedUserTask = userTaskRepository.save(userTask);

        // Verify the UserTask was saved
        assertThat(savedUserTask).isNotNull();
        assertThat(savedUserTask.getId()).isNotNull();
        assertThat(savedUserTask.getUserID()).isEqualTo(userCredentials.getUserId());
        assertThat(savedUserTask.getTask()).isEqualTo(task);
    }

    @Test
    public void testFindByUserID() {
        // Create and save a UserTask
        UserTask userTask = new UserTask();
        userTask.setUserID(userCredentials.getUserId());
        userTask.setTask(task);
        userTask.setBitmask(1);
        userTaskRepository.saveAndFlush(userTask);

        // Retrieve UserTasks by userID
        List<UserTask> userTasks = userTaskRepository.findByUserID(userCredentials.getUserId());

        // Verify the UserTasks were found
        assertThat(userTasks).isNotEmpty();
        assertThat(userTasks.size()).isEqualTo(1);
        assertThat(userTasks.get(0).getTask()).isEqualTo(task);
    }

    @Test
    public void testDeleteUserTask() {
        // Create and save a UserTask
        UserTask userTask = new UserTask();
        userTask.setUserID(userCredentials.getUserId());
        userTask.setTask(task);
        userTask.setBitmask(1);
        userTask = userTaskRepository.saveAndFlush(userTask);

        // Delete the UserTask
        userTaskRepository.delete(userTask);

        // Verify the UserTask was deleted
        Optional<UserTask> deletedUserTask = userTaskRepository.findById(userTask.getId());
        assertThat(deletedUserTask).isEmpty();
    }

    @Test
    public void testUpdateUserTask() {
        // Create and save a UserTask
        UserTask userTask = new UserTask();
        userTask.setUserID(userCredentials.getUserId());
        userTask.setTask(task);
        userTask.setBitmask(1);
        userTask = userTaskRepository.saveAndFlush(userTask);

        // Update the bitmask of the UserTask
        userTask.setBitmask(3);
        UserTask updatedUserTask = userTaskRepository.saveAndFlush(userTask);

        // Verify the UserTask was updated
        assertThat(updatedUserTask.getBitmask()).isEqualTo(3);
    }
}
 */