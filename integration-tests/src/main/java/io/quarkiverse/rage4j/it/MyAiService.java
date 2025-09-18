package io.quarkiverse.rage4j.it;

import jakarta.enterprise.context.ApplicationScoped;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
@ApplicationScoped
@SystemMessage("""
        You are a helpful assistant. Your task is to answer questions clearly, precisely, and in a friendly manner.
        You support the user in understanding concepts, solving problems, and creating content. Always provide
        explanations that are easy to follow, include examples when possible, and adapt to the user’s needs.
        """)
public interface MyAiService {
    String chat(String question);
}
