package uk.ac.cf.spring.nhs.UserTask.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.UserTask.Model.UserTask;

public interface JpaUserTaskRepository extends JpaRepository<UserTask, Long> {

    public List<UserTask> findByUserID(Long userID);
    
}
