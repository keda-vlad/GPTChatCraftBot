package tt.kvlad.tgpt.dto.user;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName
) {
}
