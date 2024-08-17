package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import uk.ac.cf.spring.nhs.Common.util.BitmaskUtility;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

@WebMvcTest(controllers = WidgetRestAPIController.class)
class WidgetRestAPIControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationInterface authenticationFacade;

    @MockBean
    private UserTaskService userTaskService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    private List<UserTask> mockTaskList(int numberOfTasks) {
        List<UserTask> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(new UserTask()); // Add mocked UserTask instances
        }
        return tasks;
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void testGetTaskCompletionData() throws Exception {
        // Arrange
        Long userId = 1L;
        CustomUserDetails customUserDetails = new CustomUserDetails(
                userId,
                "testuser",
                "password",
                List.of() // Assuming no authorities for simplicity
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());

        // Mock the behavior of the authenticationFacade to return the mocked
        // Authentication object
        when(authenticationFacade.getAuthentication()).thenReturn(auth);

        int currentDay = LocalDate.now().getDayOfWeek().getValue();

        when(userTaskService.getTasksForUser(userId)).thenReturn(mockTaskList(1)); // 1 task
        when(userTaskService.countCompletedTasksForday(userId, currentDay)).thenReturn(1); // 1 completed task

        // Act & Assert
        mockMvc.perform(get("/api/widgets/task-completion/data")
                .param("day", String.valueOf(currentDay)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertThat(jsonResponse).contains("\"totalTasks\":1");
                    assertThat(jsonResponse).contains("\"completedTasks\":1");
                });
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void testWidgetReceivesUpdatedTaskData() throws Exception {
        // Arrange
        Long userId = 1L;
        CustomUserDetails customUserDetails = new CustomUserDetails(
                userId,
                "testuser",
                "password",
                Collections.emptyList());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());

        // Mock the behavior of the authenticationFacade to return the mocked
        // Authentication object
        when(authenticationFacade.getAuthentication()).thenReturn(auth);

        int currentDay = LocalDate.now().getDayOfWeek().getValue();

        // Creating 5 tasks, where 2 tasks are completed
        List<UserTask> tasks = mockTaskList(5);
        tasks.get(0).setBitmask(BitmaskUtility.setBit(0, LocalDate.now().getDayOfMonth())); // Task 1 completed
        tasks.get(1).setBitmask(BitmaskUtility.setBit(0, LocalDate.now().getDayOfMonth())); // Task 2 completed

        when(userTaskService.getTasksForUser(userId)).thenReturn(tasks); // 5 tasks in total
        when(userTaskService.countCompletedTasksForday(userId, currentDay)).thenReturn(2); // 2 completed tasks

        // Act & Assert
        mockMvc.perform(get("/api/widgets/task-completion/data")
                .param("day", String.valueOf(currentDay)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertThat(jsonResponse).contains("\"totalTasks\":5");
                    assertThat(jsonResponse).contains("\"completedTasks\":2");
                });
    }
}
