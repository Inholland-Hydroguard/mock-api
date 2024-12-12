package hydroguard.api.mock.controllers.tasks;

import hydroguard.api.mock.models.task.TaskDTO;
import hydroguard.api.mock.models.task.TasksDTO;
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
@RequestMapping("api/tasks")
public class TaskController {

    private final List<TaskDTO> tasks = new ArrayList<>();

    @PostConstruct
    public void init() {
        tasks.add(new TaskDTO(UUID.randomUUID(), UUID.fromString("0000000-0000-0000-0000-000000000000"), UUID.randomUUID(), "Task 1", false, LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now()));
        tasks.add(new TaskDTO(UUID.randomUUID(), UUID.fromString("0000000-0000-0000-0000-000000000000"), UUID.randomUUID(), "Task 2", false, LocalDateTime.now().plusDays(2), LocalDateTime.now(), LocalDateTime.now()));
        tasks.add(new TaskDTO(UUID.randomUUID(), UUID.fromString("0000000-0000-0000-0000-000000000000"), UUID.randomUUID(), "Task 3", false, LocalDateTime.now().plusDays(3), LocalDateTime.now(), LocalDateTime.now()));
    }

    @GetMapping
    public TasksDTO getAllTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID plantId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean isCompleted,
            @RequestParam(required = false) LocalDateTime dueFrom,
            @RequestParam(required = false) LocalDateTime dueTo,
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

        List<TaskDTO> filteredTasks = tasks;

        if (userId != null) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.getUserId().equals(userId))
                    .collect(Collectors.toList());
        }

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

        if (sortField != null) {
            Comparator<TaskDTO> comparator;
            switch (sortField) {
                case "userId":
                    comparator = Comparator.comparing(TaskDTO::getUserId);
                    break;
                case "plantId":
                    comparator = Comparator.comparing(TaskDTO::getPlantId);
                    break;
                case "description":
                    comparator = Comparator.comparing(TaskDTO::getDescription);
                    break;
                case "isCompleted":
                    comparator = Comparator.comparing(TaskDTO::isCompleted);
                    break;
                case "dueAt":
                    comparator = Comparator.comparing(TaskDTO::getDueAt);
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(TaskDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(TaskDTO::getUpdatedAt);
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

        List<TaskDTO> paginatedTasks = filteredTasks.subList(start, end);

        return new TasksDTO(paginatedTasks, total, page);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task) {
        task.setId(UUID.randomUUID());
        task.setUserId(UUID.fromString("0000000-0000-0000-0000-000000000000"));
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable UUID taskId) {
        TaskDTO task = tasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable UUID taskId, @RequestBody TaskDTO task) {
        TaskDTO existingTask = tasks.stream()
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
        return ResponseEntity.ok(existingTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(taskId));
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}