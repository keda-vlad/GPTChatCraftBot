package tt.kvlad.tgpt.service.update;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateManager {
    void handle(Update update);
}
