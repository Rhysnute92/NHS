package uk.ac.cf.spring.nhs.Task.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Model.Enum.Periodicity;
import uk.ac.cf.spring.nhs.Task.Repository.JpaTaskRepository;

class TaskServiceUnitTests {

    @Mock
    private JpaTaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    /**
     * Sets up the test environment by initializing the task object with default values.
     *
     * @param  None
     * @return None
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setName("Daily Exercise");
        task.setDescription("Perform daily exercises");
        task.setType("Exercise");
        task.setPeriodicity(Periodicity.DAILY);
    }

    /**
     * Tests the retrieval of all tasks from the task service.
     *
     * @return  None
     */
    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(task, new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertThat(result).isEqualTo(tasks);
        verify(taskRepository, times(1)).findAll();
    }

    /**
     * Tests the retrieval of a task by its ID when the task is found.
     *
     * @return  None
     */
    @Test
    void testGetTaskById_Found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(task);
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Tests the retrieval of a task by its ID when the task is not found.
     *
     * @return  None
     */
    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(1L);

        assertThat(result.isPresent()).isFalse();
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Tests the creation of a new task using the task service.
     *
     * @return  None
     */
    @Test
    void testCreateTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertThat(result).isEqualTo(task);
        verify(taskRepository, times(1)).save(task);
    }

    /**
     * Tests the update of a task by its ID when the task is found.
     *
     * @return  None
     */
    @Test
    void testUpdateTask_Found() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        Task updatedTask = new Task();
        updatedTask.setId(1L); // Ensure the ID is set correctly
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setType("Updated Type");
        updatedTask.setPeriodicity(Periodicity.WEEKLY);

        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask); 

        Task result = taskService.updateTask(1L, updatedTask);

        assertThat(result.getName()).isEqualTo("Updated Task");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.getType()).isEqualTo("Updated Type");
        assertThat(result.getPeriodicity()).isEqualTo(Periodicity.WEEKLY);
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).save(updatedTask);
    }

    /**
     * Tests the update of a task by its ID when the task is not found.
     *
     * @return  None
     */
    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        Task result = taskService.updateTask(1L, task);

        assertThat(result).isNull();
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    /**
     * Tests the deletion of a task by its ID when the task is found.
     *
     * @return  None
     */
    @Test
    void testDeleteTask_Found() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests the deletion of a task by its ID when the task is not found.
     *
     * @return  None
     */
    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        taskService.deleteTask(1L);

        verify(taskRepository, times(0)).deleteById(anyLong());
    }
}
