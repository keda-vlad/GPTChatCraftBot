package tt.kvlad.tgpt.service.callbackquery.impl;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tt.kvlad.tgpt.exception.StrategyException;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryManager;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategy;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategyType;

@Component
@RequiredArgsConstructor
public class CallbackQueryManagerImpl implements CallbackQueryManager {
    private static final String ADMIN_CONTEXT_NAME = "Admin chat";
    private final CallbackQueryStrategy callbackQueryStrategy;

    @Override
    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String text = callbackQuery.getData();
        CallbackQueryStrategyType type;
        String contextName;
        if (text.matches(CallbackQueryStrategyType.OPERATIONS.key() + ADMIN_CONTEXT_NAME)) {
            type = CallbackQueryStrategyType.OPEN;
            contextName = text.substring(CallbackQueryStrategyType.OPERATIONS.key().length());
        } else {
            type = parseType(text);
            contextName = text.substring(type.key().length());
        }
        callbackQueryStrategy.getStrategyHandler(type).handleCallbackQuery(chatId, contextName);
    }

    private CallbackQueryStrategyType parseType(String text) {
        return Arrays.stream(CallbackQueryStrategyType.values())
                .filter(type -> text.startsWith(type.key()))
                .findFirst().orElseThrow(() -> new StrategyException(
                                "Can't parse callback strategy type by text = " + text
                        )
                );
    }
}
