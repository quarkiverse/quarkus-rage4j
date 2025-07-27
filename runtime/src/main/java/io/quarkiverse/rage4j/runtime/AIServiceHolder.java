package io.quarkiverse.rage4j.runtime;

import java.lang.reflect.Method;

public class AIServiceHolder {
    private final Object aiServiceClass;
    private final Method method;
    private final String apiKey;

    public AIServiceHolder(Object aiServiceClass, Method methodName, String apiKey) {
        this.aiServiceClass = aiServiceClass;
        this.method = methodName;
        this.apiKey = apiKey;
    }

    public Object getAiServiceClass() {
        return aiServiceClass;
    }

    public Method getMethod() {
        return method;
    }

    public String getApiKey() {
        return apiKey;
    }
}
