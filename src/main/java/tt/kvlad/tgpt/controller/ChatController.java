package tt.kvlad.tgpt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.kvlad.tgpt.dto.chat.ChatDto;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.dto.message.SendMessageRequestDto;
import tt.kvlad.tgpt.service.chat.TelegramChatService;
import tt.kvlad.tgpt.service.message.AdminMessageSender;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Tag(name = "Chat management", description = "Endpoints for managing chats")
@RequiredArgsConstructor
@RestController
@RequestMapping("/chats")
public class ChatController {
    private final TelegramChatService telegramChatService;
    private final ChatMessageService messageService;
    private final AdminMessageSender adminMessageSender;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all chats")
    public List<ChatDto> getAll(Pageable pageable) {
        return telegramChatService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get target chat by id.")
    public ChatDto getById(@PathVariable Long id) {
        return telegramChatService.getById(id);
    }

    @GetMapping("/{id}/messages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all chat messages by chat id.")
    public List<MessageDto> getLog(@PathVariable Long id, Pageable pageable) {
        return messageService.getAllByChatId(id, pageable);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Send message to chat by id")
    public MessageDto sendMessage(@RequestBody @Valid SendMessageRequestDto request) {
        return adminMessageSender.makeAdminResponse(request.chatId(), request.text());
    }
}
