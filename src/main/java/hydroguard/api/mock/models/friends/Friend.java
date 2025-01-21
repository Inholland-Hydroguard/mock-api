package hydroguard.api.mock.models.friends;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Friend {
    private UUID userId1;
    private UUID userId2;
    private Status status;

    public Friend(String userId1, String userId2, Status status) {
        this.userId1 = UUID.fromString(userId1);
        this.userId2 = UUID.fromString(userId2);
        this.status = status;
    }

    public Friend(String userId1, String userId2) {
        this.userId1 = UUID.fromString(userId1);
        this.userId2 = UUID.fromString(userId2);
        this.status = Status.PENDING;
    }

    public enum Status {
        PENDING,
        ACCEPTED,
        BLOCKED,
        DENIED,
        REMOVED;

        public static Status findByName(String name) {
            for (Status status : values()) {
                if (status.name().equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return null;
        }

        public static String toString(Status status) {
            return status == null ? null : status.name();
        }
    }
}
