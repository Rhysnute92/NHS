package uk.ac.cf.spring.nhs.UserTaskLog.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.ResourceAccessException;

import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Repository.JpaUserTaskLogRepository;

public class UserTaskLogServiceUnitTests {

    @Mock
    private JpaUserTaskLogRepository userTaskLogRepository;

    @InjectMocks
    private UserTaskLogService userTaskLogService;

    private UserTaskLog userTaskLog;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userTaskLog = new UserTaskLog();
        userTaskLog.setId(1L);
        userTaskLog.setUserID(123L);
        userTaskLog.setBitmask(31);
        userTaskLog.setMonthYear("2024-08");
        userTaskLog.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreateUserTaskLog() {
        when(userTaskLogRepository.save(userTaskLog)).thenReturn(userTaskLog);

        UserTaskLog createdLog = userTaskLogService.createUserTaskLog(userTaskLog);

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getId()).isEqualTo(1L);
        verify(userTaskLogRepository, times(1)).save(userTaskLog);
    }

    @Test
    public void testGetUserTaskLogById_Found() {
        when(userTaskLogRepository.findById(1L)).thenReturn(Optional.of(userTaskLog));

        UserTaskLog foundLog = userTaskLogService.getUserTaskLogById(1L);

        assertThat(foundLog).isNotNull();
        assertThat(foundLog.getId()).isEqualTo(1L);
        verify(userTaskLogRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserTaskLogById_NotFound() {
        when(userTaskLogRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceAccessException.class, () -> userTaskLogService.getUserTaskLogById(1L));

        verify(userTaskLogRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllUserTaskLogs() {
        List<UserTaskLog> logs = new ArrayList<>();
        logs.add(userTaskLog);
        when(userTaskLogRepository.findAll()).thenReturn(logs);

        List<UserTaskLog> foundLogs = userTaskLogService.getAllUserTaskLogs();

        assertThat(foundLogs).hasSize(1);
        verify(userTaskLogRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateUserTaskLog_Found() {
        when(userTaskLogRepository.findById(1L)).thenReturn(Optional.of(userTaskLog));
        when(userTaskLogRepository.save(userTaskLog)).thenReturn(userTaskLog);

        UserTaskLog updatedLog = new UserTaskLog();
        updatedLog.setBitmask(99);
        updatedLog.setMonthYear("2025-09");
        updatedLog.setCreatedAt(LocalDateTime.now());

        UserTaskLog result = userTaskLogService.updateUserTaskLog(1L, updatedLog);

        assertThat(result).isNotNull();
        assertThat(result.getBitmask()).isEqualTo(99);
        assertThat(result.getMonthYear()).isEqualTo("2025-09");
        verify(userTaskLogRepository, times(1)).findById(1L);
        verify(userTaskLogRepository, times(1)).save(userTaskLog);
    }

    @Test
    public void testUpdateUserTaskLog_NotFound() {
        when(userTaskLogRepository.findById(1L)).thenReturn(Optional.empty());

        UserTaskLog updatedLog = new UserTaskLog();
        updatedLog.setBitmask(99);

        assertThrows(ResourceAccessException.class, () -> userTaskLogService.updateUserTaskLog(1L, updatedLog));

        verify(userTaskLogRepository, times(1)).findById(1L);
        verify(userTaskLogRepository, times(0)).save(any(UserTaskLog.class));
    }

    @Test
    public void testDeleteUserTaskLog() {
        doNothing().when(userTaskLogRepository).deleteById(1L);

        userTaskLogService.deleteUserTaskLog(1L);

        verify(userTaskLogRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetUserTaskLogByUserID() {
        List<UserTaskLog> logs = new ArrayList<>();
        logs.add(userTaskLog);
        when(userTaskLogRepository.findAllByUserID(123L)).thenReturn(logs);

        List<UserTaskLog> foundLogs = userTaskLogService.getUserTaskLogByUserID(123L);

        assertThat(foundLogs).hasSize(1);
        verify(userTaskLogRepository, times(1)).findAllByUserID(123L);
    }

    // Edge cases
    @Test
    public void testCreateUserTaskLog_NullLog() {
        when(userTaskLogRepository.save(null)).thenThrow(new IllegalArgumentException("UserTaskLog cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> userTaskLogService.createUserTaskLog(null));

        verify(userTaskLogRepository, times(1)).save(null);
    }

    @Test
    public void testGetUserTaskLogById_NegativeId() {
        assertThrows(ResourceAccessException.class, () -> userTaskLogService.getUserTaskLogById(-1L));
        verify(userTaskLogRepository, times(1)).findById(-1L);
    }

    @Test
    public void testUpdateUserTaskLog_NullUpdate() {
        when(userTaskLogRepository.findById(1L)).thenReturn(Optional.of(userTaskLog));

        assertThrows(NullPointerException.class, () -> userTaskLogService.updateUserTaskLog(1L, null));

        verify(userTaskLogRepository, times(1)).findById(1L);
        verify(userTaskLogRepository, times(0)).save(any(UserTaskLog.class));
    }

    @Test
    public void testDeleteUserTaskLog_NonExistentId() {
        doThrow(new ResourceAccessException("UserTaskLog not found")).when(userTaskLogRepository).deleteById(99L);

        assertThrows(ResourceAccessException.class, () -> userTaskLogService.deleteUserTaskLog(99L));

        verify(userTaskLogRepository, times(1)).deleteById(99L);
    }
}
