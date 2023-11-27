package tt.kvlad.tgpt.dto.chat;

import java.util.List;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;

public record ChatDto(
        Long id,
        Long chatId,
        List<ContextDtoWithoutMessages> contexts
) {
}
