package tt.kvlad.tgpt.api;

import jakarta.annotation.PostConstruct;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import tt.kvlad.tgpt.event.TelegramMethodEvent;
import tt.kvlad.tgpt.exception.TelegramException;
import tt.kvlad.tgpt.service.update.UpdateManager;

public class TelegramBot extends TelegramLongPollingBot {
    private final String botUsername;
    private final UpdateManager updateManager;

    public TelegramBot(String botUsername, String botToken, UpdateManager updateManager) {
        super(botToken);
        this.botUsername = botUsername;
        this.updateManager = updateManager;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateManager.handle(update);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @EventListener
    public void complete(TelegramMethodEvent event) {
        try {
            execute(event.getMethod());
        } catch (Exception e) {
            throw new TelegramException("Can't execute method = " + event.getMethod()
                    + " source = " + event.getSource());
        }
    }

    @PostConstruct
    private void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new TelegramException("Can't initialize telegram bot");
        }
    }
}
