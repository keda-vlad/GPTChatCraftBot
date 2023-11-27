package tt.kvlad.tgpt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tt.kvlad.tgpt.config.MapperConfig;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.model.Context;

@Mapper(config = MapperConfig.class)
public interface ContextMapper {
    @Mapping(source = "contextType", target = "type")
    ContextDtoWithoutMessages toDtoWithoutMessages(Context context);
}
