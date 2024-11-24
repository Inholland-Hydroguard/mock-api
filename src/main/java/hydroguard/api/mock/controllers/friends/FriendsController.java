package hydroguard.api.mock.controllers.friends;

import hydroguard.api.mock.models.friends.AddFriendDTO;
import hydroguard.api.mock.models.friends.Friends;
import hydroguard.api.mock.models.friends.UpdateFriendStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    private final List<Friends> friendsList = new ArrayList<>();

    @PostMapping("/invite")
    public ResponseEntity<String> inviteFriend(@RequestBody AddFriendDTO dto) {
        Friends newFriendship = new Friends(dto.getUserId1(), dto.getUserId2());
        friendsList.add(newFriendship);

        return ResponseEntity.ok("Friend invitation sent from " + dto.getUserId1() + " to " + dto.getUserId2());
    }

    @PostMapping("/invite/accept")
    public ResponseEntity<String> acceptInvitation(@RequestBody AddFriendDTO dto) {
        for (Friends friendship : friendsList) {
            if (friendship.getUserId1().equals(UUID.fromString(dto.getUserId1())) &&
                    friendship.getUserId2().equals(UUID.fromString(dto.getUserId2())) &&
                    friendship.getStatus() == Friends.Status.PENDING) {

                friendship.setStatus(Friends.Status.ACCEPTED);
                return ResponseEntity.ok("Friendship accepted between " + dto.getUserId1() + " and " + dto.getUserId2());
            }
            if (friendship.getUserId2().equals(UUID.fromString(dto.getUserId1())) &&
                    friendship.getUserId1().equals(UUID.fromString(dto.getUserId2())) &&
                    friendship.getStatus() == Friends.Status.PENDING) {

                friendship.setStatus(Friends.Status.ACCEPTED);
                return ResponseEntity.ok("Friendship accepted between " + dto.getUserId1() + " and " + dto.getUserId2());
            }
        }
        return ResponseEntity.status(404).body("Friend invitation not found.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFriendshipStatus(@PathVariable UUID id, @RequestBody UpdateFriendStatusDTO dto) {
        if (dto.getUserId1().equals(id.toString()) || dto.getUserId2().equals(id.toString())) {
            for (Friends friendship : friendsList) {
                if ((friendship.getUserId1().toString().equals(dto.getUserId1()) && friendship.getUserId2().toString().equals(dto.getUserId2())) ||
                        (friendship.getUserId2().toString().equals(dto.getUserId1()) && friendship.getUserId1().toString().equals(dto.getUserId2()))) {
                    friendship.setStatus(dto.getStatus());
                    return ResponseEntity.ok("Friendship status updated to " + dto.getStatus());
                }
            }
            return ResponseEntity.status(404).body("Friendship not found.");
        }
        return ResponseEntity.status(400).body("The ID supplied in the path is not in the DTO");
    }

    @GetMapping
    public ResponseEntity<List<Friends>> getAllFriends(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Friends> paginatedList = paginateList(friendsList, page, size);
        return ResponseEntity.ok(paginatedList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Friends>> getFriendsByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Friends> userFriends = friendsList.stream()
                .filter(f -> f.getUserId1().equals(userId) || f.getUserId2().equals(userId))
                .collect(Collectors.toList());

        List<Friends> paginatedList = paginateList(userFriends, page, size);
        return ResponseEntity.ok(paginatedList);
    }

    // Utility method for pagination
    private List<Friends> paginateList(List<Friends> list, int page, int size) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, list.size());

        if (fromIndex > list.size()) {
            return new ArrayList<>();
        }
        return list.subList(fromIndex, toIndex);
    }
}
