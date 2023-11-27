package tt.kvlad.tgpt.service.context.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.mapper.ContextMapper;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.repository.ContextRepository;
import tt.kvlad.tgpt.service.context.ContextService;

@Service
@RequiredArgsConstructor
public class ContextServiceImpl implements ContextService {
    private final ContextRepository contextRepository;
    private final ContextMapper contextMapper;

    @Override
    public List<ContextDtoWithoutMessages> getAllByChatId(Long chatId) {
        return contextRepository.getAllByChatId(chatId).stream()
                .map(contextMapper::toDtoWithoutMessages)
                .toList();
    }

    @Override
    public List<ContextDtoWithoutMessages> getAll(Pageable pageable) {
        return contextRepository.findAll(pageable).toList().stream()
                .map(contextMapper::toDtoWithoutMessages)
                .toList();
    }

    @Override
    public ContextDtoWithoutMessages getById(Long id) {
        Context context = contextRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find context by id = " + id)
        );
        return contextMapper.toDtoWithoutMessages(context);
    }

    @Override
    public Context createContext(
            TelegramChat telegramChat,
            String contextName,
            Context.ContextType contextType
    ) {
        Context context = new Context()
                .setName(contextName)
                .setTelegramChat(telegramChat)
                .setContextType(contextType);
        return contextRepository.save(context);
    }

    @Override
    public Context getByChatIdAndName(Long chatId, String contextName) {
        return contextRepository.findByChatIdAndName(chatId, contextName).orElseThrow(
                () -> new EntityNotFoundException("Can't find context by chatid = " + chatId
                        + "and context name = " + contextName)
        );
    }

    @Override
    public void deleteByChatIdAndName(Long chatId, String contextName) {
        contextRepository.delete(getByChatIdAndName(chatId, contextName));
    }
}
