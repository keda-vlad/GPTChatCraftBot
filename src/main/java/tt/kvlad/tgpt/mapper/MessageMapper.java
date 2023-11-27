package tt.kvlad.tgpt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tt.kvlad.tgpt.config.MapperConfig;
import tt.kvlad.tgpt.dto.gpt.GptMessageDto;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.model.ChatMessage;

@Mapper(config = MapperConfig.class)
public interface MessageMapper {
    @Mapping(source = "signature", target = "role")
    @Mapping(source = "text", target = "content")
    GptMessageDto toGptMessageDto(MessageDto message);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "telegramChat", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(source = "role", target = "signature")
    @Mapping(source = "content", target = "text")
    ChatMessage toModel(GptMessageDto gptMessageDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "telegramChat", ignore = true)
    ChatMessage toModel(MessageDto gptMessageDto);

    MessageDto toDto(ChatMessage chatMessage);
}
