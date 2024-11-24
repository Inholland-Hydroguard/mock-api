package hydroguard.api.mock.models.friends;

import lombok.Data;

@Data
public class UpdateFriendStatusDTO {
    private String userId1;
    private String userId2;
    private Friends.Status status;
}
