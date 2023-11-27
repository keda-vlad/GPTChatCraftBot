package tt.kvlad.tgpt.service.context;

import tt.kvlad.tgpt.model.ChatMessage;

public interface ContextManager {
    void processContextMessage(ChatMessage chatMessage);
}
