package tt.kvlad.tgpt.service.context;

import java.util.List;
import org.springframework.data.domain.Pageable;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.model.TelegramChat;

public interface ContextService {
    List<ContextDtoWithoutMessages> getAllByChatId(Long id);

    List<ContextDtoWithoutMessages> getAll(Pageable pageable);

    ContextDtoWithoutMessages getById(Long id);

    Context createContext(
            TelegramChat telegramChat,
            String contextName,
            Context.ContextType contextType
    );

    Context getByChatIdAndName(Long chatId, String contextName);

    void deleteByChatIdAndName(Long chatId, String text);
}
