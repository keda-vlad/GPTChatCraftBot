package tt.kvlad.tgpt.dto.message;

import java.time.LocalDateTime;

public record MessageDto(
        Long id,
        String text,
        String signature,
        LocalDateTime creationTime
) {
}
