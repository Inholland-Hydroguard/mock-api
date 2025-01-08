package hydroguard.api.mock.controllers.household;

import hydroguard.api.mock.models.household.*;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final List<HouseholdDTO> households = new ArrayList<>();

    @PostConstruct
    public void init() {
        HouseholdDTO household1 = new HouseholdDTO(UUID.randomUUID(), "Household 1", "City 1");
        household1.getMembers().add(new HouseholdUserDTO(UUID.fromString("0000000-0000-0000-0000-000000000000"), "Me", "avatar1.png", "Owner"));
        household1.getMembers().add(new HouseholdUserDTO(UUID.randomUUID(), "user2", "avatar2.png", "Member"));

        HouseholdDTO household2 = new HouseholdDTO(UUID.randomUUID(), "Household 2", "City 2");
        household2.getMembers().add(new HouseholdUserDTO(UUID.fromString("0000000-0000-0000-0000-000000000000"), "Me", "avatar3.png", "Member"));
        household2.getMembers().add(new HouseholdUserDTO(UUID.randomUUID(), "user4", "avatar4.png", "Owner"));

        households.add(household1);
        households.add(household2);
    }

    @PostMapping(consumes = "application/json")
    public HouseholdDTO createHousehold(@RequestBody AddHouseholdDTO addHouseholdDTO) {
        UUID id = UUID.randomUUID();
        HouseholdDTO householdDTO = new HouseholdDTO(id, addHouseholdDTO.getName(), addHouseholdDTO.getCity());
        householdDTO.setCreatedAt(LocalDateTime.now());
        householdDTO.setUpdatedAt(LocalDateTime.now());

        for (AddUserDTO addUserDTO : addHouseholdDTO.getMembers()) {
            HouseholdUserDTO userDTO = new HouseholdUserDTO(addUserDTO.getUserId(), addUserDTO.getUsername(), addUserDTO.getAvatarUrl(), "Owner");
            householdDTO.getMembers().add(userDTO);
        }

        households.add(householdDTO);
        return householdDTO;
    }

    @PostMapping("/invite/{id}")
    public void inviteUser(@PathVariable UUID id, @RequestBody AddUserDTO addUserDTO) {
        HouseholdDTO household = households.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Household not found"));

        HouseholdUserDTO userDTO = new HouseholdUserDTO(addUserDTO.getUserId(), addUserDTO.getUsername(), addUserDTO.getAvatarUrl(), "invited");
        household.getMembers().add(userDTO);
    }

    @PostMapping("/invite/accept/{id}")
    public void acceptInvite(@PathVariable UUID id, @RequestParam UUID userId) {
        HouseholdDTO household = households.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Household not found"));

        HouseholdUserDTO user = household.getMembers().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in household"));

        user.setRole("member");
    }

    @PostMapping("/invite/reject/{id}")
    public void rejectInvite(@PathVariable UUID id, @RequestParam UUID userId) {
        HouseholdDTO household = households.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Household not found"));

        household.getMembers().removeIf(u -> u.getUserId().equals(userId));
    }

    @GetMapping
    public HouseholdsDTO getAllHouseholds(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
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

        List<HouseholdDTO> filteredHouseholds = households;

        filteredHouseholds = filteredHouseholds.stream()
                .filter(household -> household.getMembers().stream().anyMatch(user -> user.getUserId().equals(UUID.fromString("0000000-0000-0000-0000-000000000000"))))
                .collect(Collectors.toList());

        if (name != null && !name.isEmpty()) {
            filteredHouseholds = filteredHouseholds.stream()
                    .filter(household -> household.getName().contains(name))
                    .collect(Collectors.toList());
        }

        if (city != null && !city.isEmpty()) {
            filteredHouseholds = filteredHouseholds.stream()
                    .filter(household -> household.getCity().contains(city))
                    .collect(Collectors.toList());
        }

        if (createdFrom != null) {
            filteredHouseholds = filteredHouseholds.stream()
                    .filter(household -> !household.getCreatedAt().isBefore(createdFrom))
                    .collect(Collectors.toList());
        }

        if (createdTo != null) {
            filteredHouseholds = filteredHouseholds.stream()
                    .filter(household -> !household.getCreatedAt().isAfter(createdTo))
                    .collect(Collectors.toList());
        }

        if (updatedFrom != null) {
            filteredHouseholds = filteredHouseholds.stream()
                    .filter(household -> !household.getUpdatedAt().isBefore(updatedFrom))
                    .collect(Collectors.toList());
        }

        if (updatedTo != null) {
            filteredHouseholds = filteredHouseholds.stream()
                    .filter(household -> !household.getUpdatedAt().isAfter(updatedTo))
                    .collect(Collectors.toList());
        }

        if (sortField != null) {
            Comparator<HouseholdDTO> comparator;
            switch (sortField) {
                case "name":
                    comparator = Comparator.comparing(HouseholdDTO::getName);
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(HouseholdDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(HouseholdDTO::getUpdatedAt);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort field");
            }

            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }

            filteredHouseholds.sort(comparator);
        }

        int total = filteredHouseholds.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<HouseholdDTO> paginatedHouseholds = filteredHouseholds.subList(start, end);

        return new HouseholdsDTO(paginatedHouseholds, total, page);
    }

    @GetMapping("/{id}")
    public HouseholdDTO getHouseholdById(@PathVariable UUID id) {
        return households.stream()
                .filter(household -> household.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Household not found"));
    }

    @PutMapping("/{id}")
    public HouseholdDTO updateHousehold(@PathVariable UUID id, @RequestBody HouseholdDTO updatedHousehold) {
        for (HouseholdDTO household : households) {
            if (household.getId().equals(id)) {
                household.setName(updatedHousehold.getName());
                household.setCity(updatedHousehold.getCity());
                household.setUpdatedAt(LocalDateTime.now());
                return household;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Household not found");
    }

    @DeleteMapping("/{id}")
    public void deleteHousehold(@PathVariable UUID id) {
        households.removeIf(household -> household.getId().equals(id));
    }

    @DeleteMapping("/{householdId}/members/{userId}")
    public void removeMember(@PathVariable UUID householdId, @PathVariable UUID userId) {
        HouseholdDTO household = households.stream()
                .filter(h -> h.getId().equals(householdId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Household not found"));

        if (household.getMembers().stream().noneMatch(u -> u.getUserId().equals(userId))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found in household");
        }

        if (household.getMembers().stream().anyMatch(u -> u.getUserId().equals(userId) && "Owner".equals(u.getRole()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot remove the owner of the household");
        }

        household.getMembers().removeIf(u -> u.getUserId().equals(userId));
    }
}