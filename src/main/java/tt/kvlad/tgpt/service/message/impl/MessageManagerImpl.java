package tt.kvlad.tgpt.service.message.impl;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import tt.kvlad.tgpt.service.message.ChatMessageStrategy;
import tt.kvlad.tgpt.service.message.MessageManager;
import tt.kvlad.tgpt.service.message.MessageStrategyType;

@Component
@RequiredArgsConstructor
public class MessageManagerImpl implements MessageManager {
    private final ChatMessageStrategy messageStrategy;

    @Override
    public void handleMessage(Message message) {
        MessageStrategyType type = parseMessageType(message);
        Long chatId = message.getChatId();
        String text = message.getText();
        messageStrategy.getStrategyHandler(type).handleMessage(chatId, text);
    }

    private MessageStrategyType parseMessageType(Message message) {
        return Arrays.stream(MessageStrategyType.values())
                .filter(type -> message.getText().matches(type.pattern()))
                .findFirst().orElse(MessageStrategyType.COMMUNICATION);
    }
}
