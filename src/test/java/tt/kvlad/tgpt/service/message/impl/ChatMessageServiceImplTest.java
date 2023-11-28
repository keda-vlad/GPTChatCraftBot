package tt.kvlad.tgpt.service.message.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.mapper.MessageMapper;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.repository.ChatMessageRepository;
import tt.kvlad.tgpt.service.chat.TelegramChatService;

@ExtendWith(MockitoExtension.class)
class ChatMessageServiceImplTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private TelegramChatService telegramChatService;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private ChatMessageServiceImpl chatMessageService;

    @Test
    void getAllByContextId_ShouldReturnMessageDtoList() {
        Long contextId = 1L;
        Pageable pageable = mock(Pageable.class);
        List<ChatMessage> chatMessages = Collections.singletonList(new ChatMessage());
        List<MessageDto> expectedMessageDtos = Collections.singletonList(
                new MessageDto(1L, "test", "user", LocalDateTime.MIN)
        );

        when(chatMessageRepository.getAllByContextId(contextId, pageable)).thenReturn(chatMessages);
        when(messageMapper.toDto(any(ChatMessage.class))).thenReturn(
                new MessageDto(1L, "test", "user", LocalDateTime.MIN)
        );

        List<MessageDto> actualMessageDtos = chatMessageService.getAllByContextId(
                contextId, pageable
        );

        assertEquals(expectedMessageDtos, actualMessageDtos);
        verify(chatMessageRepository, times(1))
                .getAllByContextId(contextId, pageable);
        verify(messageMapper, times(1)).toDto(any(ChatMessage.class));
    }

    @Test
    void getAllByChatId_ShouldReturnMessageDtoList() {
        Long chatId = 1L;
        Pageable pageable = mock(Pageable.class);
        List<ChatMessage> chatMessages = Collections.singletonList(new ChatMessage());
        List<MessageDto> expectedMessageDtos = Collections.singletonList(
                new MessageDto(1L, "test", "user", LocalDateTime.MIN)
        );

        when(chatMessageRepository.getAllByChatId(chatId, pageable)).thenReturn(chatMessages);
        when(messageMapper.toDto(any(ChatMessage.class))).thenReturn(
                new MessageDto(1L, "test", "user", LocalDateTime.MIN)
        );

        List<MessageDto> actualMessageDtos = chatMessageService.getAllByChatId(chatId, pageable);

        assertEquals(expectedMessageDtos, actualMessageDtos);
        verify(chatMessageRepository, times(1)).getAllByChatId(chatId, pageable);
        verify(messageMapper, times(1)).toDto(any(ChatMessage.class));
    }

    @Test
    void save_ShouldReturnSavedChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        when(chatMessageRepository.save(chatMessage)).thenReturn(chatMessage);

        ChatMessage savedChatMessage = chatMessageService.save(chatMessage);

        assertEquals(chatMessage, savedChatMessage);
        verify(chatMessageRepository, times(1)).save(chatMessage);
    }

    @Test
    void findLatestByContextIdAsc_ShouldReturnSortedMessageDtoList() {
        Long contextId = 1L;
        Pageable pageable = mock(Pageable.class);
        List<MessageDto> messageDtos = Collections.singletonList(
                new MessageDto(1L, "test", "user", LocalDateTime.MIN)
        );

        when(chatMessageRepository.findLatestByContextId(contextId, pageable))
                .thenReturn(Collections.singletonList(new ChatMessage()));
        when(messageMapper.toDto(any(ChatMessage.class))).thenReturn(
                new MessageDto(1L, "test", "user", LocalDateTime.MIN)
        );

        List<MessageDto> actualMessageDtos = chatMessageService.findLatestByContextIdAsc(
                contextId, pageable
        );

        assertEquals(messageDtos, actualMessageDtos);
        verify(chatMessageRepository, times(1))
                .findLatestByContextId(contextId, pageable);
        verify(messageMapper, times(1)).toDto(any(ChatMessage.class));
    }

    @Test
    void createNewUserContextMessage_ShouldReturnSavedChatMessage() {
        Long chatId = 1L;
        String text = "Hello";
        TelegramChat chat = new TelegramChat();
        ChatMessage chatMessage = new ChatMessage();

        when(telegramChatService.getByChatIdWithTargetContext(chatId)).thenReturn(chat);
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        ChatMessage savedChatMessage = chatMessageService.createNewUserContextMessage(chatId, text);

        assertEquals(chatMessage, savedChatMessage);
        verify(telegramChatService, times(1)).getByChatIdWithTargetContext(chatId);
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void createNewUserMessage_ShouldReturnSavedChatMessage() {
        Long chatId = 1L;
        String text = "Hello";
        TelegramChat chat = new TelegramChat();
        ChatMessage chatMessage = new ChatMessage();

        when(telegramChatService.getByChatIdWithTargetContext(chatId)).thenReturn(chat);
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        ChatMessage savedChatMessage = chatMessageService.createNewUserMessage(chatId, text);

        assertEquals(chatMessage, savedChatMessage);
        verify(telegramChatService, times(1)).getByChatIdWithTargetContext(chatId);
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }
}
