package hydroguard.api.mock.models.community;

import java.util.List;

import hydroguard.api.mock.models.GetMoreDTO;

public class CommentsDTO extends GetMoreDTO<CommentDTO> {
    public CommentsDTO() {
        super();
    }

    public CommentsDTO(List<CommentDTO> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
