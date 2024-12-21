package hydroguard.api.mock.controllers.reoccurringtask;


import hydroguard.api.mock.models.reocurringTask.ReoccurringTaskDTO;
import hydroguard.api.mock.models.reocurringTask.ReoccurringTasksDTO;
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
@RequestMapping("api/reoccurring-tasks")
public class ReoccurringTaskController {

    private final List<ReoccurringTaskDTO> reoccurringTasks = new ArrayList<>();

    @PostConstruct
    public void init() {
        reoccurringTasks.add(new ReoccurringTaskDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Task 1", false, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now(), 3600));
        reoccurringTasks.add(new ReoccurringTaskDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Task 2", false, LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now(), 7200));
        reoccurringTasks.add(new ReoccurringTaskDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Task 3", false, LocalDateTime.now().plusDays(3), LocalDateTime.now(), LocalDateTime.now(), 10800));
    }

    @GetMapping
    public ReoccurringTasksDTO getAllReoccurringTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) UUID plantId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean isCompleted,
            @RequestParam(required = false) LocalDateTime dueFrom,
            @RequestParam(required = false) LocalDateTime dueTo,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            @RequestParam(required = false) LocalDateTime updatedFrom,
            @RequestParam(required = false) LocalDateTime updatedTo,
            @RequestParam(required = false) Integer intervalSecondsFrom,
            @RequestParam(required = false) Integer intervalSecondsTo,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }

        List<ReoccurringTaskDTO> filteredTasks = reoccurringTasks;

        if (plantId != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.getPlantId().equals(plantId))
                    .collect(Collectors.toList());
        }

        if (description != null && !description.isEmpty()) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.getDescription().contains(description))
                    .collect(Collectors.toList());
        }

        if (isCompleted != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.isCompleted() == isCompleted)
                    .collect(Collectors.toList());
        }

        if (dueFrom != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> !task.getDueAt().isBefore(dueFrom))
                    .collect(Collectors.toList());
        }

        if (dueTo != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> !task.getDueAt().isAfter(dueTo))
                    .collect(Collectors.toList());
        }

        if (createdFrom != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> !task.getCreatedAt().isBefore(createdFrom))
                    .collect(Collectors.toList());
        }

        if (createdTo != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> !task.getCreatedAt().isAfter(createdTo))
                    .collect(Collectors.toList());
        }

        if (updatedFrom != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> !task.getUpdatedAt().isBefore(updatedFrom))
                    .collect(Collectors.toList());
        }

        if (updatedTo != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> !task.getUpdatedAt().isAfter(updatedTo))
                    .collect(Collectors.toList());
        }

        if (intervalSecondsFrom != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.getIntervalSeconds() >= intervalSecondsFrom)
                    .collect(Collectors.toList());
        }

        if (intervalSecondsTo != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.getIntervalSeconds() <= intervalSecondsTo)
                    .collect(Collectors.toList());
        }

        if (sortField != null) {
            Comparator<ReoccurringTaskDTO> comparator;
            switch (sortField) {
                case "plantId":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::getPlantId);
                    break;
                case "description":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::getDescription);
                    break;
                case "isCompleted":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::isCompleted);
                    break;
                case "dueAt":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::getDueAt);
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::getUpdatedAt);
                    break;
                case "intervalSeconds":
                    comparator = Comparator.comparing(ReoccurringTaskDTO::getIntervalSeconds);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort field");
            }

            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }

            filteredTasks.sort(comparator);
        }

        int total = filteredTasks.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<ReoccurringTaskDTO> paginatedTasks = filteredTasks.subList(start, end);

        return new ReoccurringTasksDTO(paginatedTasks, total, page);
    }

    @PostMapping
    public ResponseEntity<ReoccurringTaskDTO> createReoccurringTask(@RequestBody ReoccurringTaskDTO task) {
        task.setId(UUID.randomUUID());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        reoccurringTasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ReoccurringTaskDTO> getReoccurringTaskById(@PathVariable UUID taskId) {
        ReoccurringTaskDTO task = reoccurringTasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ReoccurringTaskDTO> updateReoccurringTask(@PathVariable UUID taskId, @RequestBody ReoccurringTaskDTO task) {
        ReoccurringTaskDTO existingTask = reoccurringTasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.isCompleted());
        existingTask.setDueAt(task.getDueAt());
        existingTask.setUpdatedAt(LocalDateTime.now());
        existingTask.setUserId(task.getUserId());
        existingTask.setPlantId(task.getPlantId());
        existingTask.setIntervalSeconds(task.getIntervalSeconds());
        return ResponseEntity.ok(existingTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteReoccurringTask(@PathVariable UUID taskId) {
        boolean removed = reoccurringTasks.removeIf(task -> task.getId().equals(taskId));
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}