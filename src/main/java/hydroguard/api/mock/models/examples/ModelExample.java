package hydroguard.api.mock.models.examples;

import java.util.UUID;

/// <summary>
/// Represents a model example.
/// </summary>
public class ModelExample {
    private UUID id;
    private String name;

    private String secretAttribute;

    // Constructors
    public ModelExample(UUID id, String name) {
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

    public String getSecretAttribute() {
        return secretAttribute;
    }

    public void setSecretAttribute(String secretAttribute) {
        this.secretAttribute = secretAttribute;
    }
}
