package tt.kvlad.tgpt.service.context;

import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.Context;

public interface ContextHandler {
    void handleMessageContext(ChatMessage message);

    Context.ContextType getType();
}
