package hydroguard.api.mock.models.examples;

import java.util.UUID;

/// <summary>
/// Represents a DTO for a model example. Since this is a GET, do not include the secret attribute.
/// </summary>
public class GetModelExampleDTO {
    private UUID id;
    private String name;

    // Constructors
    public GetModelExampleDTO() {
    }

    public GetModelExampleDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
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
}
