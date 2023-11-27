package tt.kvlad.tgpt.service.message;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageManager {
    void handleMessage(Message message);
}
