package tt.kvlad.tgpt.service.message.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.mapper.MessageMapper;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.repository.ChatMessageRepository;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final TelegramChatService telegramChatService;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageDto> getAllByContextId(Long id, Pageable pageable) {
        return chatMessageRepository.getAllByContextId(id, pageable).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
    public List<MessageDto> getAllByChatId(Long id, Pageable pageable) {
        return chatMessageRepository.getAllByChatId(id, pageable).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
    public ChatMessage save(ChatMessage messages) {
        return chatMessageRepository.save(messages);
    }

    @Override
    public List<MessageDto> findLatestByContextId(Long id, Pageable pageable) {
        return chatMessageRepository.findByContextIdOrderByCreationTimeDesc(id, pageable).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
    public List<MessageDto> findLatestByContextIdAsc(Long id, Pageable pageable) {
        return findLatestByContextId(id, pageable).stream()
                .sorted(Comparator.comparing(MessageDto::creationTime))
                .toList();
    }

    @Override
    public ChatMessage createNewUserContextMessage(Long chatId, String text) {
        TelegramChat chat = telegramChatService.getByChatIdWithTargetContext(chatId);
        ChatMessage chatMessage = new ChatMessage()
                .setTelegramChat(chat)
                .setContext(chat.getTargetContext())
                .setCreationTime(LocalDateTime.now())
                .setText(text)
                .setSignature(ChatMessage.SignatureType.USER.signature());
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public ChatMessage createNewUserMessage(Long chatId, String text) {
        TelegramChat chat = telegramChatService.getByChatIdWithTargetContext(chatId);
        ChatMessage chatMessage = new ChatMessage()
                .setTelegramChat(chat)
                .setCreationTime(LocalDateTime.now())
                .setText(text)
                .setSignature(ChatMessage.SignatureType.USER.signature());
        return chatMessageRepository.save(chatMessage);
    }
}

