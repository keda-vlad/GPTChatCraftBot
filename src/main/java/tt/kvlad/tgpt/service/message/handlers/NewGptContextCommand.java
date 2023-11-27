package tt.kvlad.tgpt.service.message.handlers;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tt.kvlad.tgpt.model.ChatMessage;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.model.TelegramChat;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.chat.TelegramMessageSender;
import tt.kvlad.tgpt.service.context.ContextService;
import tt.kvlad.tgpt.service.message.ChatMessageHandler;
import tt.kvlad.tgpt.service.message.ChatMessageService;
import tt.kvlad.tgpt.service.message.MessageStrategyType;

@Component
@RequiredArgsConstructor
public class NewGptContextCommand implements ChatMessageHandler {
    private static final String SUCCESS_MESSAGE = """
            "%s" chat is created. What is your question?
            """;
    private static final String EXISTED_CONTEXT_MESSAGE = """
            Failed to create chat. You already have a chat with a name "%s"
            """;
    private static final String EMPTY_NAME_MESSAGE = """
            Failed to create chat. The chat must have a name.
            Example of creation: "/new Chat for learning English"
            """;
    private static final String OVER_MAX_SIZE_CONTEXTS_MESSAGE = """
            You have reached the maximum limit for the number of chats.
            To create a new chat, you must delete an existing one.
            """;
    private static final int MAX_CONTEXT_NAME_LENGTH = 25;
    private static final int MAX_CONTEXTS_PER_USER = 5;
    private static final int START_NAME_INDEX = 4;
    private final ContextService contextService;
    private final TelegramChatService telegramChatService;
    private final TelegramMessageSender telegramMessageSender;
    private final ChatMessageService chatMessageService;

    @Override
    @Transactional
    public void handleMessage(Long chatId, String text) {
        ChatMessage message = chatMessageService.createNewUserMessage(chatId, text);
        TelegramChat chat = telegramChatService.getByChatIdWithAllContext(chatId);
        String contextName = parseName(message.getText());
        ChatMessage answer;
        if (isMaxContextsExceeded(chat)) {
            answer = buildAnswer(message, OVER_MAX_SIZE_CONTEXTS_MESSAGE);
        } else if (contextName.isEmpty()) {
            answer = buildAnswer(message, EMPTY_NAME_MESSAGE);
        } else if (isAlreadyExist(chat, contextName)) {
            answer = buildAnswer(message, String.format(EXISTED_CONTEXT_MESSAGE, contextName));
        } else {
            answer = buildAnswer(message, String.format(SUCCESS_MESSAGE, contextName));
            Context context = contextService.createContext(
                    message.getTelegramChat(), contextName, Context.ContextType.GPT
            );
            message.getTelegramChat().setTargetContext(context);
            telegramChatService.save(message.getTelegramChat());
        }
        chatMessageService.save(answer);
        telegramMessageSender.sendMessage(answer);
    }

    @Override
    public MessageStrategyType getType() {
        return MessageStrategyType.NEW_CHAT;
    }

    private boolean isMaxContextsExceeded(TelegramChat chat) {
        return chat.getContexts().size() > MAX_CONTEXTS_PER_USER;
    }

    private boolean isAlreadyExist(TelegramChat chat, String contextName) {
        return chat.getContexts().stream()
                .anyMatch(context -> Objects.equals(context.getName(), contextName));
    }

    private ChatMessage buildAnswer(ChatMessage message, String answer) {
        return message.buildAnswerMessage(
                answer,
                ChatMessage.SignatureType.BOT.signature()
        );
    }

    private String parseName(String text) {
        String name = text.substring(START_NAME_INDEX).trim();
        if (name.length() > MAX_CONTEXT_NAME_LENGTH) {
            name = name.substring(0, MAX_CONTEXT_NAME_LENGTH - 3) + "...";
        }
        return name;
    }
}
