package tt.kvlad.tgpt.service.chat.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tt.kvlad.tgpt.dto.chat.ChatDto;
import tt.kvlad.tgpt.exception.EntityNotFoundException;
import tt.kvlad.tgpt.mapper.ChatMapper;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.repository.TelegramChatRepository;

@ExtendWith(MockitoExtension.class)
class TelegramChatServiceImplTest {

    @Mock
    private TelegramChatRepository telegramChatRepository;

    @Mock
    private ChatMapper chatMapper;

    @InjectMocks
    private TelegramChatServiceImpl telegramChatService;

    @Test
    @DisplayName("Verify getByChatIdWithTargetContext() method works for TelegramChatServiceImpl")
    void getByChatIdWithTargetContext_ValidChatId_ReturnTelegramChat() {
        Long chatId = 123L;
        TelegramChat telegramChat = new TelegramChat();

        when(telegramChatRepository.getByChatIdWithTargetContext(chatId))
                .thenReturn(Optional.of(telegramChat));

        TelegramChat result = telegramChatService.getByChatIdWithTargetContext(chatId);

        assertEquals(telegramChat, result);
        verify(telegramChatRepository).getByChatIdWithTargetContext(chatId);
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify getByChatIdWithAllContext() method works for TelegramChatServiceImpl")
    void getByChatIdWithAllContext_ValidChatId_ReturnTelegramChat() {
        Long chatId = 123L;
        TelegramChat telegramChat = new TelegramChat();

        when(telegramChatRepository.getByChatIdWithAllContexts(chatId))
                .thenReturn(Optional.of(telegramChat));

        TelegramChat result = telegramChatService.getByChatIdWithAllContext(chatId);

        assertEquals(telegramChat, result);
        verify(telegramChatRepository).getByChatIdWithAllContexts(chatId);
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify getById() method works for TelegramChatServiceImpl")
    void getById_ValidId_ReturnChatDto() {
        Long id = 1L;
        TelegramChat telegramChat = new TelegramChat();
        ChatDto chatDto = new ChatDto(1L, 1L, List.of());

        when(telegramChatRepository.getByIdWithAllContexts(id))
                .thenReturn(Optional.of(telegramChat));
        when(chatMapper.toDto(telegramChat)).thenReturn(chatDto);

        ChatDto result = telegramChatService.getById(id);

        assertEquals(chatDto, result);
        verify(telegramChatRepository).getByIdWithAllContexts(id);
        verify(chatMapper).toDto(telegramChat);
        verifyNoMoreInteractions(telegramChatRepository, chatMapper);
    }

    @Test
    @DisplayName("Verify getById() throws EntityNotFoundException for TelegramChatServiceImpl")
    void getById_InvalidId_ThrowEntityNotFoundException() {
        when(telegramChatRepository.getByIdWithAllContexts(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> telegramChatService.getById(100L));

        verify(telegramChatRepository).getByIdWithAllContexts(100L);
        verifyNoMoreInteractions(telegramChatRepository, chatMapper);
    }

    @Test
    @DisplayName("Verify isPresentInDataBase() method works for TelegramChatServiceImpl")
    void isPresentInDataBase_ValidChatId_ReturnTrue() {
        Long chatId = 123L;

        when(telegramChatRepository.findByChatId(chatId))
                .thenReturn(Optional.of(new TelegramChat()));

        assertTrue(telegramChatService.isPresentInDataBase(chatId));

        verify(telegramChatRepository).findByChatId(chatId);
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify isPresentInDataBase() method works for TelegramChatServiceImpl")
    void isPresentInDataBase_InvalidChatId_ReturnFalse() {
        Long chatId = 123L;

        when(telegramChatRepository.findByChatId(chatId)).thenReturn(Optional.empty());

        assertFalse(telegramChatService.isPresentInDataBase(chatId));

        verify(telegramChatRepository).findByChatId(chatId);
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify createByChatId() method works for TelegramChatServiceImpl")
    void createByChatId_ValidChatId_ReturnTelegramChat() {
        Long chatId = 123L;
        TelegramChat telegramChat = new TelegramChat();

        when(telegramChatRepository.save(any())).thenReturn(telegramChat);

        TelegramChat result = telegramChatService.createByChatId(chatId);

        assertEquals(telegramChat, result);
        verify(telegramChatRepository).save(any());
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify save() method works for TelegramChatServiceImpl")
    void save_ValidTelegramChat_CallsSaveInRepository() {
        TelegramChat telegramChat = new TelegramChat();

        telegramChatService.save(telegramChat);

        verify(telegramChatRepository).save(telegramChat);
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify getByChatId() method works for TelegramChatServiceImpl")
    void getByChatId_ValidChatId_ReturnTelegramChat() {
        Long chatId = 123L;
        TelegramChat telegramChat = new TelegramChat();

        when(telegramChatRepository.findByChatId(chatId)).thenReturn(Optional.of(telegramChat));

        TelegramChat result = telegramChatService.getByChatId(chatId);

        assertEquals(telegramChat, result);
        verify(telegramChatRepository).findByChatId(chatId);
        verifyNoMoreInteractions(telegramChatRepository);
    }

    @Test
    @DisplayName("Verify getByChatId() throws EntityNotFoundException for TelegramChatServiceImpl")
    void getByChatId_InvalidChatId_ThrowEntityNotFoundException() {
        when(telegramChatRepository.findByChatId(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> telegramChatService.getByChatId(100L));

        verify(telegramChatRepository).findByChatId(100L);
        verifyNoMoreInteractions(telegramChatRepository);
    }
}
