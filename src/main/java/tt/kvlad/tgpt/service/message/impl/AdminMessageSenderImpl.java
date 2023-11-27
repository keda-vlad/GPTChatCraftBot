package tt.kvlad.tgpt.service.message.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.dto.chat.ChatDto;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.mapper.MessageMapper;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextService;
import tt.kvlad.tgpt.service.message.AdminMessageSender;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class AdminMessageSenderImpl implements AdminMessageSender {
    private static final String ADMIN_CONTEXT_NAME = "Admin chat";
    private final TelegramChatService telegramChatService;
    private final ChatMessageService chatMessageService;
    private final ContextService contextService;
    private final TelegramMessageSender telegramMessageSender;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto makeAdminResponse(Long id, String text) {
        ChatDto chat = telegramChatService.getById(id);
        Context adminContext = contextService.getByChatIdAndName(chat.chatId(), ADMIN_CONTEXT_NAME);
        ChatMessage message = buildAdminMessage(text, adminContext);
        chatMessageService.save(message);
        telegramMessageSender.sendMessage(message);
        return messageMapper.toDto(message);
    }

    private ChatMessage buildAdminMessage(String text, Context context) {
        return new ChatMessage()
                .setSignature(ChatMessage.SignatureType.ADMIN.signature())
                .setText(text)
                .setTelegramChat(context.getTelegramChat())
                .setContext(context)
                .setCreationTime(LocalDateTime.now());
    }
}
