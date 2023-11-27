package tt.kvlad.tgpt.service.callbackquery;

public interface CallbackQueryHandler {
    void handleCallbackQuery(Long chatId, String text);

    CallbackQueryStrategyType getType();
}
