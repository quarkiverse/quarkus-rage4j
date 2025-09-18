package io.quarkiverse.rage4j.it;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AiServiceWrapper {

    @Inject
    MyAiService myAiService;

    public String chatWithMyChatbot(String question) {
        return myAiService.chat(question);
    }
}
