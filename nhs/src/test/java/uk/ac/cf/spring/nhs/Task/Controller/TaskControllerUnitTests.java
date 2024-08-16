package uk.ac.cf.spring.nhs.Task.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Model.Enum.Periodicity;
import uk.ac.cf.spring.nhs.Task.Service.TaskService;

public class TaskControllerUnitTests {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetTaskById_Found() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    public void testGetTaskById_NotFound() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task Description");
        task.setType("Type");
        task.setPeriodicity(Periodicity.DAILY);

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"New Task\", \"description\": \"Task Description\", \"type\": \"Type\", \"periodicity\": \"DAILY\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Task"))
                .andExpect(jsonPath("$.description").value("Task Description"))
                .andExpect(jsonPath("$.type").value("Type"))
                .andExpect(jsonPath("$.periodicity").value("DAILY"));
    }

    @Test
    public void testUpdateTask_Found() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setType("Updated Type");
        updatedTask.setPeriodicity(Periodicity.WEEKLY);

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"Updated Task\", \"description\": \"Updated Description\", \"type\": \"Updated Type\", \"periodicity\": \"WEEKLY\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.type").value("Updated Type"))
                .andExpect(jsonPath("$.periodicity").value("WEEKLY"));
    }

    @Test
    public void testUpdateTask_NotFound() throws Exception {
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(null);

        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"Updated Task\", \"description\": \"Updated Description\", \"type\": \"Updated Type\", \"periodicity\": \"WEEKLY\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }
}
