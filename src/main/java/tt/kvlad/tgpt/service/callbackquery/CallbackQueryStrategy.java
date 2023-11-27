package tt.kvlad.tgpt.service.callbackquery;

public interface CallbackQueryStrategy {
    CallbackQueryHandler getStrategyHandler(CallbackQueryStrategyType type);
}
