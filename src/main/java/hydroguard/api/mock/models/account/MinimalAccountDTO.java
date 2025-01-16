package hydroguard.api.mock.models.account;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinimalAccountDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String avatarUrl;
    private boolean publicProfile;
}
