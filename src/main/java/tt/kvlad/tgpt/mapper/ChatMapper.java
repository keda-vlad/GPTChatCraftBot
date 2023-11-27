package tt.kvlad.tgpt.mapper;

import org.mapstruct.Mapper;
import tt.kvlad.tgpt.config.MapperConfig;
import tt.kvlad.tgpt.dto.chat.ChatDto;
import tt.kvlad.tgpt.model.TelegramChat;

@Mapper(config = MapperConfig.class, uses = ContextMapper.class)
public interface ChatMapper {
    ChatDto toDto(TelegramChat telegramChat);
}
