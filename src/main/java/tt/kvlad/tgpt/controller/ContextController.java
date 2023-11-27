package tt.kvlad.tgpt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.service.context.ContextService;
import tt.kvlad.tgpt.service.message.ChatMessageService;

@Tag(name = "Context management", description = "Endpoints for managing contexts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/contexts")
public class ContextController {
    private final ContextService contextService;
    private final ChatMessageService messageService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get contexts", description = "Get all contexts.")
    public List<ContextDtoWithoutMessages> getAll(Pageable pageable) {
        return contextService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get context by id", description = "Get context by id.")
    public ContextDtoWithoutMessages getById(@PathVariable Long id) {
        return contextService.getById(id);
    }

    @GetMapping("/{id}/messages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get context messages", description = "Get context messages by id")
    public List<MessageDto> getAllContextMessagesById(@PathVariable Long id, Pageable pageable) {
        return messageService.getAllByContextId(id, pageable);
    }
}
