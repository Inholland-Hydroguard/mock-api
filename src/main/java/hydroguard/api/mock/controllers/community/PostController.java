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

import hydroguard.api.mock.models.community.PostDTO;
import hydroguard.api.mock.models.community.PostsDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/api/community/posts")
public class PostController {
    private final List<PostDTO> posts = new ArrayList<>();

    @PostConstruct
    public void init() {
        //String title, String content, String groupName, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt
        posts.add(new PostDTO("OMG I love this plant", "Look at this cool plant i came across.", "PlantLovers", "https://media-cache-ak0.pinimg.com/736x/a8/d7/06/a8d706c596f31c0c5d2776c634abba67.jpg", getRandomDate(), getRandomDate()));
        posts.add(new PostDTO("Water your plants!!!", "Don't forget to water your plants", "General Care", "https://media-cache-ak0.pinimg.com/736x/a8/d7/06/a8d706c596f31c0c5d2776c634abba67.jpg", getRandomDate(), getRandomDate()));
        posts.add(new PostDTO("Don't forget to turn your pots", "Turning your pots can help keep your plants healthy.", "General Care", "https://media-cache-ak0.pinimg.com/736x/a8/d7/06/a8d706c596f31c0c5d2776c634abba67.jpg", getRandomDate(), getRandomDate()));
        posts.add(new PostDTO("Water Cactus", "Look at this cactus that loves to drink water.", "CactiLovers", "https://media-cache-ak0.pinimg.com/736x/a8/d7/06/a8d706c596f31c0c5d2776c634abba67.jpg", getRandomDate(), getRandomDate()));
    }

    private LocalDateTime getRandomDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(lastWeek.toEpochSecond(ZoneOffset.UTC), now.toEpochSecond(ZoneOffset.UTC));
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        postDTO.setId(UUID.randomUUID());
        posts.add(postDTO);
        return postDTO;
    }

    @GetMapping
    public PostsDTO getAllPosts(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) UUID userId,
        @RequestParam(required = false) String content,
        @RequestParam(required = false) UUID groupId,
        @RequestParam(required = false) LocalDateTime fromDate,
        @RequestParam(required = false) LocalDateTime toDate,
        @RequestParam(required = false) String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection) {
        
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }

        List<PostDTO> filteredPosts = posts.stream()
            .filter(post -> title == null || post.getTitle().contains(title))
            .filter(post -> userId == null || post.getUserId().equals(userId))
            .filter(post -> content == null || post.getContent().contains(content))
            .filter(post -> groupId == null || post.getGroupId().equals(groupId))
            .filter(post -> fromDate == null || post.getCreatedAt().isAfter(fromDate))
            .filter(post -> toDate == null || post.getCreatedAt().isBefore(toDate))
            .collect(Collectors.toList());

        Comparator<PostDTO> comparator = Comparator.comparing(PostDTO::getTitle);
        if (sortField != null) {
            switch (sortField) {
                case "createdAt":
                    comparator = Comparator.comparing(PostDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(PostDTO::getUpdatedAt);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort field: " + sortField);
            }
        }

        if (sortDirection.equals("desc")) {
            comparator = comparator.reversed();
        }

        List<PostDTO> sortedPosts = filteredPosts.stream()
            .sorted(comparator)
            .collect(Collectors.toList());

        int total = sortedPosts.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        if (fromIndex >= total) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }

        return new PostsDTO(sortedPosts.subList(fromIndex, toIndex), total, page);
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable UUID id) {
        return posts.stream()
            .filter(post -> post.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable UUID id, @RequestBody PostDTO postDTO) {
        for (PostDTO post : posts) {
            if (post.getId().equals(id)) {
                post.setTitle(postDTO.getTitle());
                post.setContent(postDTO.getContent());
                post.setCreatedAt(postDTO.getCreatedAt());
                post.setUpdatedAt(postDTO.getUpdatedAt());
                return post;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}

