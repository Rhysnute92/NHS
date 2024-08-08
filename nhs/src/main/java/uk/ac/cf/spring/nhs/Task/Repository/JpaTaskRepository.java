package uk.ac.cf.spring.nhs.Task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Task.Model.Task;

public interface JpaTaskRepository extends JpaRepository<Task, Long> {
}
