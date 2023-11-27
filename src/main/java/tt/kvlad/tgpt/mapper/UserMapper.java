package tt.kvlad.tgpt.mapper;

import org.mapstruct.Mapper;
import tt.kvlad.tgpt.config.MapperConfig;
import tt.kvlad.tgpt.dto.user.UpdateUserDto;
import tt.kvlad.tgpt.dto.user.UserDto;
import tt.kvlad.tgpt.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(UpdateUserDto updateUserDto);
}
