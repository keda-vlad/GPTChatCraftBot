package tt.kvlad.tgpt.dto.gpt;

import java.util.List;

public record GptChatResponseDto(
        List<GptChoiceDto> choices
) {
}
