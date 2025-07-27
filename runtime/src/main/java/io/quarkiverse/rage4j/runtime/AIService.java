package io.quarkiverse.rage4j.runtime;

import java.lang.reflect.InvocationTargetException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AIService {

    @ConfigProperty(name = "quarkus.rage4j.api-key")
    String apiKey;

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
