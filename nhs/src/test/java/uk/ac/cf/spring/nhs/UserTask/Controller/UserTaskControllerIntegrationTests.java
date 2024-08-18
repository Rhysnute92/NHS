package uk.ac.cf.spring.nhs.UserTask.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.cf.spring.nhs.Security.AuthenticationFacade;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Repository.JpaTaskRepository;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Repository.JpaUserTaskRepository;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@WebMvcTest(controllers = UserTaskController.class)
public class UserTaskControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationFacade authenticationFacade;

    @MockBean
    private JpaUserTaskRepository userTaskRepository;

    @MockBean
    private JpaTaskRepository taskRepository;

    @MockBean
    private UserTaskService userTaskService;

    private Task task;
    private UserTask userTask;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        // Set up a Task entity
        task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task.");
        when(taskRepository.save(task)).thenReturn(task);

        // Set up a UserTask entity
        userTask = new UserTask();
        userTask.setTask(task);
        userTask.setUserID(1L);
        userTask.setBitmask(0);
        when(userTaskRepository.save(userTask)).thenReturn(userTask);
    }

    private Authentication getMockedAuthentication(Long userId) {
        CustomUserDetails customUserDetails = new CustomUserDetails(userId, "testuser", "password",
                Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void testGetTasksForUser() throws Exception {

        Long userId = 1L;
        when(authenticationFacade.getAuthentication()).thenReturn(getMockedAuthentication(userId));
        when(userTaskService.getTasksForUser(userId)).thenReturn(Collections.singletonList(userTask));

        mockMvc.perform(get("/usertask/user"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertThat(jsonResponse).contains("\"name\":\"Test Task\"");
                });
    }

    /*
     * @Test
     * 
     * @WithMockUser(username = "testuser", roles = { "USER" })
     * public void testAssignTaskToUserAsUser() throws Exception {
     * Long userId = 1L;
     * 
     * // Manually setting the SecurityContext
     * CustomUserDetails customUserDetails = new CustomUserDetails(userId,
     * "testuser", "password",
     * Collections.singletonList(() -> "ROLE_USER"));
     * Authentication auth = new
     * UsernamePasswordAuthenticationToken(customUserDetails, null,
     * customUserDetails.getAuthorities());
     * SecurityContextHolder.getContext().setAuthentication(auth);
     * 
     * // Double-check the authentication in the test
     * Authentication authentication =
     * SecurityContextHolder.getContext().getAuthentication();
     * assertThat(authentication.getAuthorities()).extracting("authority").contains(
     * "ROLE_USER");
     * 
     * when(authenticationFacade.getAuthentication()).thenReturn(auth);
     * 
     * UserTask newUserTask = new UserTask();
     * newUserTask.setTask(task);
     * newUserTask.setUserID(userId);
     * newUserTask.setBitmask(0);
     * 
     * when(userTaskService.assignTaskToUser(newUserTask)).thenReturn(newUserTask);
     * 
     * mockMvc.perform(post("/usertask")
     * .contentType(MediaType.APPLICATION_JSON)
     * .content(objectMapper.writeValueAsString(newUserTask)))
     * .andExpect(status().isCreated())
     * .andExpect(result -> {
     * String jsonResponse = result.getResponse().getContentAsString();
     * assertThat(jsonResponse).contains("\"name\":\"Test Task\"");
     * assertThat(jsonResponse).contains("\"userID\":1");
     * });
     * }
     * 
     * @Test
     * 
     * @WithMockUser(username = "testuser", roles = { "USER" })
     * public void testGetUserTaskById() throws Exception {
     * 
     * Long userId = 1L;
     * when(authenticationFacade.getAuthentication()).thenReturn(
     * getMockedAuthentication(userId));
     * when(userTaskService.getUserTaskById(userTask.getId())).thenReturn(userTask);
     * 
     * mockMvc.perform(get("/usertask/task/{userTaskID}", userTask.getId()))
     * .andExpect(status().isOk())
     * .andExpect(result -> {
     * String jsonResponse = result.getResponse().getContentAsString();
     * assertThat(jsonResponse).contains("\"name\":\"Test Task\"");
     * assertThat(jsonResponse).contains("\"userID\":1");
     * });
     * }
     * 
     * @Test
     * 
     * @WithMockUser(username = "testuser", roles = { "USER" })
     * public void testUpdateUserTask() throws Exception {
     * 
     * Long userId = 1L;
     * when(authenticationFacade.getAuthentication()).thenReturn(
     * getMockedAuthentication(userId));
     * 
     * userTask.setBitmask(1);
     * when(userTaskService.updateUserTask(userTask.getId(),
     * userTask)).thenReturn(userTask);
     * 
     * mockMvc.perform(put("/usertask/task-update/{userTaskID}", userTask.getId())
     * .contentType(MediaType.APPLICATION_JSON)
     * .content(objectMapper.writeValueAsString(userTask)))
     * .andExpect(status().isOk())
     * .andExpect(result -> {
     * String jsonResponse = result.getResponse().getContentAsString();
     * assertThat(jsonResponse).contains("\"bitmask\":1");
     * });
     * }
     * 
     * @Test
     * 
     * @WithMockUser(username = "testuser", roles = { "USER" })
     * public void testToggleTaskCompletion() throws Exception {
     * 
     * Long userId = 1L;
     * when(authenticationFacade.getAuthentication()).thenReturn(
     * getMockedAuthentication(userId));
     * 
     * userTask.setBitmask(1);
     * when(userTaskService.getUserTaskById(userTask.getId())).thenReturn(userTask);
     * when(userTaskRepository.save(userTask)).thenReturn(userTask);
     * 
     * mockMvc.perform(put("/usertask/task-toggle/{userTaskID}", userTask.getId()))
     * .andExpect(status().isOk())
     * .andExpect(result -> {
     * String jsonResponse = result.getResponse().getContentAsString();
     * assertThat(jsonResponse).contains("\"status\":\"success\"");
     * });
     * 
     * // Verify that the task's bitmask was updated (toggled)
     * assertThat(userTask.getBitmask()).isEqualTo(1);
     * }
     * 
     * @Test
     * 
     * @WithMockUser(username = "testuser", roles = { "USER" })
     * public void testDeleteUserTask() throws Exception {
     * 
     * Long userId = 1L;
     * when(authenticationFacade.getAuthentication()).thenReturn(
     * getMockedAuthentication(userId));
     * 
     * mockMvc.perform(delete("/usertask/task-delete/{userTaskID}",
     * userTask.getId()))
     * .andExpect(status().isNoContent());
     * 
     * // Verify that the task was deleted
     * when(userTaskRepository.findById(userTask.getId())).thenReturn(java.util.
     * Optional.empty());
     * assertThat(userTaskRepository.findById(userTask.getId()).isEmpty()).isTrue();
     * }
     * 
     * @Test
     * 
     * @WithMockUser(username = "testuser", roles = { "USER" })
     * public void testCountCompletedTasksForDay() throws Exception {
     * 
     * Long userId = 1L;
     * int currentDay = 1; // Example day
     * when(authenticationFacade.getAuthentication()).thenReturn(
     * getMockedAuthentication(userId));
     * when(userTaskService.countCompletedTasksForday(userId,
     * currentDay)).thenReturn(1);
     * 
     * mockMvc.perform(get("/usertask/completed-tasks/{day}", currentDay))
     * .andExpect(status().isOk())
     * .andExpect(result -> {
     * String jsonResponse = result.getResponse().getContentAsString();
     * assertThat(jsonResponse).isEqualTo("1");
     * });
     * }
     */
}
