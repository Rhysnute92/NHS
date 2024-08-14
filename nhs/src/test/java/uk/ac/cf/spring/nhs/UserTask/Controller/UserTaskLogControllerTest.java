package uk.ac.cf.spring.nhs.UserTask.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.cf.spring.nhs.UserTaskLog.Controller.UserTaskLogController;
import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Service.UserTaskLogService;

public class UserTaskLogControllerTest {

    @Mock
    private UserTaskLogService userTaskLogService;

    @InjectMocks
    private UserTaskLogController userTaskLogController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTaskLogController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateUserTaskLog() throws Exception {
        UserTaskLog userTaskLog = new UserTaskLog();
        userTaskLog.setId(1L);

        when(userTaskLogService.createUserTaskLog(any(UserTaskLog.class))).thenReturn(userTaskLog);

        mockMvc.perform(post("/api/user-task-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userTaskLog)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userTaskLog)));
    }

    @Test
    public void testGetUserTaskLogById() throws Exception {
        UserTaskLog userTaskLog = new UserTaskLog();
        userTaskLog.setId(1L);

        when(userTaskLogService.getUserTaskLogById(1L)).thenReturn(userTaskLog);

        mockMvc.perform(get("/api/user-task-logs/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userTaskLog)));
    }

    @Test
    public void testGetUserTaskLogById_NotFound() throws Exception {
        when(userTaskLogService.getUserTaskLogById(1L)).thenThrow(new NoSuchElementException("UserTaskLog not found"));

        mockMvc.perform(get("/api/user-task-logs/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUserTaskLogs() throws Exception {
        UserTaskLog userTaskLog1 = new UserTaskLog();
        userTaskLog1.setId(1L);
        UserTaskLog userTaskLog2 = new UserTaskLog();
        userTaskLog2.setId(2L);
        List<UserTaskLog> logs = Arrays.asList(userTaskLog1, userTaskLog2);

        when(userTaskLogService.getAllUserTaskLogs()).thenReturn(logs);

        mockMvc.perform(get("/api/user-task-logs"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(logs)));
    }

    @Test
    public void testUpdateUserTaskLog() throws Exception {
        UserTaskLog updatedLog = new UserTaskLog();
        updatedLog.setId(1L);

        when(userTaskLogService.updateUserTaskLog(anyLong(), any(UserTaskLog.class))).thenReturn(updatedLog);

        mockMvc.perform(put("/api/user-task-logs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedLog)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedLog)));
    }

    @Test
    public void testUpdateUserTaskLog_NotFound() throws Exception {
        when(userTaskLogService.updateUserTaskLog(anyLong(), any(UserTaskLog.class)))
                .thenThrow(new NoSuchElementException("UserTaskLog not found"));

        UserTaskLog updatedLog = new UserTaskLog();
        updatedLog.setId(1L);

        mockMvc.perform(put("/api/user-task-logs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedLog)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserTaskLog() throws Exception {
        mockMvc.perform(delete("/api/user-task-logs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserTaskLog_NotFound() throws Exception {
        doThrow(new NoSuchElementException("UserTaskLog not found")).when(userTaskLogService).deleteUserTaskLog(1L);

        mockMvc.perform(delete("/api/user-task-logs/1"))
                .andExpect(status().isNotFound());
    }
}
