package tt.kvlad.tgpt.service.message;

public interface ChatMessageStrategy {
    ChatMessageHandler getStrategyHandler(MessageStrategyType type);
}
