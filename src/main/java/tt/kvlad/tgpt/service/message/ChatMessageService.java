package tt.kvlad.tgpt.service.message;

import java.util.List;
import org.springframework.data.domain.Pageable;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.model.ChatMessage;

public interface ChatMessageService {
    List<MessageDto> getAllByContextId(Long id, Pageable pageable);

    List<MessageDto> getAllByChatId(Long id, Pageable pageable);

    ChatMessage save(ChatMessage messages);

    List<MessageDto> findLatestByContextId(Long id, Pageable pageable);

    List<MessageDto> findLatestByContextIdAsc(Long id, Pageable pageable);

    ChatMessage createNewUserContextMessage(Long id, String text);

    ChatMessage createNewUserMessage(Long id, String text);
}
