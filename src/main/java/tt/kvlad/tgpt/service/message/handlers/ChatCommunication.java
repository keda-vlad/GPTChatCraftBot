package tt.kvlad.tgpt.service.message.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.service.context.ContextManager;
import tt.kvlad.tgpt.service.message.ChatMessageHandler;
import tt.kvlad.tgpt.service.message.ChatMessageService;
import tt.kvlad.tgpt.service.message.MessageStrategyType;

@Component
@RequiredArgsConstructor
public class ChatCommunication implements ChatMessageHandler {
    private final ChatMessageService chatMessageService;
    private final ContextManager contextManager;

    @Override
    public void handleMessage(Long chatId, String text) {
        ChatMessage message = chatMessageService.createNewUserContextMessage(chatId, text);
        if (message.getContext() != null) {
            contextManager.processContextMessage(message);
        }
    }

    @Override
    public MessageStrategyType getType() {
        return MessageStrategyType.COMMUNICATION;
    }
}
