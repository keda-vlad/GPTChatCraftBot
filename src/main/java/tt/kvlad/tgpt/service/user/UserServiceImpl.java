package tt.kvlad.tgpt.service.user;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tt.kvlad.tgpt.dto.user.UpdateUserDto;
import tt.kvlad.tgpt.dto.user.UserDto;
import tt.kvlad.tgpt.dto.user.UserRegistrationRequestDto;
import tt.kvlad.tgpt.exception.EntityNotFoundException;
import tt.kvlad.tgpt.exception.RegistrationException;
import tt.kvlad.tgpt.mapper.UserMapper;
import tt.kvlad.tgpt.model.Role;
import tt.kvlad.tgpt.model.User;
import tt.kvlad.tgpt.repository.RoleRepository;
import tt.kvlad.tgpt.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto register(UserRegistrationRequestDto request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = new User()
                .setPassword(passwordEncoder.encode(request.password()))
                .setFirstName(request.firstName())
                .setLastName(request.lastName())
                .setEmail(request.email())
                .setRoles(Set.of(roleRepository.getByName(Role.RoleName.ROLE_ADMIN)));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto getInfo(User user) {
        return userMapper.toDto(getUserById(user.getId()));
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserDto update(UpdateUserDto updateUserDto, User user) {
        User updatedUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id = " + user.getId())
        ).setPassword(passwordEncoder.encode(updateUserDto.password()))
                .setFirstName(updateUserDto.firstName())
                .setLastName(updateUserDto.lastName())
                .setEmail(updateUserDto.email());
        return userMapper.toDto(userRepository.save(updatedUser));
    }

    @Override
    public List<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(userMapper::toDto)
                .toList();
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with id: " + id));
    }
}
