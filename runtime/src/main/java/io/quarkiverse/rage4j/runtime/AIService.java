package io.quarkiverse.rage4j.runtime;

import java.lang.reflect.InvocationTargetException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AIService {

    @Inject
    AIServiceHolder aiServiceHolder;

    public String getAiAnswer(String question) {
        return invokeMethod(question);
    }

    private String invokeMethod(String question) {
        try {
            return aiServiceHolder.getMethod().invoke(aiServiceHolder.getAiServiceClass(), question).toString();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
