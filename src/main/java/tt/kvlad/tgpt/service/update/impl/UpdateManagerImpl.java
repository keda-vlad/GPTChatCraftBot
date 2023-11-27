package tt.kvlad.tgpt.service.update.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import tt.kvlad.tgpt.service.update.UpdateManager;
import tt.kvlad.tgpt.service.update.UpdateStrategy;
import tt.kvlad.tgpt.service.update.UpdateStrategyType;

@Component
@RequiredArgsConstructor
public class UpdateManagerImpl implements UpdateManager {
    private final UpdateStrategy updateStrategy;

    @Override
    public void handle(Update update) {
        UpdateStrategyType type = parseStrategyType(update);
        updateStrategy.getStrategyHandler(type).handleUpdate(update);
    }

    private UpdateStrategyType parseStrategyType(Update update) {
        if (update.hasMessage()) {
            return UpdateStrategyType.MESSAGE;
        } else if (update.hasCallbackQuery()) {
            return UpdateStrategyType.CALLBACK;
        } else {
            return UpdateStrategyType.DEFAULT;
        }
    }
}






