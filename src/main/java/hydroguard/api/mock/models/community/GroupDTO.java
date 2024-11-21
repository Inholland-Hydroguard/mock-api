package hydroguard.api.mock.models.community;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GroupDTO {
    private UUID id;
    private String groupName;
    private String description;
    private UUID groupOwner;
    private List<PostDTO> posts; // List of posts in the group
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public GroupDTO(UUID id, String groupName, String description, UUID groupOwner, List<PostDTO> posts,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.groupName = groupName;
        this.description = description;
        this.groupOwner = groupOwner;
        this.posts = posts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public GroupDTO() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(UUID groupOwner) {
        this.groupOwner = groupOwner;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
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