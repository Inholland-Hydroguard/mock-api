package hydroguard.api.mock.models.account;

import hydroguard.api.mock.models.subscriptions.SubscriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private int streaks;
    private String avatarUrl;
    private String phoneNumber;
    private boolean publicProfile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SubscriptionDTO subscription;

    public AccountDTO(UUID id, String username, String avatarUrl, String email) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    public AccountDTO(UUID id, String name, String username, String email, int streaks, String avatarUrl, String phoneNumber, boolean publicProfile, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.streaks = streaks;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
        this.publicProfile = publicProfile;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}