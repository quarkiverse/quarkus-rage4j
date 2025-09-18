package io.quarkiverse.rage4j.deployment.builditem;

import java.lang.reflect.Method;

import io.quarkus.builder.item.SimpleBuildItem;

public final class AIServiceBuildItem extends SimpleBuildItem {
    private final Method methodName;
    private final Class<?> aiServiceClass;

    public AIServiceBuildItem(Method methodName, Class<?> aiServiceClass) {
        this.methodName = methodName;
        this.aiServiceClass = aiServiceClass;
    }

    public Class<?> getAiServiceClass() {
        return aiServiceClass;
    }

    public Method getMethodName() {
        return methodName;
    }
}
