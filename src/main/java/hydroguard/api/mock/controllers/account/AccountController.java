package hydroguard.api.mock.controllers.account;

import hydroguard.api.mock.models.account.AccountDTO;
import hydroguard.api.mock.models.account.AccountsDTO;
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
@RequestMapping("/accounts")
public class AccountController {

    private final List<AccountDTO> accounts = new ArrayList<>();

    @GetMapping
    public AccountsDTO getAllAccounts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
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

        if (name != null && !name.isEmpty()) {
            filteredAccounts = filteredAccounts.stream()
                    .filter(account -> account.getName().contains(name))
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

        List<AccountDTO> paginatedAccounts = filteredAccounts.subList(start, end);

        return new AccountsDTO(paginatedAccounts, total, page);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO account) {
        account.setId(UUID.randomUUID());
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        accounts.add(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID accountId) {
        AccountDTO account = accounts.stream()
                .filter(a -> a.getId().equals(accountId))
                .findFirst()
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable UUID accountId, @RequestBody AccountDTO account) {
        AccountDTO existingAccount = accounts.stream()
                .filter(a -> a.getId().equals(accountId))
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

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        boolean removed = accounts.removeIf(account -> account.getId().equals(accountId));
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}