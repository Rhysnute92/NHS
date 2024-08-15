package uk.ac.cf.spring.nhs.UserTaskLog.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Service.UserTaskLogService;

public class UserTaskLogControllerUnitTests {

    private MockMvc mockMvc;

    @Mock
    private UserTaskLogService userTaskLogService;

    @InjectMocks
    private UserTaskLogController userTaskLogController;

    private UserTaskLog userTaskLog;

    /**
     * Initializes the test environment by setting up the MockMvc instance and
     * creating a default UserTaskLog object.
     *
     * @return void
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTaskLogController)
                .build();

        userTaskLog = new UserTaskLog();
        userTaskLog.setId(1L);
        userTaskLog.setUserID(123L);
        userTaskLog.setBitmask(31);
        userTaskLog.setMonthYear("2024-08");
        userTaskLog.setCreatedAt(LocalDateTime.now());
    }

    /**
     * Tests that a UserTaskLog can be successfully created.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testCreateUserTaskLog() throws Exception {
        when(userTaskLogService.createUserTaskLog(any(UserTaskLog.class))).thenReturn(userTaskLog);

        mockMvc.perform(post("/api/user-task-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTaskLog)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userID").value(123L))
                .andExpect(jsonPath("$.bitmask").value(31));

        verify(userTaskLogService, times(1)).createUserTaskLog(any(UserTaskLog.class));
    }

    /**
     * Tests that a UserTaskLog can be successfully retrieved by its ID.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetUserTaskLogById_Found() throws Exception {
        when(userTaskLogService.getUserTaskLogById(1L)).thenReturn(userTaskLog);

        mockMvc.perform(get("/api/user-task-logs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userID").value(123L));

        verify(userTaskLogService, times(1)).getUserTaskLogById(1L);
    }

    /**
     * Tests that a 404 status is returned when attempting to retrieve a UserTaskLog
     * by ID that does not exist.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetUserTaskLogById_NotFound() throws Exception {
        when(userTaskLogService.getUserTaskLogById(1L)).thenThrow(new NoSuchElementException("UserTaskLog not found"));

        mockMvc.perform(get("/api/user-task-logs/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTaskLog not found"));

        verify(userTaskLogService, times(1)).getUserTaskLogById(1L);
    }

    /**
     * Tests the retrieval of all user task logs by invoking the
     * getAllUserTaskLogs() method of the userTaskLogService.
     * Verifies that the service method is called exactly once.
     * Checks that the response status is OK and that the first
     * user task log in the response has the expected ID and user ID.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetAllUserTaskLogs() throws Exception {
        when(userTaskLogService.getAllUserTaskLogs()).thenReturn(Arrays.asList(userTaskLog));

        mockMvc.perform(get("/api/user-task-logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userID").value(123L));

        verify(userTaskLogService, times(1)).getAllUserTaskLogs();
    }

    /**
     * Tests that a UserTaskLog can be successfully updated.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateUserTaskLog() throws Exception {
        when(userTaskLogService.updateUserTaskLog(eq(1L), any(UserTaskLog.class))).thenReturn(userTaskLog);

        mockMvc.perform(put("/api/user-task-logs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTaskLog)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userID").value(123L));

        verify(userTaskLogService, times(1)).updateUserTaskLog(eq(1L), any(UserTaskLog.class));
    }

    /**
     * Tests the deletion of a UserTaskLog by its ID.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteUserTaskLog() throws Exception {
        doNothing().when(userTaskLogService).deleteUserTaskLog(1L);

        mockMvc.perform(delete("/api/user-task-logs/1"))
                .andExpect(status().isNoContent());

        verify(userTaskLogService, times(1)).deleteUserTaskLog(1L);
    }

    // Edge cases

    /**
     * Tests the creation of a UserTaskLog with a null body.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testCreateUserTaskLog_NullBody() throws Exception {
        mockMvc.perform(post("/api/user-task-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that a 404 status is returned when attempting to update a UserTaskLog
     * that does not exist.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateUserTaskLog_NotFound() throws Exception {
        when(userTaskLogService.updateUserTaskLog(eq(1L), any(UserTaskLog.class)))
                .thenThrow(new NoSuchElementException("UserTaskLog not found"));

        mockMvc.perform(put("/api/user-task-logs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTaskLog)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTaskLog not found"));

        verify(userTaskLogService, times(1)).updateUserTaskLog(eq(1L), any(UserTaskLog.class));
    }

    /**
     * Tests that a 404 status is returned when attempting to delete a UserTaskLog
     * that does not exist.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteUserTaskLog_NotFound() throws Exception {
        doThrow(new NoSuchElementException("UserTaskLog not found")).when(userTaskLogService).deleteUserTaskLog(99L);

        mockMvc.perform(delete("/api/user-task-logs/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTaskLog not found"));

        verify(userTaskLogService, times(1)).deleteUserTaskLog(99L);
    }

    /**
     * Converts an object into a JSON string.
     *
     * @param obj the object to be converted
     * @return the JSON string representation of the object
     */
    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Register the module for Java 8 Date/Time API
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // To prevent serialization as
                                                                                  // timestamps
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
