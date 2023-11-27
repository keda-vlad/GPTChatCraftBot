package tt.kvlad.tgpt.service.chat.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tt.kvlad.tgpt.event.TelegramMethodEvent;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;

@Component
@RequiredArgsConstructor
public class TelegramMessageSenderImpl implements TelegramMessageSender {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void sendMessage(ChatMessage answer) {
        SendMessage message = SendMessage.builder()
                .chatId(answer.getTelegramChat().getChatId())
                .text(answer.getText())
                .build();
        applicationEventPublisher.publishEvent(TelegramMethodEvent.of(this, message));
    }

    @Override
    public void sendMessage(List<ChatMessage> answers) {
        answers.forEach(this::sendMessage);
    }

    @Override
    public void sendMessageWithInlineMarkup(
            ChatMessage answer,
            Map<String, String> buttonsText) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttonsText.forEach((key, value) -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(key)
                .callbackData(value)
                .build())));
        SendMessage message = SendMessage.builder()
                .chatId(answer.getTelegramChat().getChatId())
                .text(answer.getText())
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttons)
                        .build())
                .build();
        applicationEventPublisher.publishEvent(TelegramMethodEvent.of(this, message));
    }
}
