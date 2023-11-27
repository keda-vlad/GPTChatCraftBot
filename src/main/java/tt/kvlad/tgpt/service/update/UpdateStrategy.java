package tt.kvlad.tgpt.service.update;

public interface UpdateStrategy {
    UpdateHandler getStrategyHandler(UpdateStrategyType update);
}
