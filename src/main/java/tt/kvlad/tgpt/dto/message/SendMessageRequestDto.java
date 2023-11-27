package tt.kvlad.tgpt.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record SendMessageRequestDto(
        @PositiveOrZero
        Long chatId,
        @NotBlank(message = "required field")
        String text
) {
}
