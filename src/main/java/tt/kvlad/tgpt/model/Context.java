package tt.kvlad.tgpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "contexts")
@Accessors(chain = true)
@SQLDelete(sql = "UPDATE contexts SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Data
public class Context {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContextType contextType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "telegram_chat_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TelegramChat telegramChat;
    @OneToMany(
            mappedBy = "context",
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ChatMessage> messages;
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    public enum ContextType {
        DEFAULT, GPT, ADMIN
    }
}
