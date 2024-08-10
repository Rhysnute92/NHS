package uk.ac.cf.spring.nhs.UserTaskLog.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Repository.JpaUserTaskLogRepository;

@Service
public class UserTaskLogService {
    @Autowired
    private JpaUserTaskLogRepository userTaskLogRepository;

    // CRUD Functions

    @Transactional
    public UserTaskLog createUserTaskLog(UserTaskLog userTaskLog) {
        return userTaskLogRepository.save(userTaskLog);
    }

    @Transactional(readOnly = true)
    public UserTaskLog getUserTaskLogById(Long id) {
        return userTaskLogRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UserTaskLog not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<UserTaskLog> getAllUserTaskLogs() {
        return userTaskLogRepository.findAll();
    }

    @Transactional
    public UserTaskLog updateUserTaskLog(Long id, UserTaskLog updatedLog) {
        UserTaskLog existingLog = getUserTaskLogById(id);
        existingLog.setBitmask(updatedLog.getBitmask());
        existingLog.setMonthYear(updatedLog.getMonthYear());
        existingLog.setCreatedAt(updatedLog.getCreatedAt());
        return userTaskLogRepository.save(existingLog);
    }

    @Transactional
    public void deleteUserTaskLog(Long id) {
        userTaskLogRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UserTaskLog> getUserTaskLogByUserID(Long UserID) {
        return userTaskLogRepository.findAllByUserID(UserID);
    }

}
