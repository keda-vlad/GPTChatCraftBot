package tt.kvlad.tgpt.service.callbackquery;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryManager {
    void handleCallbackQuery(CallbackQuery callbackQuery);
}
