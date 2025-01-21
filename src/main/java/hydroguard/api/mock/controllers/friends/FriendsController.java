package hydroguard.api.mock.controllers.friends;

import hydroguard.api.mock.models.friends.AddFriendDTO;
import hydroguard.api.mock.models.friends.Friend;
import hydroguard.api.mock.models.friends.FriendsDTO;
import hydroguard.api.mock.models.friends.UpdateFriendStatusDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
    Controller for the mock API. when
 */
@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    // List that means created objects persist throughout a running application's lifetime
    private final List<Friend> friendsList = new ArrayList<>();
    private final String mockUserId = "00000000-0000-0000-0000-000000000000";

    @PostConstruct
    public void init() {
        friendsList.add(new Friend(mockUserId, "934b03fa-d8f1-4d8b-8b03-fad8f17d8b0e", Friend.Status.ACCEPTED));
        friendsList.add(new Friend(mockUserId, "a22883c0-76ba-42db-a883-c076ba32db10", Friend.Status.PENDING));
        friendsList.add(new Friend("a22883c0-76ba-42db-a883-c076ba32db10", "934b03fa-d8f1-4d8b-8b03-fad8f17d8b0e", Friend.Status.DENIED));
        friendsList.add(new Friend("a22883c0-76ba-42db-a883-c076ba32db10", UUID.randomUUID().toString(), Friend.Status.ACCEPTED));
        friendsList.add(new Friend("a22883c0-76ba-42db-a883-c076ba32db10", UUID.randomUUID().toString(), Friend.Status.PENDING));
        friendsList.add(new Friend("a22883c0-76ba-42db-a883-c076ba32db10", UUID.randomUUID().toString(), Friend.Status.ACCEPTED));
        friendsList.add(new Friend(mockUserId, "dc2f92e9-5475-4f2a-af92-e95475df2ab5", Friend.Status.DENIED));
    }


    // Invite a friend
    @PostMapping("/invite")
    public ResponseEntity<String> inviteFriend(@RequestBody AddFriendDTO dto) {
        Friend newFriendship = new Friend(mockUserId, dto.getFriendId());
        friendsList.add(newFriendship);

        return ResponseEntity.ok("Friend invitation sent from " + mockUserId + " to " + dto.getFriendId());
    }

    // Accept a friend request
    @PostMapping("/invite/accept")
    public ResponseEntity<String> acceptInvitation(@RequestBody AddFriendDTO dto) {
        for (Friend friendship : friendsList) {
            if (friendship.getUserId1().equals(UUID.fromString(mockUserId)) &&
                    friendship.getUserId2().equals(UUID.fromString(dto.getFriendId())) &&
                    friendship.getStatus() == Friend.Status.PENDING) {

                friendship.setStatus(Friend.Status.ACCEPTED);
                return ResponseEntity.ok("Friendship accepted between " + mockUserId + " and " + dto.getFriendId());
            }
            if (friendship.getUserId2().equals(UUID.fromString(mockUserId)) &&
                    friendship.getUserId1().equals(UUID.fromString(dto.getFriendId())) &&
                    friendship.getStatus() == Friend.Status.PENDING) {

                friendship.setStatus(Friend.Status.ACCEPTED);
                return ResponseEntity.ok("Friendship accepted between " + mockUserId + " and " + dto.getFriendId());
            }
        }
        return ResponseEntity.status(404).body("No Pending invitation found.");
    }

    // Deny a friend request
    @PostMapping("/invite/deny")
    public ResponseEntity<String> denyInvitation(@RequestBody AddFriendDTO dto) {
        for (Friend friendship : friendsList) {
            if (friendship.getUserId1().equals(UUID.fromString(mockUserId)) &&
                    friendship.getUserId2().equals(UUID.fromString(dto.getFriendId())) &&
                    friendship.getStatus() == Friend.Status.PENDING) {

                friendship.setStatus(Friend.Status.DENIED);
                return ResponseEntity.ok("Friendship denied between " + mockUserId + " and " + dto.getFriendId());
            }
            if (friendship.getUserId2().equals(UUID.fromString(mockUserId)) &&
                    friendship.getUserId1().equals(UUID.fromString(dto.getFriendId())) &&
                    friendship.getStatus() == Friend.Status.PENDING) {

                friendship.setStatus(Friend.Status.DENIED);
                return ResponseEntity.ok("Friendship denied between " + mockUserId + " and " + dto.getFriendId());
            }
        }
        return ResponseEntity.status(404).body("Friend invitation not found.");
    }

    // Method for adjusting a friendship status, this method is used for blocking, unblocking and removing
    @PutMapping
    public ResponseEntity<String> updateFriendshipStatus(@RequestBody UpdateFriendStatusDTO dto) {
        for (Friend friendship : friendsList) {
            if (friendship.getUserId1().toString().equals(mockUserId) && friendship.getUserId2().toString().equals(dto.getFriendId())) {
                friendship.setStatus(dto.getStatus());
                return ResponseEntity.ok("Friendship status updated to " + dto.getStatus());
            } else if (friendship.getUserId2().toString().equals(mockUserId) && friendship.getUserId1().toString().equals(dto.getFriendId())) {
                friendship.setStatus(dto.getStatus());
                return ResponseEntity.ok("Friendship status updated to " + dto.getStatus());
            } 
        }
        return ResponseEntity.status(404).body("Friendship not found.");
    }

    // Get all friends of the user
    @GetMapping
    public ResponseEntity<FriendsDTO> getAllFriends(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        if (page < 1) {
            throw new IllegalArgumentException("Invalid page number");
        }

        // Pagination logic
        int total = friendsList.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        if (fromIndex > total) {
            throw new IllegalArgumentException("Invalid page number higher than total number of pages");
        }

        return ResponseEntity.ok(new FriendsDTO(friendsList.subList(fromIndex, toIndex), total, page));
    }

}
