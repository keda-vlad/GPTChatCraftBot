package tt.kvlad.tgpt.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextHandler;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class AdminCommunication implements ContextHandler {
    private static final String ADMIN_ANSWER = """
                         Thank you for your inquiry.
                         Your request has been forwarded to the administration.
                         """;
    private final ChatMessageService chatMessageService;
    private final TelegramMessageSender telegramMessageSender;

    @Override
    public void handleMessageContext(ChatMessage message) {
        ChatMessage answer = message.buildAnswerMessage(
                ADMIN_ANSWER,
                ChatMessage.SignatureType.BOT.signature()
        );
        chatMessageService.save(message);
        telegramMessageSender.sendMessage(answer);
    }

    @Override
    public Context.ContextType getType() {
        return Context.ContextType.ADMIN;
    }
}
