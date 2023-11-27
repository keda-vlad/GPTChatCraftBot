package tt.kvlad.tgpt.service.callbackquery.handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryHandler;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategyType;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class OperationsContextHandler implements CallbackQueryHandler {
    private static final String OPERATIONS_MESSAGE = """
            "Chat Name: %s
            Please select an action for the chat:
                        
            OPEN: Choose this option to open and access the chat.
            DELETE: Choose this option to delete and close the chat.
            """;
    private final TelegramChatService telegramChatService;
    private final ChatMessageService chatMessageService;
    private final TelegramMessageSender telegramMessageSender;

    @Override
    public void handleCallbackQuery(Long chatId, String text) {
        TelegramChat chat = telegramChatService.getByChatId(chatId);
        ChatMessage answer = answerMessage(chat, text);
        chatMessageService.save(answer);
        telegramMessageSender.sendMessageWithInlineMarkup(
                answer,
                buildOptionsButtons(text)
        );
    }

    private ChatMessage answerMessage(TelegramChat chat, String text) {
        return new ChatMessage()
                .setTelegramChat(chat)
                .setText(String.format(OPERATIONS_MESSAGE, text))
                .setCreationTime(LocalDateTime.now())
                .setSignature(ChatMessage.SignatureType.BOT.signature());
    }

    private Map<String, String> buildOptionsButtons(String contextName) {
        Map<String, String> buttons = new HashMap<>();
        buttons.put(CallbackQueryStrategyType.OPEN.name(),
                CallbackQueryStrategyType.OPEN.key() + contextName);
        buttons.put(CallbackQueryStrategyType.DELETE.name(),
                CallbackQueryStrategyType.DELETE.key() + contextName);
        return buttons;
    }

    @Override
    public CallbackQueryStrategyType getType() {
        return CallbackQueryStrategyType.OPERATIONS;
    }
}
