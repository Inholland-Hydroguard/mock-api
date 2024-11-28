package hydroguard.api.mock.models.household;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HouseholdDTO {
    private UUID id;
    private String name;
    private String city;
    private List<HouseholdUserDTO> members = new ArrayList<>();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    public HouseholdDTO(String name, String city) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.city = city;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public HouseholdDTO(UUID id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<HouseholdUserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<HouseholdUserDTO> members) {
        this.members = members;
    }

    public HouseholdDTO(UUID id, String name, String city, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}