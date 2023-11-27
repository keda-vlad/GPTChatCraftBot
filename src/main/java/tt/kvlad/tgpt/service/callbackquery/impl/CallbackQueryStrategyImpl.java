package tt.kvlad.tgpt.service.callbackquery.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tt.kvlad.tgpt.exception.StrategyException;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryHandler;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategy;
import tt.kvlad.tgpt.service.callbackquery.CallbackQueryStrategyType;

@Component
@RequiredArgsConstructor
public class CallbackQueryStrategyImpl implements CallbackQueryStrategy {
    private final List<CallbackQueryHandler> handlers;

    @Override
    public CallbackQueryHandler getStrategyHandler(CallbackQueryStrategyType type) {
        return handlers.stream()
                .filter(handlers -> handlers.getType() == type)
                .findFirst().orElseThrow(
                        () -> new StrategyException(
                                "Can't find callback strategy by type = " + type
                        )
                );
    }
}
