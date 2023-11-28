package tt.kvlad.tgpt.service.context.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.exception.EntityNotFoundException;
import tt.kvlad.tgpt.mapper.ContextMapper;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.repository.ContextRepository;

@ExtendWith(MockitoExtension.class)
class ContextServiceImplTest {

    @Mock
    private ContextRepository contextRepository;

    @Mock
    private ContextMapper contextMapper;

    @InjectMocks
    private ContextServiceImpl contextService;

    @Test
    @DisplayName("Verify getAllByChatId() method works for ContextServiceImpl")
    void getAllByChatId_ValidChatId_ReturnListOfContextDtoWithoutMessages() {
        Long chatId = 123L;
        List<Context> contexts = Collections.singletonList(new Context());
        final List<ContextDtoWithoutMessages> contextDtoList = Collections.singletonList(
                new ContextDtoWithoutMessages(1L, "name", Context.ContextType.ADMIN)
        );

        when(contextRepository.getAllByChatId(chatId)).thenReturn(contexts);
        when(contextMapper.toDtoWithoutMessages(any())).thenReturn(
                new ContextDtoWithoutMessages(1L, "name", Context.ContextType.ADMIN)
        );
        List<ContextDtoWithoutMessages> result = contextService.getAllByChatId(chatId);

        assertNotNull(result);
        assertEquals(contextDtoList.size(), result.size());
        verify(contextRepository).getAllByChatId(chatId);
        verify(contextMapper, times(contexts.size())).toDtoWithoutMessages(any());
        verifyNoMoreInteractions(contextRepository, contextMapper);
    }

    @Test
    @DisplayName("Verify getById() method works for ContextServiceImpl")
    void getById_ValidId_ReturnContextDtoWithoutMessages() {
        Long id = 1L;
        Context context = new Context();
        ContextDtoWithoutMessages contextDto = new ContextDtoWithoutMessages(
                1L, "name", Context.ContextType.ADMIN
        );

        when(contextRepository.findById(id)).thenReturn(Optional.of(context));
        when(contextMapper.toDtoWithoutMessages(context)).thenReturn(contextDto);

        ContextDtoWithoutMessages result = contextService.getById(id);

        assertEquals(contextDto, result);
        verify(contextRepository).findById(id);
        verify(contextMapper).toDtoWithoutMessages(context);
        verifyNoMoreInteractions(contextRepository, contextMapper);
    }

    @Test
    @DisplayName("Verify getById() throws EntityNotFoundException for ContextServiceImpl")
    void getById_InvalidId_ThrowEntityNotFoundException() {
        when(contextRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> contextService.getById(100L));

        verify(contextRepository).findById(100L);
        verifyNoMoreInteractions(contextRepository, contextMapper);
    }

    @Test
    @DisplayName("Verify createContext() method works for ContextServiceImpl")
    void createContext_ValidData_ReturnContext() {
        Long chatId = 123L;
        TelegramChat telegramChat = new TelegramChat();
        String contextName = "ExampleContext";
        Context.ContextType contextType = Context.ContextType.ADMIN;
        Context context = new Context();

        when(contextRepository.save(any())).thenReturn(context);

        Context result = contextService.createContext(telegramChat, contextName, contextType);

        assertNotNull(result);
        verify(contextRepository).save(any());
        verifyNoMoreInteractions(contextRepository);
    }

    @Test
    @DisplayName("Verify getByChatIdAndName() method works for ContextServiceImpl")
    void getByChatIdAndName_ValidChatIdAndName_ReturnContext() {
        Long chatId = 123L;
        String contextName = "ExampleContext";
        Context context = new Context();

        when(contextRepository.findByChatIdAndName(chatId, contextName))
                .thenReturn(Optional.of(context));

        Context result = contextService.getByChatIdAndName(chatId, contextName);

        assertEquals(context, result);
        verify(contextRepository).findByChatIdAndName(chatId, contextName);
        verifyNoMoreInteractions(contextRepository);
    }

    @Test
    @DisplayName("Verify getByChatIdAndName() throw EntityNotFoundException for ContextServiceImpl")
    void getByChatIdAndName_InvalidChatIdAndName_ThrowEntityNotFoundException() {
        when(contextRepository.findByChatIdAndName(anyLong(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> contextService.getByChatIdAndName(100L, "InvalidContext")
        );

        verify(contextRepository).findByChatIdAndName(100L, "InvalidContext");
        verifyNoMoreInteractions(contextRepository);
    }

    @Test
    @DisplayName("Verify deleteByChatIdAndName() method works for ContextServiceImpl")
    void deleteByChatIdAndName_ValidChatIdAndName_CallsDeleteInRepository() {
        Long chatId = 123L;
        String contextName = "ExampleContext";
        Context context = new Context();

        when(contextRepository.findByChatIdAndName(chatId, contextName))
                .thenReturn(Optional.of(context));

        contextService.deleteByChatIdAndName(chatId, contextName);

        verify(contextRepository).findByChatIdAndName(chatId, contextName);
        verify(contextRepository).delete(context);
        verifyNoMoreInteractions(contextRepository);
    }
}
