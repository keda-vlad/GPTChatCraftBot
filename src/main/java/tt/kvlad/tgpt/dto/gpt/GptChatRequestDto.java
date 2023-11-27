package tt.kvlad.tgpt.dto.gpt;

import java.util.List;

public record GptChatRequestDto(
        String model,
        List<GptMessageDto> messages,
        int n,
        double temperature
) {
}
