package tt.kvlad.tgpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "chat_messages")
@Accessors(chain = true)
@SQLDelete(sql = "UPDATE chat_messages SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String signature;
    @Column(nullable = false)
    private LocalDateTime creationTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "context_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Context context;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TelegramChat telegramChat;
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    public ChatMessage buildAnswerMessage(String text, String signature) {
        return new ChatMessage()
                .setTelegramChat(telegramChat)
                .setContext(context)
                .setCreationTime(LocalDateTime.now())
                .setSignature(signature)
                .setText(text);
    }

    public enum SignatureType {
        ADMIN("admin", false),
        USER("user", true),
        BOT("bot", false),
        SYSTEM("system", true),
        ASSISTANT("assistant", true);

        private final String signature;
        private final boolean gptValid;

        SignatureType(String signature, boolean gptValid) {
            this.signature = signature;
            this.gptValid = gptValid;
        }

        public String signature() {
            return signature;
        }

        public static boolean isGptRole(String signature) {
            return Arrays.stream(SignatureType.values())
                    .anyMatch(r -> Objects.equals(r.signature, signature) && r.gptValid);
        }
    }
}
