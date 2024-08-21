package uk.ac.cf.spring.nhs.Task.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Task.Model.Task;
import uk.ac.cf.spring.nhs.Task.Repository.JpaTaskRepository;

@Service
public class TaskService {

    @Autowired
    private JpaTaskRepository taskRepository;

    /**
     * Retrieves a list of all tasks.
     *
     * @return          a list of tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param  id	the ID of the task to retrieve
     * @return      an Optional containing the task if found, or an empty Optional if the task is not found
     */
    public Task getTaskById(long id) {
        return taskRepository.findById(id);
    }

    /**
     * Creates a new task.
     *
     * @param  task	the task to be created
     * @return     	the created task
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Updates an existing task in the repository.
     *
     * @param  id       the ID of the task to update
     * @param  task     the updated task details
     * @return          the updated task if found, or null if the task is not found
     */
    public Task updateTask(Long id, Task task) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            return taskRepository.save(task);
        }
        return null;
    }

    /**
     * Deletes a task by its ID if it exists in the repository.
     *
     * @param  id	the ID of the task to delete
     * @return      void
     */
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }
}
