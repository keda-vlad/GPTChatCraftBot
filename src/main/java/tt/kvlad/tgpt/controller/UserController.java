package tt.kvlad.tgpt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tt.kvlad.tgpt.dto.user.UpdateUserDto;
import tt.kvlad.tgpt.dto.user.UserDto;
import tt.kvlad.tgpt.dto.user.UserRegistrationRequestDto;
import tt.kvlad.tgpt.exception.RegistrationException;
import tt.kvlad.tgpt.model.User;
import tt.kvlad.tgpt.service.user.UserService;

@Tag(name = "User management", description = "Endpoints for managing users")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admins")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get current authenticated admin")
    UserDto getInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getInfo(user);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update admin")
    UserDto update(@Valid @RequestBody UpdateUserDto updateUserDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.update(updateUserDto, user);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Register admin")
    public UserDto register(
            @RequestBody @Valid UserRegistrationRequestDto request
    ) throws RegistrationException {
        return userService.register(request);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all admins")
    public List<UserDto> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @DeleteMapping ("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete admin by ID")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
