package hydroguard.api.mock.controllers.subscriptions;

import hydroguard.api.mock.models.subscriptions.SubscriptionPlanDTO;
import hydroguard.api.mock.models.subscriptions.SubscriptionsPlanDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final List<SubscriptionPlanDTO> subscriptions = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Initialize with some sample data
        subscriptions.add(new SubscriptionPlanDTO(UUID.randomUUID(), "Monthly Plan", "Monthly subscription plan", 9.99f, SubscriptionPlanDTO.BillingCycle.MONTHLY, LocalDateTime.now(), LocalDateTime.now()));
        subscriptions.add(new SubscriptionPlanDTO(UUID.randomUUID(), "Annually Plan", "Annually subscription plan", 19.99f, SubscriptionPlanDTO.BillingCycle.ANNUALLY, LocalDateTime.now(), LocalDateTime.now()));
        subscriptions.add(new SubscriptionPlanDTO(UUID.randomUUID(), "Biannually Plan", "Biannually subscription plan", 19.99f, SubscriptionPlanDTO.BillingCycle.BIANNUALLY, LocalDateTime.now(), LocalDateTime.now()));
    }


    @GetMapping
    public ResponseEntity<SubscriptionsPlanDTO> getAllSubscriptions() {
        return ResponseEntity.ok(new SubscriptionsPlanDTO(subscriptions, subscriptions.size(), 1));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDTO> getSubscriptionById(@PathVariable UUID id) {
        SubscriptionPlanDTO subscription = subscriptions.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (subscription == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(subscription);
    }
}