package hydroguard.api.mock.models.reocurringTask;
import hydroguard.api.mock.models.GetMoreDTO;
import java.util.List;

public class ReoccurringTasksDTO extends GetMoreDTO<ReoccurringTaskDTO> {
    public ReoccurringTasksDTO() {
    }

    public ReoccurringTasksDTO(List<ReoccurringTaskDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }

}