package tt.kvlad.tgpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "telegram_chats")
@Accessors(chain = true)
@SQLDelete(sql = "UPDATE telegram_chats SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Data
public class TelegramChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long chatId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_context_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Context targetContext;
    @OneToMany(
            mappedBy = "telegramChat",
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Context> contexts = new ArrayList<>();
    @Column(nullable = false)
    private boolean isDeleted = false;
}
