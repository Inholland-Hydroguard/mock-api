package hydroguard.api.mock.controllers.community;

import hydroguard.api.mock.models.community.CommentDTO;
import hydroguard.api.mock.models.community.CommentsDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/community/comments")
public class CommentController {

    private final List<CommentDTO> comments = new ArrayList<>();

    @PostConstruct
    public void init() {
        comments.add(new CommentDTO("PlantLover69", "How amazing that you did that!", 1, 0, getRandomDate()));
        comments.add(new CommentDTO("ILikePlants", "Wow, did you also know you can cut the big leaves so new leaves will grow.", 123, 55, getRandomDate()));
        comments.add(new CommentDTO("OrchidsWin", "I like orchids better", 23, 154, getRandomDate()));
    }

    private LocalDateTime getRandomDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(lastWeek.toEpochSecond(ZoneOffset.UTC), now.toEpochSecond(ZoneOffset.UTC));
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }

    @PostMapping
    public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
        commentDTO.setId(UUID.randomUUID());
        comments.add(commentDTO);
        return commentDTO;
    }

    @GetMapping
    public CommentsDTO getAllComments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) UUID parentId,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }

        List<CommentDTO> filteredComments = comments.stream()
                .filter(comment -> userName == null || comment.getUserName().equals(userName))
                .filter(comment -> parentId == null || comment.getParentId().equals(parentId))
                .collect(Collectors.toList());

        Comparator<CommentDTO> comparator = Comparator.comparing(CommentDTO::getCreatedAt);
        if (sortField != null) {
            switch (sortField) {
                case "userName":
                    comparator = Comparator.comparing(CommentDTO::getUserName);
                    break;
                case "likes":
                    comparator = Comparator.comparingInt(CommentDTO::getLikes);
                    break;
                case "dislikes":
                    comparator = Comparator.comparingInt(CommentDTO::getDislikes);
                    break;
            }
        }

        if (sortDirection.equals("desc")) {
            comparator = comparator.reversed();
        }

        List<CommentDTO> sortedComments = filteredComments.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        int total = sortedComments.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        

        if (fromIndex >= total) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }

        return new CommentsDTO(sortedComments.subList(fromIndex, toIndex), total, page);
    }
    
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        comments.removeIf(comment -> comment.getId().equals(id));
    }
}