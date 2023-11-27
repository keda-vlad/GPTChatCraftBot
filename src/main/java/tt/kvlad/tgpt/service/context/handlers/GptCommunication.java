package tt.kvlad.tgpt.service.context.handlers;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.api.ChatGptClient;
import tt.kvlad.tgpt.dto.gpt.GptMessageDto;
import tt.kvlad.tgpt.mapper.MessageMapper;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextHandler;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Component
@RequiredArgsConstructor
public class GptCommunication implements ContextHandler {
    private static final int CONTEXT_SIZE = 20;
    private final ChatGptClient chatGptClient;
    private final MessageMapper messageMapper;
    private final TelegramMessageSender telegramMessageSender;
    private final ChatMessageService chatMessageService;

    @Override
    public void handleMessageContext(ChatMessage question) {
        List<GptMessageDto> context = chatMessageService.findLatestByContextIdAsc(
                question.getContext().getId(),
                PageRequest.of(0, CONTEXT_SIZE)
        ).stream()
                .filter(message -> ChatMessage.SignatureType.isGptRole(message.signature()))
                .map(messageMapper::toGptMessageDto)
                .toList();
        GptMessageDto gptMessageDto = chatGptClient.makeRequest(context);
        ChatMessage answer = messageMapper.toModel(gptMessageDto)
                .setCreationTime(LocalDateTime.now())
                .setTelegramChat(question.getTelegramChat())
                .setContext(question.getContext());
        chatMessageService.save(answer);
        telegramMessageSender.sendMessage(answer);
    }

    @Override
    public Context.ContextType getType() {
        return Context.ContextType.GPT;
    }
}
