package uk.ac.cf.spring.nhs.Widget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserTask.DTO.UserTaskDTO;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Service.UserTaskService;

class WidgetRestAPIControllerUnitTests {

    @InjectMocks
    private WidgetRestAPIController widgetRestAPIController;

    @Mock
    private AuthenticationInterface authenticationFacade;

    @Mock
    private UserTaskService userTaskService;

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
    }

    private List<UserTask> mockTaskList(int numberOfTasks) {
        List<UserTask> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(new UserTask()); // Add mocked UserTask instances
        }
        return tasks;
    }

    @Test
    void getTaskCompletionData_validRequest_returnsCorrectData() {
        // Arrange
        Long userId = 1L;
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        when(customUserDetails.getUserId()).thenReturn(userId);
        when(userTaskService.getTasksForUser(userId)).thenReturn(mockTaskList(5));
        when(userTaskService.countCompletedTasksForday(userId, dayOfMonth)).thenReturn(3);

        // Act
        UserTaskDTO result = widgetRestAPIController.getTaskCompletionData(null);

        // Assert
        assertEquals(5, result.getTotalTasks());
        assertEquals(3, result.getCompletedTasks());
    }

    @Test
    void getTaskCompletionData_specificDay_returnsCorrectData() {
        // Arrange
        Long userId = 1L;
        int specificDay = 3;
        when(customUserDetails.getUserId()).thenReturn(userId);
        when(userTaskService.getTasksForUser(userId)).thenReturn(mockTaskList(4));
        when(userTaskService.countCompletedTasksForday(userId, specificDay)).thenReturn(2);

        // Act
        UserTaskDTO result = widgetRestAPIController.getTaskCompletionData(specificDay);

        // Assert
        assertEquals(4, result.getTotalTasks());
        assertEquals(2, result.getCompletedTasks());
    }
}
