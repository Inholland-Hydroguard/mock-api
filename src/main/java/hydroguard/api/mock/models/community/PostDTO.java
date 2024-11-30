package hydroguard.api.mock.models.community;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostDTO {
    private UUID id;
    private String title;
    private String content;
    private UUID groupId;
    private String imageUrl;
    private UUID userId;
    private List<CommentDTO> comments; // List of comment IDs
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public PostDTO(UUID id, String title, String content, UUID groupId, String imageUrl, UUID userId,
                   List<CommentDTO> comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.groupId = groupId;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PostDTO(String title, String content, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.groupId = UUID.randomUUID();
        this.imageUrl = imageUrl;
        this.userId = UUID.fromString("00000000-00000000-00000000-00000000");
        this.comments = new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PostDTO() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters omitted for brevity

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
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