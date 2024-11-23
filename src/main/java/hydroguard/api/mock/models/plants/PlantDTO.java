package hydroguard.api.mock.models.plants;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class PlantDTO {
    private UUID id;
    private UUID householdId;
    private String name;
    private String species;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public PlantDTO(UUID id, String name, String species, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.householdId = UUID.randomUUID();
        this.name = name;
        this.species = species;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PlantDTO(UUID id) {
        this.id = id;
        this.householdId = UUID.randomUUID();
        String[] names = {"Yellow Vine", "Blue Poppy", "Red Rose", "Green Leaf", "Purple Orchid", "White Lily", "Black Tulip", "Orange Daisy", "Pink Carnation", "Brown Sunflower"};
        Random random = new Random();
        this.name = names[random.nextInt(names.length)];
        this.species = "Plantae";
        this.description = "A plant";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public PlantDTO() {
        this.id = UUID.randomUUID();
        this.householdId = UUID.randomUUID();
        String[] names = {"Yellow Vine", "Blue Poppy", "Red Rose", "Green Leaf", "Purple Orchid", "White Lily", "Black Tulip", "Orange Daisy", "Pink Carnation", "Brown Sunflower"};
        Random random = new Random();
        this.name = names[random.nextInt(names.length)];
        this.species = "Plantae";
        this.description = "A plant";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

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

    public UUID getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(UUID householdId) {
        this.householdId = householdId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
