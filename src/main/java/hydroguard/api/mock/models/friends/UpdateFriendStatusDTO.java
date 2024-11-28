package hydroguard.api.mock.models.friends;

import lombok.Data;

@Data
public class UpdateFriendStatusDTO {
    private String friendId;
    private Friend.Status status;
}
