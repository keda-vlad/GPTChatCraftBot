package tt.kvlad.tgpt.service.callbackquery.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.mapper.MessageMapper;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryHandler;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategyType;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextService;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class OpenContextHandler implements CallbackQueryHandler {
    private static final int CONTEXT_SIZE = 10;
    private static final String SWITCH_MESSAGE = "Now you in \"%s\" chat.";
    private final TelegramChatService telegramChatService;
    private final ContextService contextService;
    private final ChatMessageService chatMessageService;
    private final TelegramMessageSender messageSender;
    private final MessageMapper messageMapper;

    @Override
    public void handleCallbackQuery(Long chatId, String contextName) {
        TelegramChat chat = telegramChatService.getByChatId(chatId);
        Context newTargetContext = contextService.getByChatIdAndName(chatId, contextName);
        chat.setTargetContext(newTargetContext);
        telegramChatService.save(chat);
        List<ChatMessage> context = buildContextLog(chat, newTargetContext);
        messageSender.sendMessage(context);
    }

    @Override
    public CallbackQueryStrategyType getType() {
        return CallbackQueryStrategyType.OPEN;
    }

    private List<ChatMessage> buildContextLog(TelegramChat chat, Context context) {
        List<ChatMessage> contextLog = new ArrayList<>();
        ChatMessage switchMessage = buildSwitchMessage(context.getName(), chat);
        contextLog.add(chatMessageService.save(switchMessage));
        contextLog.addAll(chatMessageService.findLatestByContextIdAsc(
                context.getId(), PageRequest.of(0, CONTEXT_SIZE)
        ).stream().map(messageDto -> reverseMapping(messageDto, chat)).toList());
        return contextLog;
    }

    private ChatMessage buildSwitchMessage(String text, TelegramChat telegramChat) {
        String switchText = String.format(SWITCH_MESSAGE, text);
        return new ChatMessage()
                .setText(switchText)
                .setTelegramChat(telegramChat)
                .setSignature(ChatMessage.SignatureType.BOT.signature())
                .setCreationTime(LocalDateTime.now());
    }

    private ChatMessage reverseMapping(MessageDto messageDto, TelegramChat telegramChat) {
        return messageMapper.toModel(messageDto).setTelegramChat(telegramChat);
    }
}
