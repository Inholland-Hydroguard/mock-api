package hydroguard.api.mock.models.household;

import hydroguard.api.mock.models.GetMoreDTO;

import java.util.List;

public class HouseholdsDTO extends GetMoreDTO<HouseholdDTO> {
    public HouseholdsDTO() {
        super();
    }

    public HouseholdsDTO(List<HouseholdDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
