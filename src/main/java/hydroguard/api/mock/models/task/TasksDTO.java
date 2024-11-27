package hydroguard.api.mock.models.task;

import hydroguard.api.mock.models.GetMoreDTO;

import java.util.List;

public class TasksDTO extends GetMoreDTO<TaskDTO> {
    public TasksDTO() {
    }

    public TasksDTO(List<TaskDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }

}