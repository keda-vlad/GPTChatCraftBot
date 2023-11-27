package tt.kvlad.tgpt.service.update.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.exception.StrategyException;
import tt.kvlad.tgpt.service.update.UpdateHandler;
import tt.kvlad.tgpt.service.update.UpdateStrategy;
import tt.kvlad.tgpt.service.update.UpdateStrategyType;

@Component
@RequiredArgsConstructor
public class UpdateStrategyImpl implements UpdateStrategy {
    private final List<UpdateHandler> updateHandlers;

    @Override
    public UpdateHandler getStrategyHandler(UpdateStrategyType type) {
        return updateHandlers.stream()
                .filter(handler -> handler.getType() == type)
                .findFirst()
                .orElseThrow(
                        () -> new StrategyException(
                                "Can't find update strategy by type = " + type
                        )
                );

    }
}
