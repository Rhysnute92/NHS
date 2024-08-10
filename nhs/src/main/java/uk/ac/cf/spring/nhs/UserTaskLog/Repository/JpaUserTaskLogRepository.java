package uk.ac.cf.spring.nhs.UserTaskLog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.cf.spring.nhs.UserTaskLog.Model.UserTaskLog;

@Repository
public interface JpaUserTaskLogRepository extends JpaRepository<UserTaskLog, Long> {
    
}
