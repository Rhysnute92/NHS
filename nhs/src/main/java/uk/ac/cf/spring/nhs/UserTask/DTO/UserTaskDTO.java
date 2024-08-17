package uk.ac.cf.spring.nhs.UserTask.DTO;

public class UserTaskDTO {
    private int totalTasks;
    private int completedTasks;

    // Constructors, Getters, and Setters
    public UserTaskDTO(int totalTasks, int completedTasks) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }
}
