package tt.kvlad.tgpt.service.message.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.exception.StrategyException;
import tt.kvlad.tgpt.service.message.ChatMessageHandler;
import tt.kvlad.tgpt.service.message.ChatMessageStrategy;
import tt.kvlad.tgpt.service.message.MessageStrategyType;

@Component
@RequiredArgsConstructor
public class MessageStrategyImpl implements ChatMessageStrategy {
    private final List<ChatMessageHandler> messageHandlers;

    @Override
    public ChatMessageHandler getStrategyHandler(MessageStrategyType type) {
        return messageHandlers.stream()
                .filter(handler -> handler.getType() == type)
                .findFirst().orElseThrow(
                        () -> new StrategyException(
                                "Can't find message strategy by type = " + type
                        )
                );
    }
}
