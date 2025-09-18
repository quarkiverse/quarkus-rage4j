package io.quarkiverse.rage4j.runtime.recorder;

import io.quarkiverse.rage4j.runtime.holder.ApiKeyHolder;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class ApiKeyHolderRecorder {
    public void initApiKey(String apiKey) {
        ApiKeyHolder apiKeyHolder = Arc.container().instance(ApiKeyHolder.class).get();
        apiKeyHolder.setApiKey(apiKey);
    }
}
