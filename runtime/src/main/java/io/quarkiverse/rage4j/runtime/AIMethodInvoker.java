package io.quarkiverse.rage4j.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkiverse.rage4j.runtime.holder.AIMethodHolder;

@ApplicationScoped
public class AIMethodInvoker {

    @Inject
    AIMethodHolder aiMethodHolder;

    public String getAiAnswer(String question) {
        return invokeMethod(question);
    }

    private String invokeMethod(String question) {
        try {
            Method method = aiMethodHolder.getMethod();
            method.setAccessible(true);
            return method
                    .invoke(aiMethodHolder.getAiServiceClass(), question)
                    .toString();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
