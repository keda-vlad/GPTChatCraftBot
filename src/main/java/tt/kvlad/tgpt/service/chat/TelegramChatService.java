package tt.kvlad.tgpt.service.chat;

import java.util.List;
import org.springframework.data.domain.Pageable;
import tt.kvlad.tgpt.dto.chat.ChatDto;
import tt.kvlad.tgpt.model.TelegramChat;

public interface TelegramChatService {
    TelegramChat getByChatIdWithTargetContext(Long chatId);

    TelegramChat getByChatIdWithAllContext(Long chatId);

    List<ChatDto> getAll(Pageable pageable);

    ChatDto getById(Long id);

    boolean isPresentInDataBase(Long chatId);

    TelegramChat createByChatId(Long chatId);

    void save(TelegramChat telegramChat);

    TelegramChat getByChatId(Long chatId);
}
