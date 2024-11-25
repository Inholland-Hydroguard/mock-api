package hydroguard.api.mock.controllers.tasks;

import hydroguard.api.mock.models.task.TaskDTO;
import hydroguard.api.mock.models.task.TasksDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<TaskDTO> tasks = new ArrayList<>();

    @GetMapping
    public TasksDTO getAllTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID plantId) {

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

        int total = filteredTasks.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<TaskDTO> paginatedTasks = filteredTasks.subList(start, end);

        return new TasksDTO(paginatedTasks, total, page);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task) {
        task.setId(UUID.randomUUID());
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