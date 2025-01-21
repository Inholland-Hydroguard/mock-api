package hydroguard.api.mock.controllers.account;

import hydroguard.api.mock.models.account.AccountDTO;
import hydroguard.api.mock.models.account.AccountsDTO;
import hydroguard.api.mock.models.account.MinimalAccountDTO;
import hydroguard.api.mock.models.account.MinimalAccountsDTO;
import hydroguard.api.mock.models.subscriptions.SubscriptionDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final List<AccountDTO> accounts = new ArrayList<>();

    @PostConstruct
    public void init() {
        accounts.add(new AccountDTO(UUID.fromString("0000000-0000-0000-0000-000000000000"), "John Doe Alexander Johnson White Carper Jones", "johndoewithalongerusernamethanmost", "john.doe@example.com", 501, "https://robohash.org/8b70f91f5eae6ada462f8efbc9c40d17?set=set4&bgset=&size=400x400", "06-XXXXXXXX", true, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1)));
        accounts.add(new AccountDTO(UUID.fromString("934b03fa-d8f1-4d8b-8b03-fad8f17d8b0e"), "Jane Smith", "j4n3sm1th", "jane.smith@example.com", 10, "https://robohash.org/a476485bc5d85adc20f23cbf5a02a684?set=set4&bgset=&size=400x400", "06-XXXXXXXX", false, LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(2)));
        accounts.add(new AccountDTO(UUID.fromString("a22883c0-76ba-42db-a883-c076ba32db10"), "Alice Johnson", "aj", "alice.johnson@example.com", 15, "https://robohash.org/398ca7969c2b1966070f6d4fff3509f0?set=set4&bgset=&size=400x400", "06-XXXXXXXX", true, LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(3)));
        accounts.add(new AccountDTO(UUID.randomUUID(), "Bob Brown", "bobb", "bob.brown@example.com", 20, "https://robohash.org/610b9cfb18e50d33e7fef6d56905b992?set=set4&bgset=&size=400x400", "06-XXXXXXXX", false, LocalDateTime.now().minusDays(40), LocalDateTime.now().minusDays(4)));
        accounts.add(new AccountDTO(UUID.fromString("dc2f92e9-5475-4f2a-af92-e95475df2ab5"), "Charlie Davis", "charlied", "charlie.davis@example.com", 25, "https://robohash.org/3f7aaf88ccba1265747142e405d72b9c?set=set4&bgset=&size=400x400", "06-XXXXXXXX", true, LocalDateTime.now().minusDays(50), LocalDateTime.now().minusDays(5)));
        accounts.add(new AccountDTO(UUID.randomUUID(), "David Evans", "davide", "david.evans@example.com", 30, "https://robohash.org/4f7aaf88ccba1265747142e405d72b9c?set=set4&bgset=&size=400x400", "06-XXXXXXXX", true, LocalDateTime.now().minusDays(60), LocalDateTime.now().minusDays(6)));
        accounts.add(new AccountDTO(UUID.randomUUID(), "Eve Foster", "evef", "eve.foster@example.com", 35, "https://robohash.org/5f7aaf88ccba1265747142e405d72b9c?set=set4&bgset=&size=400x400", "06-XXXXXXXX", false, LocalDateTime.now().minusDays(70), LocalDateTime.now().minusDays(7)));
    }



    @GetMapping
    public MinimalAccountsDTO getAllAccounts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String avatarUrl,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            @RequestParam(required = false) LocalDateTime updatedFrom,
            @RequestParam(required = false) LocalDateTime updatedTo,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }

        List<AccountDTO> filteredAccounts = accounts;

        filteredAccounts = filteredAccounts.stream()
                .filter(AccountDTO::isPublicProfile)
                .collect(Collectors.toList());

        if (name != null && !name.isEmpty()) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> account.getName().contains(name))
                    .collect(Collectors.toList());
        }

        if (username != null && !username.isEmpty()) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> account.getUsername().contains(username))
                    .collect(Collectors.toList());
        }

        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> account.getAvatarUrl().contains(avatarUrl))
                    .collect(Collectors.toList());
        }

        if (email != null && !email.isEmpty()) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> account.getEmail().contains(email))
                    .collect(Collectors.toList());
        }

        if (createdFrom != null) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> !account.getCreatedAt().isBefore(createdFrom))
                    .collect(Collectors.toList());
        }

        if (createdTo != null) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> !account.getCreatedAt().isAfter(createdTo))
                    .collect(Collectors.toList());
        }

        if (updatedFrom != null) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> !account.getUpdatedAt().isBefore(updatedFrom))
                    .collect(Collectors.toList());
        }

        if (updatedTo != null) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> !account.getUpdatedAt().isAfter(updatedTo))
                    .collect(Collectors.toList());
        }

        if (sortField != null) {
            Comparator<AccountDTO> comparator;
            switch (sortField) {
                case "name":
                    comparator = Comparator.comparing(AccountDTO::getName);
                    break;
                case "username":
                    comparator = Comparator.comparing(AccountDTO::getUsername);
                    break;
                case "email":
                    comparator = Comparator.comparing(AccountDTO::getEmail);
                    break;
                case "avatarUrl":
                    comparator = Comparator.comparing(AccountDTO::getAvatarUrl);
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(AccountDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(AccountDTO::getUpdatedAt);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort field");
            }

            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }

            filteredAccounts.sort(comparator);
        }

        int total = filteredAccounts.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<MinimalAccountDTO> paginatedAccounts = filteredAccounts.subList(start, end).stream()
                .map(account -> new MinimalAccountDTO(
                        account.getId(),
                        account.getName(),
                        account.getUsername(),
                        account.getEmail(),
                        account.getAvatarUrl(),
                        account.isPublicProfile()))
                .collect(Collectors.toList());

        return new MinimalAccountsDTO(paginatedAccounts, total, page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID id) {
        AccountDTO account = accounts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(account);
    }

    @PutMapping()
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO account) {
        AccountDTO existingAccount = accounts.stream()
                .filter(a -> a.getId().equals(UUID.fromString("0000000-0000-0000-0000-000000000000")))
                .findFirst()
                .orElse(null);
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        existingAccount.setName(account.getName());
        existingAccount.setUsername(account.getUsername());
        existingAccount.setEmail(account.getEmail());
        existingAccount.setStreaks(account.getStreaks());
        existingAccount.setAvatarUrl(account.getAvatarUrl());
        existingAccount.setPhoneNumber(account.getPhoneNumber());
        existingAccount.setPublicProfile(account.isPublicProfile());
        existingAccount.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(existingAccount);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAccount() {
        boolean removed = accounts.removeIf(account -> account.getId().equals(UUID.fromString("0000000-0000-0000-0000-000000000000")));
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/subscription")
    public ResponseEntity<AccountDTO> addSubscription(@RequestBody SubscriptionDTO subscription) {
        AccountDTO account = accounts.stream()
                .filter(a -> a.getId().equals(UUID.fromString("0000000-0000-0000-0000-000000000000")))
                .findFirst()
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        account.setSubscription(subscription);
        account.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/subscription")
    public ResponseEntity<Void> removeSubscription() {
        AccountDTO account = accounts.stream()
                .filter(a -> a.getId().equals(UUID.fromString("0000000-0000-0000-0000-000000000000")))
                .findFirst()
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        account.setSubscription(null);
        account.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.noContent().build();
    }
}