package tt.kvlad.tgpt.service.message;

public interface ChatMessageHandler {
    void handleMessage(Long chatId, String text);

    MessageStrategyType getType();
}
