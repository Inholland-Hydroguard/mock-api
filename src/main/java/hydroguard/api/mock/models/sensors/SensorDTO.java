package hydroguard.api.mock.models.sensors;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class SensorDTO {
    private UUID id; // RowKey
    private UUID householdId; // PartitionKey
    private UUID plantId;
    private String name;
    private Float thresholdMin;
    private Float thresholdMax;
    private Byte batteryLevel;
    private Boolean isOutdoor;
    private Boolean isActive;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public SensorDTO(UUID id, UUID householdId, UUID plantId, String name, Float thresholdMin, Float thresholdMax,
                     Byte batteryLevel, Boolean isOutdoor, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.householdId = householdId;
        this.plantId = plantId;
        this.name = name;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.batteryLevel = batteryLevel;
        this.isOutdoor = isOutdoor;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public SensorDTO() {
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

    public UUID getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(UUID householdId) {
        this.householdId = householdId;
    }

    public UUID getPlantId() {
        return plantId;
    }

    public void setPlantId(UUID plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getThresholdMin() {
        return thresholdMin;
    }

    public void setThresholdMin(Float thresholdMin) {
        this.thresholdMin = thresholdMin;
    }

    public Float getThresholdMax() {
        return thresholdMax;
    }

    public void setThresholdMax(Float thresholdMax) {
        this.thresholdMax = thresholdMax;
    }

    public Byte getBatteryLevel() {
        return batteryLevel;
    }

    public Boolean getIsOutdoor() {
        return isOutdoor;
    }

    public void setIsOutdoor(Boolean isOutdoor) {
        this.isOutdoor = isOutdoor;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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