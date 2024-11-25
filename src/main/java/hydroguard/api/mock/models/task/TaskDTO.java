package hydroguard.api.mock.models.task;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskDTO {
    private UUID id;
    private UUID userId;
    private UUID plantId;
    private String description;
    private boolean completed;
    private LocalDateTime dueAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskDTO(UUID id, UUID userId, UUID plantId, String description, boolean completed, LocalDateTime dueAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.plantId = plantId;
        this.description = description;
        this.completed = completed;
        this.dueAt = dueAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public UUID getPlantId() {
        return plantId;
    }

    public void setPlantId(UUID plantId) {
        this.plantId = plantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
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