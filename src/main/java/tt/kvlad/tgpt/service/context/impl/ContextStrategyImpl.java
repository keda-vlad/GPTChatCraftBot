package tt.kvlad.tgpt.service.context.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.exception.StrategyException;
import tt.kvlad.tgpt.model.Context;
import tt.kvlad.tgpt.service.context.ContextHandler;
import tt.kvlad.tgpt.service.context.ContextStrategy;

@Component
@RequiredArgsConstructor
public class ContextStrategyImpl implements ContextStrategy {
    private final List<ContextHandler> chatStrategyHandlers;

    @Override
    public ContextHandler getIntegrationHandler(Context.ContextType chatType) {
        return chatStrategyHandlers.stream()
                .filter(handler -> handler.getType() == chatType)
                .findFirst().orElseThrow(
                        () -> new StrategyException(
                                "Can't find context strategy by type = " + chatType
                        )
                );
    }
}
