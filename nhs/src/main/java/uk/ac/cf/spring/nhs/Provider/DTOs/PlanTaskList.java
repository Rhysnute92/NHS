package uk.ac.cf.spring.nhs.Provider.DTOs;

import java.util.List;

public class PlanTaskList {

    private List<String> taskList;

    public List<String> getTaskList() {
        return taskList;
    }
    public void setTaskList(List<String> taskList) {
        this.taskList = taskList;
    }
    
}
