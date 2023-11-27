package tt.kvlad.tgpt.service.context;

import tt.kvlad.tgpt.model.Context;

public interface ContextStrategy {
    ContextHandler getIntegrationHandler(Context.ContextType chatType);
}

