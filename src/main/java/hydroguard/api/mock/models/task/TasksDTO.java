package hydroguard.api.mock.models.task;

import java.util.List;

public class TasksDTO {
    private List<TaskDTO> tasks;
    private int total;
    private int page;

    public TasksDTO(List<TaskDTO> tasks, int total, int page) {
        this.tasks = tasks;
        this.total = total;
        this.page = page;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}