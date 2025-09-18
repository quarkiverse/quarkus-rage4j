package io.quarkiverse.rage4j.runtime.holder;

import java.lang.reflect.Method;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AIMethodHolder {
    private Object aiServiceClass;
    private Method method;

    public void setAiServiceClass(Object aiServiceClass) {
        this.aiServiceClass = aiServiceClass;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getAiServiceClass() {
        return aiServiceClass;
    }

    public Method getMethod() {
        return method;
    }
}
