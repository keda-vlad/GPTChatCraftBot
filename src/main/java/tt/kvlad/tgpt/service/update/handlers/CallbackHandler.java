package tt.kvlad.tgpt.service.update.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryManager;
import tt.kvlad.tgpt.service.update.UpdateHandler;
import tt.kvlad.tgpt.service.update.UpdateStrategyType;

@Component
@RequiredArgsConstructor
public class CallbackHandler implements UpdateHandler {
    private final CallbackQueryManager callbackQueryManager;

    @Override
    public void handleUpdate(Update update) {
        callbackQueryManager.handleCallbackQuery(update.getCallbackQuery());
    }

    @Override
    public UpdateStrategyType getType() {
        return UpdateStrategyType.CALLBACK;
    }
}
