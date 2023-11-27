package tt.kvlad.tgpt.service.callbackquery.handlers;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryHandler;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategyType;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextService;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class DeleteContextHandler implements CallbackQueryHandler {
    private static final String DELETE_MESSAGE = "\"%s\" is deleted";
    private final ContextService contextService;
    private final TelegramChatService telegramChatService;
    private final ChatMessageService chatMessageService;
    private final TelegramMessageSender telegramMessageSender;

    @Override
    public void handleCallbackQuery(Long chatId, String text) {
        TelegramChat chat = telegramChatService.getByChatIdWithTargetContext(chatId);
        if (chat.getTargetContext() != null
                && Objects.equals(chat.getTargetContext().getName(), text)) {
            chat.setTargetContext(null);
            telegramChatService.save(chat);
        }
        contextService.deleteByChatIdAndName(chatId, text);
        ChatMessage answer = answerMessage(chat, text);
        chatMessageService.save(answer);
        telegramMessageSender.sendMessage(answer);
    }

    @Override
    public CallbackQueryStrategyType getType() {
        return CallbackQueryStrategyType.DELETE;
    }

    private ChatMessage answerMessage(TelegramChat chat, String text) {
        return new ChatMessage()
                .setTelegramChat(chat)
                .setText(String.format(DELETE_MESSAGE, text))
                .setCreationTime(LocalDateTime.now())
                .setSignature(ChatMessage.SignatureType.BOT.signature());
    }
}
