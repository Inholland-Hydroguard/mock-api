package hydroguard.api.mock.models.community;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CommentDTO {
    private UUID id;
    private UUID userId;
    private UUID parentId; // Can be postId or commentId
    private String userName;
    private String content;
    private int likes;
    private int dislikes;
    private List<String> reactions; // String representation of reactions
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    public CommentDTO(UUID id, UUID userId, UUID parentId, String userName, String content, int likes, int dislikes,
                      List<String> reactions, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.parentId = parentId;
        this.userName = userName;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.reactions = reactions;
        this.createdAt = createdAt;
    }

    public CommentDTO() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public List<String> getReactions() {
        return reactions;
    }

    public void setReactions(List<String> reactions) {
        this.reactions = reactions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
