package tt.kvlad.tgpt.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tt.kvlad.tgpt.model.Context;

public interface ContextRepository extends JpaRepository<Context, Long> {
    @Query("FROM Context c WHERE c.telegramChat.chatId = :chatId")
    List<Context> getAllByChatId(Long chatId);

    @Query("""
              FROM Context c
              JOIN FETCH c.telegramChat tch
              WHERE tch.chatId = :chatId
              AND c.name = :contextName
              """)
    Optional<Context> findByChatIdAndName(Long chatId, String contextName);
}
