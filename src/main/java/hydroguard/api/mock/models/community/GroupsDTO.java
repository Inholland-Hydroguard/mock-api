package hydroguard.api.mock.models.community;

import java.util.List;

import hydroguard.api.mock.models.GetMoreDTO;

public class GroupsDTO extends GetMoreDTO<GroupDTO> {
    public GroupsDTO() {
        super();
    }

    public GroupsDTO(List<GroupDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
