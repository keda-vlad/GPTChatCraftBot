package tt.kvlad.tgpt.service.message.handlers;

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
public class StartCommand implements ChatMessageHandler {
    private static final String HELLO_MESSAGE = """
            Hello! Welcome to the GPTChatCraftBot! 🚀
                        
            You're now connected with the power of GPT-3.5.
            Here are some commands that might come in handy:
                        
            /new [chat_name]: Create a new chat.
            For example: "/new CodingEnthusiasts".
            /chats: Get a list of available chats.
            /leave: Leave the current chat.
            
            Additionally, you have access to an administrative chat
            for communication with the administration.
                        
            Keep in mind that the maximum length for a chat name when creating one is 25 characters.
                        
            Enjoy your conversations! 🌐✨
            """;
    private static final String ADMIN_CONTEXT_NAME = "Admin chat";
    private final ChatMessageService chatMessageService;
    private final TelegramChatService telegramChatService;
    private final ContextService contextService;
    private final TelegramMessageSender telegramMessageSender;

    @Override
    @Transactional
    public void handleMessage(Long chatId, String text) {
        if (!telegramChatService.isPresentInDataBase(chatId)) {
            TelegramChat chat = telegramChatService.createByChatId(chatId);
            contextService.createContext(chat, ADMIN_CONTEXT_NAME, Context.ContextType.ADMIN);
        }
        ChatMessage message = chatMessageService.createNewUserMessage(chatId, text);
        ChatMessage answer = message.buildAnswerMessage(
                HELLO_MESSAGE,
                ChatMessage.SignatureType.BOT.signature()
        );
        chatMessageService.save(answer);
        telegramMessageSender.sendMessage(answer);
    }

    @Override
    public MessageStrategyType getType() {
        return MessageStrategyType.START;
    }
}
