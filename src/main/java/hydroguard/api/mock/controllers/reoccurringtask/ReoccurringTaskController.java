package hydroguard.api.mock.controllers.reoccurringtask;


import hydroguard.api.mock.models.reocurringTask.ReoccurringTaskDTO;
import hydroguard.api.mock.models.reocurringTask.ReoccurringTasksDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reoccurring-tasks")
public class ReoccurringTaskController {

    private final List<ReoccurringTaskDTO> reoccurringTasks = new ArrayList<>();

    @GetMapping
    public ReoccurringTasksDTO getAllReoccurringTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID plantId) {

        List<ReoccurringTaskDTO> filteredTasks = reoccurringTasks;

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