package tt.kvlad.tgpt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tt.kvlad.tgpt.api.TelegramBot;
import tt.kvlad.tgpt.service.update.UpdateManager;

@Configuration
@RequiredArgsConstructor
public class BotConfig {
    private final UpdateManager updateManager;
    @Value(value = "${bot.name}")
    private String botName;
    @Value(value = "${bot.token}")
    private String token;

    @Bean
    public TelegramBot bot() {
        return new TelegramBot(botName, token, updateManager);
    }
}
