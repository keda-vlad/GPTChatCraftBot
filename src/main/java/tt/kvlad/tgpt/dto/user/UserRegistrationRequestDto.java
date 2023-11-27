package tt.kvlad.tgpt.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tt.kvlad.tgpt.validator.FieldMatch;

@FieldMatch(first = "password", second = "repeatPassword",
        message = "passwords must match")
public record UserRegistrationRequestDto(
        @NotBlank(message = "required field")
        @Size(min = 4, max = 255, message = "size must be between 4 and 255 character")
        @Email(message = "invalid email format")
        String email,
        @NotBlank(message = "required field")
        @Size(min = 8, max = 50, message = "size must be between 8 and 50 character")
        String password,
        @NotBlank(message = "required field")
        @Size(min = 8, max = 50, message = "size must be between 8 and 50 character")
        String repeatPassword,
        @NotBlank(message = "required field")
        @Size(min = 2, max = 50, message = "size must be between 2 and 50 character")
        String firstName,
        @NotBlank
        @Size(min = 2, max = 50, message = "size must be between 2 and 50 character")
        String lastName
) {
}
