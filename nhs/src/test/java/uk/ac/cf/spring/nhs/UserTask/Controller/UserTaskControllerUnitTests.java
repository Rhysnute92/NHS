package uk.ac.cf.spring.nhs.UserTask.Controller;

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

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.cf.spring.nhs.Security.AuthenticationFacade;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

public class UserTaskControllerUnitTests {

    private MockMvc mockMvc;

    @Mock
    private UserTaskService userTaskService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails customUserDetails;

    @InjectMocks
    private UserTaskController userTaskController;

    private UserTask userTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTaskController).build();

        userTask = new UserTask();
        userTask.setId(1L);
        userTask.setUserID(123L);
        userTask.setBitmask(31);

        // Setup the mock authentication
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUserId()).thenReturn(123L);
    }

    /**
     * Test case for the `testToggleTaskCompletion` method.
     *
     * This test case verifies that the `toggleTaskCompletion` method of the
     * `UserTaskService`
     * is called correctly when the `getUserTaskById` method returns a non-null
     * `UserTask` object.
     * It also checks that the response status is `OK` and the JSON response
     * contains the expected value.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void testToggleTaskCompletion() throws Exception {
        when(userTaskService.getUserTaskById(1L)).thenReturn(userTask);
        doNothing().when(userTaskService).toggleTaskCompletion(userTask);

        mockMvc.perform(put("/usertask/task-toggle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));

        verify(userTaskService, times(1)).getUserTaskById(1L);
        verify(userTaskService, times(1)).toggleTaskCompletion(userTask);
    }

    /**
     * Tests the toggleTaskCompletion method when the user task is not found.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testToggleTaskCompletion_NotFound() throws Exception {
        when(userTaskService.getUserTaskById(1L)).thenThrow(new NoSuchElementException("UserTask not found"));

        mockMvc.perform(put("/usertask/task-toggle/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTask not found"));

        verify(userTaskService, times(1)).getUserTaskById(1L);
    }

    /**
     * Tests the toggleTaskCompletion method when the user task ID is invalid.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testToggleTaskCompletion_InvalidId() throws Exception {
        mockMvc.perform(put("/usertask/task-toggle/invalid"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that a UserTask can be successfully retrieved by its ID.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetUserTaskById_Found() throws Exception {
        when(userTaskService.getUserTaskById(1L)).thenReturn(userTask);

        mockMvc.perform(get("/usertask/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userID").value(123L));

        verify(userTaskService, times(1)).getUserTaskById(1L);
    }

    /**
     * Tests the retrieval of a UserTask by ID when the task is not found.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetUserTaskById_NotFound() throws Exception {
        when(userTaskService.getUserTaskById(1L)).thenThrow(new NoSuchElementException("UserTask not found"));

        mockMvc.perform(get("/usertask/task/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTask not found"));

        verify(userTaskService, times(1)).getUserTaskById(1L);
    }

    /**
     * Tests the retrieval of a UserTask by ID when the ID is invalid.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetUserTaskById_InvalidId() throws Exception {
        mockMvc.perform(get("/usertask/task/invalid"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests the assignment of a task to a user.
     *
     * @throws Exception if an error occurs during the test
     */
    // @Test
    // public void testAssignTaskToUser() throws Exception {
    //     when(userTaskService.assignTaskToUser(any(UserTask.class))).thenReturn(userTask);

    //     mockMvc.perform(post("/usertask")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(userTask)))
    //             .andExpect(status().isCreated())
    //             .andExpect(jsonPath("$.id").value(1L))
    //             .andExpect(jsonPath("$.userID").value(123L));

    //     verify(userTaskService, times(1)).assignTaskToUser(any(UserTask.class));
    // }

    /**
     * Tests the assignment of a task to a user when the request body is missing.
     *
     * @throws Exception if an error occurs during the test
     */
    // @Test
    // public void testAssignTaskToUser_MissingBody() throws Exception {
    //     mockMvc.perform(post("/usertask")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(""))
    //             .andExpect(status().isBadRequest());
    // }

    /**
     * Tests that a UserTask can be successfully updated.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateUserTask() throws Exception {
        when(userTaskService.updateUserTask(eq(1L), any(UserTask.class))).thenReturn(userTask);

        mockMvc.perform(put("/usertask/task-update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userID").value(123L));

        verify(userTaskService, times(1)).updateUserTask(eq(1L), any(UserTask.class));
    }

    /**
     * Tests the update of a user task when it is not found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateUserTask_NotFound() throws Exception {
        when(userTaskService.updateUserTask(eq(1L), any(UserTask.class)))
                .thenThrow(new NoSuchElementException("UserTask not found"));

        mockMvc.perform(put("/usertask/task-update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTask)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTask not found"));

        verify(userTaskService, times(1)).updateUserTask(eq(1L), any(UserTask.class));
    }

    /**
     * Tests the update of a user task with an invalid ID.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateUserTask_InvalidId() throws Exception {
        mockMvc.perform(put("/usertask/task-update/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTask)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that a user task can be successfully deleted.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteUserTask() throws Exception {
        doNothing().when(userTaskService).deleteUserTask(1L);

        mockMvc.perform(delete("/usertask/task-delete/1"))
                .andExpect(status().isNoContent());

        verify(userTaskService, times(1)).deleteUserTask(1L);
    }

    /**
     * Tests the deletion of a user task when it is not found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteUserTask_NotFound() throws Exception {
        // Simulate throwing NoSuchElementException when the service method is called
        doThrow(new NoSuchElementException("UserTask not found"))
                .when(userTaskService).deleteUserTask(1L);

        mockMvc.perform(delete("/usertask/task-delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("UserTask not found"));

        verify(userTaskService, times(1)).deleteUserTask(1L);
    }

    /**
     * Tests the deletion of a user task with an invalid ID.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteUserTask_InvalidId() throws Exception {
        mockMvc.perform(delete("/usertask/task-delete/invalid"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param obj the object to be converted
     * @return the JSON string representation of the object
     * @throws RuntimeException if an error occurs during the conversion
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
