package hydroguard.api.mock.models.household;

import java.util.UUID;

public class HouseholdUserDTO {
    private UUID userId;
    private String username;
    private String avatarUrl;
    private String role;

    public HouseholdUserDTO(UUID userId, String username, String avatarUrl, String role) {
        this.userId = userId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
