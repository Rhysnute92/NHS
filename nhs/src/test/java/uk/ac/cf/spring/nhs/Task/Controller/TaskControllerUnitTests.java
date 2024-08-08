package uk.ac.cf.spring.nhs.Task.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Service.TaskService;

@ExtendWith(MockitoExtension.class)
public class TaskControllerUnitTests {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        task1 = new Task();
        task2 = new Task();

        task1.setName("Test Task 1");
        task1.setDescription("Test Description 1");
        task1.setPeriodicity("Test Periodicity 1");

        task2.setName("Test Task 2");
        task2.setDescription("Test Description 2");
        task2.setPeriodicity("Test Periodicity 2");
    }

    @Test
    public void testGetTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Task 1"))
                .andExpect(jsonPath("$[1].name").value("Test Task 2"));

        verify(taskService, times(1)).getAllTasks();
    }

    /*
     * @Test
     * public void testGetTaskById() throws Exception {
     * when(taskService.getTaskById(task1.getId())).thenReturn(task1);
     * 
     * mockMvc.perform(get("/tasks/{id}", task1.getId()))
     * .andExpect(status().isOk())
     * .andExpect(jsonPath("$.name").value("Test Task 1"));
     * 
     * verify(taskService, times(1)).getTaskById(task1.getId());
     * }
     */

    @Test
    public void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task1);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Medication\", \"description\": \"Take medication daily\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Medication"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    public void testUpdateTask() throws Exception {
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task1);

        mockMvc.perform(put("/tasks/{id}", task1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Medication\", \"description\": \"Take medication twice daily\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Medication"));

        verify(taskService, times(1)).updateTask(anyLong(), any(Task.class));
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(task1.getId());

        mockMvc.perform(delete("/tasks/{id}", task1.getId()))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(task1.getId());
    }

}
