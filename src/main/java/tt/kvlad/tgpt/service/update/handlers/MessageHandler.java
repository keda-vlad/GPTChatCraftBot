package tt.kvlad.tgpt.service.update.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import tt.kvlad.tgpt.service.message.MessageManager;
import tt.kvlad.tgpt.service.update.UpdateHandler;
import tt.kvlad.tgpt.service.update.UpdateStrategyType;

@Component
@RequiredArgsConstructor
public class MessageHandler implements UpdateHandler {
    private final MessageManager messageManager;

    @Override
    public void handleUpdate(Update update) {
        messageManager.handleMessage(update.getMessage());
    }

    @Override
    public UpdateStrategyType getType() {
        return UpdateStrategyType.MESSAGE;
    }
}
