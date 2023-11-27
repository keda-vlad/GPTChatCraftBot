package tt.kvlad.tgpt.service.message.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.message.ChatMessageHandler;
import tt.kvlad.tgpt.service.message.MessageStrategyType;

@Component
@RequiredArgsConstructor
public class LeaveTargetContext implements ChatMessageHandler {
    private final TelegramChatService telegramChatService;

    @Override
    public void handleMessage(Long chatId, String text) {
        TelegramChat chat = telegramChatService.getByChatId(chatId);
        chat.setTargetContext(null);
        telegramChatService.save(chat);
    }

    @Override
    public MessageStrategyType getType() {
        return MessageStrategyType.LEAVE_CHAT;
    }
}
