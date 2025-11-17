package io.quarkiverse.rage4j.it;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import dev.rage4j.asserts.exception.Rage4JCorrectnessException;
import io.quarkiverse.rage4j.runtime.annotations.TestAIService;
import io.quarkiverse.rage4j.runtime.junitextension.Rage4jTestExtension;
import io.quarkiverse.rage4j.runtime.wrapper.RageAssert;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@ExtendWith(Rage4jTestExtension.class)
class Rage4jTest {

    private static final String CORRECT_ANSWER = """
            The answer to "life, the universe, and everything" is famously known to be **42**. This concept originates
            from Douglas Adams' science fiction series "The Hitchhiker's Guide to the Galaxy." In the story, a supercomputer
            named Deep Thought calculates the answer to the ultimate question of life, the universe, and everything, and
            comes up with the number 42, though the actual question remains unknown. This humorous notion has become a
            cultural reference, representing the search for meaning in life and the complexity of existence. If you're
            curious about the deeper philosophical questions behind it or how it relates to real-world concepts, feel free to ask!
            """;

    @Inject
    RageAssert rageAssert;

    @Inject
    MyAiService aiService;

    @TestAIService
    public String answer(String question) {
        return aiService.chat(question);
    }

    @Test
    @Disabled
    void shouldNotFail() {
        rageAssert
                .question("What is the answer to life, the universe and everything?")
                .groundTruth(CORRECT_ANSWER)
                .threshold(0.50)
                .assertAnswerCorrectness();
    }

    @Test
    @Disabled
    void shouldFail() {
        assertThrows(Rage4JCorrectnessException.class, () -> rageAssert
                .question("What is the answer to life, the universe and everything?")
                .groundTruth("Nothing.")
                .threshold(0.50)
                .assertAnswerCorrectness());
    }
}
