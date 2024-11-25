package hydroguard.api.mock.models.reocurringTask;

import java.util.List;

public class ReoccurringTasksDTO {
    private List<ReoccurringTaskDTO> tasks;
    private int total;
    private int page;

    public ReoccurringTasksDTO(List<ReoccurringTaskDTO> tasks, int total, int page) {
        this.tasks = tasks;
        this.total = total;
        this.page = page;
    }

    public List<ReoccurringTaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<ReoccurringTaskDTO> tasks) {
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