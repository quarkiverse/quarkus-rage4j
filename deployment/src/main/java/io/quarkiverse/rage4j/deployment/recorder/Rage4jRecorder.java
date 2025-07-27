package io.quarkiverse.rage4j.deployment.recorder;

import java.lang.reflect.Method;

import io.quarkiverse.rage4j.runtime.AIServiceHolder;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class Rage4jRecorder {
    public RuntimeValue<AIServiceHolder> createAIServiceHolder(Object aiServiceClass, Method methodName, String apiKey) {
        return new RuntimeValue<>(new AIServiceHolder(aiServiceClass, methodName, apiKey));
    }
}
