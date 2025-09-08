package io.quarkiverse.rage4j.it;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyAIService {
    public String answerQuestion(String question) {
        return "I don't know";
    }
}
