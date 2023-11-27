package tt.kvlad.tgpt.service.context.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.service.context.ContextManager;
import tt.kvlad.tgpt.service.context.ContextStrategy;

@Component
@RequiredArgsConstructor
public class ContextManagerImpl implements ContextManager {
    private final ContextStrategy contextStrategy;

    @Override
    public void processContextMessage(ChatMessage chatMessage) {
        contextStrategy
                .getIntegrationHandler(chatMessage.getContext().getContextType())
                .handleMessageContext(chatMessage);
    }
}
