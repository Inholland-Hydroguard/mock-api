package hydroguard.api.mock.models.account;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public AccountDTO(UUID id, String username, String avatarUrl, String email) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    // Getters and setters for all fields

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStreaks() {
        return streaks;
    }

    public void setStreaks(int streaks) {
        this.streaks = streaks;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}