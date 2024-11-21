package hydroguard.api.mock.controllers.community;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hydroguard.api.mock.models.community.GroupDTO;
import hydroguard.api.mock.models.community.GroupsDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/community/groups")
public class GroupController {
    private final List<GroupDTO> Groups = new ArrayList<>();

    @PostConstruct
    public void init() {
        // String groupName, String description, LocalDateTime createdAt, LocalDateTime updatedAt
        Groups.add(new GroupDTO("PlantLovers", "A group for people who love plants", getRandomDate(), getRandomDate()));
        Groups.add(new GroupDTO("General Care", "A group for people who want to know more / ask questions about general information", getRandomDate(), getRandomDate()));
        Groups.add(new GroupDTO("CactiLovers", "A group for people who love cacti", getRandomDate(), getRandomDate()));
    }

    private LocalDateTime getRandomDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(lastWeek.toEpochSecond(ZoneOffset.UTC), now.toEpochSecond(ZoneOffset.UTC));
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }

    @PostMapping
    public GroupDTO createGroup(@RequestBody GroupDTO groupDTO) {
        groupDTO.setId(UUID.randomUUID());
        Groups.add(groupDTO);
        return groupDTO;
    }

    @GetMapping
    public GroupsDTO getAllGroups(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(required = false) String groupName,
        @RequestParam(required = false) LocalDateTime createdFrom,
        @RequestParam(required = false) LocalDateTime createdTo,
        @RequestParam(required = false) LocalDateTime updatedFrom,
        @RequestParam(required = false) LocalDateTime updatedTo,
        @RequestParam(required = false) String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection) {
        
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }

        List<GroupDTO> filteredGroups = Groups.stream()
            .filter(group -> groupName == null || group.getGroupName().contains(groupName))
            .filter(group -> createdFrom == null || group.getCreatedAt().isAfter(createdFrom))
            .filter(group -> createdTo == null || group.getCreatedAt().isBefore(createdTo))
            .filter(group -> updatedFrom == null || group.getUpdatedAt().isAfter(updatedFrom))
            .filter(group -> updatedTo == null || group.getUpdatedAt().isBefore(updatedTo))
            .collect(Collectors.toList());
        
        Comparator<GroupDTO> comparator = Comparator.comparing(GroupDTO::getGroupName);
        if (sortField != null) {
            switch (sortField) {
                case "createdAt":
                    comparator = Comparator.comparing(GroupDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(GroupDTO::getUpdatedAt);
                    break;
            }
        }

        if (sortDirection.equals("desc")) {
            comparator = comparator.reversed();
        }

        List<GroupDTO> sortedGroups = filteredGroups.stream()
            .sorted(comparator)
            .collect(Collectors.toList());

        int total = sortedGroups.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        if (fromIndex >= total) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }

        return new GroupsDTO(sortedGroups.subList(fromIndex, toIndex), total, page);
    }

    @GetMapping("/{id}")
    public GroupDTO getGroupById(@PathVariable UUID id) {
        return Groups.stream()
            .filter(group -> group.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
    }

    @PutMapping("/{id}")
    public GroupDTO updateGroup(@PathVariable UUID id, @RequestBody GroupDTO groupDTO) {
        for (GroupDTO group : Groups) {
            if (group.getId().equals(id)) {
                group.setGroupName(groupDTO.getGroupName());
                group.setDescription(groupDTO.getDescription());
                group.setGroupOwner(groupDTO.getGroupOwner());
                group.setPosts(groupDTO.getPosts());
                group.setCreatedAt(groupDTO.getCreatedAt());
                group.setUpdatedAt(groupDTO.getUpdatedAt());
                return group;
            }
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable UUID id) {
        Groups.removeIf(group -> group.getId().equals(id));
    }
}
