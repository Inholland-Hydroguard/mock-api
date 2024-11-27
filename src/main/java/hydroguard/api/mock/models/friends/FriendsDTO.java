package hydroguard.api.mock.models.friends;

import java.util.List;
import hydroguard.api.mock.models.GetMoreDTO;

public class FriendsDTO extends GetMoreDTO<Friend> {
    public FriendsDTO() {
        super();
    }

    public FriendsDTO(List<Friend> data, int total, int currentPage) {
        super(data, total, currentPage);
    }
}
