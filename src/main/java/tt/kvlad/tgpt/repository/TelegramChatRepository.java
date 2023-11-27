package tt.kvlad.tgpt.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tt.kvlad.tgpt.model.TelegramChat;

public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long> {
    @Query("""
            FROM TelegramChat tch
            LEFT JOIN FETCH tch.targetContext
            WHERE tch.chatId = :chatId
            """)
    Optional<TelegramChat> getByChatIdWithTargetContext(Long chatId);

    @Query("""
            FROM TelegramChat tch
            JOIN FETCH tch.contexts
            WHERE tch.chatId = :chatId
            """)
    Optional<TelegramChat> getByChatIdWithAllContexts(Long chatId);

    @Query("""
            FROM TelegramChat tch
            JOIN FETCH tch.contexts
            """)
    List<TelegramChat> getAllWithAllContexts(Pageable pageable);

    Optional<TelegramChat> findByChatId(Long chatId);

    @Query("""
            FROM TelegramChat tch
            JOIN FETCH tch.contexts
            WHERE tch.id = :id
            """)
    Optional<TelegramChat> getByIdWithAllContexts(Long id);
}
