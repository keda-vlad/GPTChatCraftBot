package tt.kvlad.tgpt.service.chat.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tt.kvlad.tgpt.dto.chat.ChatDto;
import tt.kvlad.tgpt.exception.EntityNotFoundException;
import tt.kvlad.tgpt.mapper.ChatMapper;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.repository.TelegramChatRepository;
import tt.kvlad.tgpt.service.chat.TelegramChatService;

@Service
@RequiredArgsConstructor
public class TelegramChatServiceImpl implements TelegramChatService {
    private final TelegramChatRepository telegramChatRepository;
    private final ChatMapper chatMapper;

    @Override
    public TelegramChat getByChatIdWithTargetContext(Long chatId) {
        return telegramChatRepository.getByChatIdWithTargetContext(chatId).orElseThrow(
                        () -> new EntityNotFoundException("Can't find chat by chatId = " + chatId)
                );
    }

    @Override
    public TelegramChat getByChatIdWithAllContext(Long chatId) {
        return telegramChatRepository.getByChatIdWithAllContexts(chatId).orElseThrow(
                        () -> new EntityNotFoundException("Can't find chat by chatId = " + chatId)
                );
    }

    @Override
    public List<ChatDto> getAll(Pageable pageable) {
        return telegramChatRepository.getAllWithAllContexts(pageable).stream()
                .map(chatMapper::toDto)
                .toList();
    }

    @Override
    public ChatDto getById(Long id) {
        TelegramChat chat = telegramChatRepository.getByIdWithAllContexts(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id: " + id)
        );
        return chatMapper.toDto(chat);
    }

    @Override
    public boolean isPresentInDataBase(Long chatId) {
        return telegramChatRepository.findByChatId(chatId).isPresent();
    }

    @Override
    public TelegramChat createByChatId(Long chatId) {
        TelegramChat telegramChat = new TelegramChat().setChatId(chatId);
        return telegramChatRepository.save(telegramChat);
    }

    @Override
    public void save(TelegramChat telegramChat) {
        telegramChatRepository.save(telegramChat);
    }

    @Override
    public TelegramChat getByChatId(Long chatId) {
        return telegramChatRepository.findByChatId(chatId).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by chatId: " + chatId)
        );
    }
}
