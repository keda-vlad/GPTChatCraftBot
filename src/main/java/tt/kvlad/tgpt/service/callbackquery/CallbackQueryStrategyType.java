package tt.kvlad.tgpt.service.callbackquery;

public enum CallbackQueryStrategyType {
    OPERATIONS("OPERATIONS#"),
    DELETE("DELETE#"),
    OPEN("OPEN#");

    private String key;

    CallbackQueryStrategyType(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
