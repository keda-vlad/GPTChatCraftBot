package tt.kvlad.tgpt.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tt.kvlad.tgpt.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("""
              FROM ChatMessage cm
              WHERE cm.context.id = :id
              ORDER BY cm.creationTime DESC
              """)
    List<ChatMessage> findLatestByContextId(Long id, Pageable pageable);

    List<ChatMessage> getAllByContextId(Long id, Pageable pageable);

    @Query("FROM ChatMessage cm WHERE cm.telegramChat.id = :id")
    List<ChatMessage> getAllByChatId(Long id, Pageable pageable);
}
