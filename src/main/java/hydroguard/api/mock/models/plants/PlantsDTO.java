package hydroguard.api.mock.models.plants;

import hydroguard.api.mock.models.GetMoreDTO;
import hydroguard.api.mock.models.measurements.MeasurementDTO;

import java.util.List;

public class PlantsDTO extends GetMoreDTO<PlantDTO> {
    public PlantsDTO() {
        super();
    }

    public PlantsDTO(List<PlantDTO> data, int total, int currentPage) {
        super(data, total,  currentPage);
    }
}
