package hydroguard.api.mock.models.measurements;

import hydroguard.api.mock.models.GetMoreDTO;

import java.util.List;

public class MeasurementsDTO extends GetMoreDTO<MeasurementDTO> {
    public MeasurementsDTO() {
    }

    public MeasurementsDTO(List<MeasurementDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
