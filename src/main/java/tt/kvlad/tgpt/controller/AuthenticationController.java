package tt.kvlad.tgpt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.kvlad.tgpt.dto.user.UserLoginRequestDto;
import tt.kvlad.tgpt.dto.user.UserLoginResponseDto;
import tt.kvlad.tgpt.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication management", description = "Endpoint for login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "login user")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
