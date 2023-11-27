package tt.kvlad.tgpt.dto.context;

import tt.kvlad.tgpt.model.Context;

public record ContextDtoWithoutMessages(
        Long id,
        String name,
        Context.ContextType type
) {
}
