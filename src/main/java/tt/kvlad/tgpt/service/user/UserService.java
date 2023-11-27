package tt.kvlad.tgpt.service.user;

import java.util.List;
import org.springframework.data.domain.Pageable;
import tt.kvlad.tgpt.dto.user.UpdateUserDto;
import tt.kvlad.tgpt.dto.user.UserDto;
import tt.kvlad.tgpt.dto.user.UserRegistrationRequestDto;
import tt.kvlad.tgpt.model.User;

public interface UserService {
    UserDto register(UserRegistrationRequestDto request);

    void delete(Long id);

    UserDto getInfo(User currentUser);

    UserDto update(UpdateUserDto updateUserDto, User user);

    List<UserDto> getAll(Pageable pageable);
}
