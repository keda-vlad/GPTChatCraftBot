package tt.kvlad.tgpt.service.message.handlers;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategyType;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextService;
import tt.kvlad.tgpt.service.message.ChatMessageHandler;
import tt.kvlad.tgpt.service.message.ChatMessageService;
import tt.kvlad.tgpt.service.message.MessageStrategyType;

@Component
@RequiredArgsConstructor
public class ChatsCommand implements ChatMessageHandler {
    private static final String MESSAGE = """
            Select a chat to explore!
            (/new [bot_name] for creating new chat)
            """;
    private final TelegramMessageSender telegramMessageSender;
    private final ChatMessageService chatMessageService;
    private final ContextService contextService;

    @Override
    public void handleMessage(Long chatId, String text) {
        ChatMessage message = chatMessageService.createNewUserMessage(chatId, text);
        ChatMessage answer = message.buildAnswerMessage(
                MESSAGE,
                ChatMessage.SignatureType.BOT.signature()
        );
        chatMessageService.save(answer);
        telegramMessageSender.sendMessageWithInlineMarkup(answer, buildChatsButtons(chatId));
    }

    @Override
    public MessageStrategyType getType() {
        return MessageStrategyType.CHATS;
    }

    private Map<String, String> buildChatsButtons(Long chatId) {
        return contextService.getAllByChatId(chatId).stream()
                .map(ContextDtoWithoutMessages::name)
                .collect(Collectors.toMap(
                        button -> button,
                        button -> CallbackQueryStrategyType.OPERATIONS.key() + button
                ));
    }
}
