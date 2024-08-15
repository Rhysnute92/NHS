package uk.ac.cf.spring.nhs.UserTaskLog.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;
import uk.ac.cf.spring.nhs.UserTaskLog.Repository.JpaUserTaskLogRepository;

@Service
public class UserTaskLogService {
    @Autowired
    private JpaUserTaskLogRepository userTaskLogRepository;

    // CRUD Functions
    /**
     * Creates a new UserTaskLog entry.
     *
     * @param userTaskLog The UserTaskLog entity to create.
     * @return The created UserTaskLog entity.
     */
    @Transactional
    public UserTaskLog createUserTaskLog(UserTaskLog userTaskLog) {
        return userTaskLogRepository.save(userTaskLog);
    }

    /**
     * Retrieves a UserTaskLog by its ID.
     *
     * @param id The ID of the UserTaskLog to retrieve.
     * @return The UserTaskLog entity.
     * @throws ResourceAccessException if the UserTaskLog with the given ID does
     *                                   not exist.
     */
    @Transactional(readOnly = true)
    public UserTaskLog getUserTaskLogById(Long id) {
        return userTaskLogRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("UserTaskLog not found with id: " + id));
    }

    /**
     * Retrieves all UserTaskLog entries.
     *
     * @return A list of all UserTaskLog entities.
     */
    @Transactional(readOnly = true)
    public List<UserTaskLog> getAllUserTaskLogs() {
        return userTaskLogRepository.findAll();
    }

    /**
     * Updates an existing UserTaskLog entry.
     *
     * @param id         The ID of the UserTaskLog to update.
     * @param updatedLog The updated UserTaskLog entity.
     * @return The updated UserTaskLog entity.
     * @throws ResourceAccessException if the UserTaskLog with the given ID does
     *                                   not exist.
     */
    @Transactional
    public UserTaskLog updateUserTaskLog(Long id, UserTaskLog updatedLog) {
        UserTaskLog existingLog = getUserTaskLogById(id);
        existingLog.setBitmask(updatedLog.getBitmask());
        existingLog.setMonthYear(updatedLog.getMonthYear());
        existingLog.setCreatedAt(updatedLog.getCreatedAt());
        return userTaskLogRepository.save(existingLog);
    }

    /**
     * Deletes a UserTaskLog by its ID.
     *
     * @param id The ID of the UserTaskLog to delete.
     */
    @Transactional
    public void deleteUserTaskLog(Long id) {
        userTaskLogRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UserTaskLog> getUserTaskLogByUserID(Long UserID) {
        return userTaskLogRepository.findAllByUserID(UserID);
    }

}
