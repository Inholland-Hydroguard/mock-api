package hydroguard.api.mock.models.subscriptions;

import java.time.LocalDateTime;
import java.util.UUID;

public class SubscriptionPlanDTO {
    private UUID id;
    private String name;
    private String description;
    private float price;
    private BillingCycle billingCycle;

    // Enum for billing cycle
    public enum BillingCycle {
        MONTHLY,
        BIANNUALLY,
        ANNUALLY
    }

    // Constructors, getters, and setters
    public SubscriptionPlanDTO() {}

    public SubscriptionPlanDTO(UUID id, String name, String description, float price, BillingCycle billingCycle, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.billingCycle = billingCycle;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }
}