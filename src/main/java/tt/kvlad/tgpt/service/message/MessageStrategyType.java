package tt.kvlad.tgpt.service.message;

public enum MessageStrategyType {
    COMMUNICATION("^[^/].*"),
    CHATS("^/chats$"),
    NEW_CHAT("^/new.*"),
    START("^/start$"),
    LEAVE_CHAT("^/leave$");

    private final String pattern;

    MessageStrategyType(String pattern) {
        this.pattern = pattern;
    }

    public String pattern() {
        return pattern;
    }
}
