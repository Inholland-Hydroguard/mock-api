package hydroguard.api.mock.models.household;

import java.util.UUID;

public class RemoveMemberDTO {
    private UUID userId;
    private UUID householdId;

    // Getters and setters
    public UUID getUserId() {
        return userId;
    }

    public UUID getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(UUID householdId) {
        this.householdId = householdId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
