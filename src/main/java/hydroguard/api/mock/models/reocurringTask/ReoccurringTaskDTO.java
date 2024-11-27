package hydroguard.api.mock.models.reocurringTask;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReoccurringTaskDTO {
    private UUID id;
    private UUID userId;
    private UUID plantId;
    private String description;
    private boolean completed;
    private LocalDateTime dueAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int intervalSeconds;

    public ReoccurringTaskDTO(UUID taskId, UUID uuid, UUID uuid1, String sampleReoccurringTask, boolean b, LocalDateTime now, LocalDateTime now1, LocalDateTime now2, int intervalSeconds) {
        this.id = taskId;
        this.userId = uuid;
        this.plantId = uuid1;
        this.description = sampleReoccurringTask;
        this.completed = b;
        this.dueAt = now;
        this.createdAt = now1;
        this.updatedAt = now2;
        this.intervalSeconds = intervalSeconds;
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

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

}