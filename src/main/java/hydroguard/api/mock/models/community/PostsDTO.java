package hydroguard.api.mock.models.community;

import java.util.List;

import hydroguard.api.mock.models.GetMoreDTO;

public class PostsDTO extends GetMoreDTO<PostDTO> {
    public PostsDTO() {
        super();
    }

    public PostsDTO(List<PostDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
