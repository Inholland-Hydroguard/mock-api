package hydroguard.api.mock.models.examples;

import java.util.UUID;

/// <summary>
/// Represents a DTO for a model example. Since this is a POST, include the secret attribute, but not the id in the ctor.
/// </summary>
public class CreateModelExampleDTO {
    private UUID id;
    private String name;

    private String secretAttribute;

    public CreateModelExampleDTO(String name, String secretAttribute) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.secretAttribute = secretAttribute;
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

    public String getSecretAttribute() {
        return secretAttribute;
    }

    public void setSecretAttribute(String secretAttribute) {
        this.secretAttribute = secretAttribute;
    }
}
