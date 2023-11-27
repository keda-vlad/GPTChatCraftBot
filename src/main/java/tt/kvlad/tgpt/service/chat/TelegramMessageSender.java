package tt.kvlad.tgpt.service.chat;

import java.util.List;
import java.util.Map;
import tt.kvlad.tgpt.model.ChatMessage;

public interface TelegramMessageSender {
    void sendMessage(ChatMessage answer);

    void sendMessage(List<ChatMessage> answers);

    void sendMessageWithInlineMarkup(
            ChatMessage answer,
            Map<String, String> buttons
    );
}
