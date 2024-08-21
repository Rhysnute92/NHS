package uk.ac.cf.spring.nhs.UserTask.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.ac.cf.spring.nhs.Common.util.BitmaskUtility;
import uk.ac.cf.spring.nhs.Common.util.DateUtils;
import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;
import uk.ac.cf.spring.nhs.UserTask.Repository.JpaUserTaskRepository;
import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Service.UserTaskLogService;

public class UserTaskServiceTest {

    @InjectMocks
    private UserTaskService userTaskService;

    @Mock
    private JpaUserTaskRepository userTaskRepository;

    @Mock
    private UserTaskLogService userTaskLogService;

    private UserTask userTask;
    private Task task;
    private Long userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = 1L;

        task = new Task();
        task.setName("Daily Exercise");

        userTask = new UserTask();
        userTask.setId(1L);
        userTask.setUserID(1L);
        userTask.setTask(task);
        userTask.setBitmask(0); // No tasks completed yet
    }

    @Test
    public void testToggleTaskCompletion() {
        when(userTaskRepository.save(any(UserTask.class))).thenReturn(userTask);

        userTaskService.toggleTaskCompletion(userTask);
        verify(userTaskRepository, times(1)).save(userTask);

        assertThat(userTask.getBitmask()).isGreaterThan(0);

        // Toggle again to mark as incomplete
        userTaskService.toggleTaskCompletion(userTask);
        verify(userTaskRepository, times(2)).save(userTask);
        assertThat(userTask.getBitmask()).isEqualTo(0);
    }

    @Test
    public void testIsTaskCompleted() {
        int currentDay = DateUtils.getCurrentDayOfMonth(); // Get the current day
        userTask.setBitmask(BitmaskUtility.setBit(userTask.getBitmask(), currentDay)); // Set the bit for the current
                                                                                       // day

        boolean isCompleted = userTaskService.isTaskCompleted(userTask);

        assertThat(isCompleted).isTrue();
    }

    @Test
    public void testArchiveAndResetMonthlyBitmask() {
        when(userTaskLogService.createUserTaskLog(any(UserTaskLog.class))).thenReturn(new UserTaskLog());
        when(userTaskRepository.save(any(UserTask.class))).thenReturn(userTask);

        userTaskService.archiveAndResetMonthlyBitmask(userTask);

        verify(userTaskLogService, times(1)).createUserTaskLog(any(UserTaskLog.class));
        verify(userTaskRepository, times(1)).save(userTask);
        assertThat(userTask.getBitmask()).isEqualTo(0);
    }

    @Test
    public void testCountCompletedTasksForDay() {
        userTask.setBitmask(BitmaskUtility.setBit(userTask.getBitmask(), 1));
        when(userTaskRepository.findByUserID(1L)).thenReturn(Arrays.asList(userTask));

        int completedTasks = userTaskService.countCompletedTasksForday(1L, 1);

        assertThat(completedTasks).isEqualTo(1);
    }

    // @Test
    // public void testAssignTaskToUser() {
    //     when(userTaskRepository.save(userTask)).thenReturn(userTask);

    //     UserTask savedTask = userTaskService.assignTaskToUser(task, userId);

    //     assertThat(savedTask).isNotNull();
    //     verify(userTaskRepository, times(1)).save(userTask);
    // }

    @Test
    public void testGetTasksForUser() {
        when(userTaskRepository.findByUserID(1L)).thenReturn(Arrays.asList(userTask));

        List<UserTask> tasks = userTaskService.getTasksForUser(1L);

        assertThat(tasks).isNotEmpty();
        assertThat(tasks.size()).isEqualTo(1);
        verify(userTaskRepository, times(1)).findByUserID(1L);
    }

    @Test
    public void testGetUserTaskById() {
        when(userTaskRepository.findById(1L)).thenReturn(Optional.of(userTask));

        UserTask foundTask = userTaskService.getUserTaskById(1L);

        assertThat(foundTask).isNotNull();
        verify(userTaskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserTaskById_NotFound() {
        when(userTaskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userTaskService.getUserTaskById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("UserTask not found");
    }

    @Test
    public void testUpdateUserTask() {
        when(userTaskRepository.findById(1L)).thenReturn(Optional.of(userTask));
        when(userTaskRepository.save(any(UserTask.class))).thenReturn(userTask);

        userTask.setBitmask(2);
        UserTask updatedTask = userTaskService.updateUserTask(1L, userTask);

        assertThat(updatedTask.getBitmask()).isEqualTo(2);
        verify(userTaskRepository, times(1)).save(userTask);
    }

    @Test
    public void testUpdateUserTasksBatch() {
        when(userTaskRepository.save(any(UserTask.class))).thenReturn(userTask);

        List<UserTask> tasks = userTaskService.updateUserTasksBatch(Arrays.asList(userTask));

        assertThat(tasks).isNotNull();
        verify(userTaskRepository, times(1)).save(userTask);
    }

    @Test
    public void testDeleteUserTask() {
        when(userTaskRepository.findById(1L)).thenReturn(Optional.of(userTask));
        doNothing().when(userTaskRepository).delete(userTask);

        userTaskService.deleteUserTask(1L);

        verify(userTaskRepository, times(1)).delete(userTask);
    }

    @Test
    public void testDeleteUserTask_NotFound() {
        when(userTaskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userTaskService.deleteUserTask(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("UserTask not found");
    }
}
