package io.quarkiverse.rage4j.it;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkiverse.rage4j.runtime.annotations.TestAIService;
import io.quarkiverse.rage4j.runtime.wrapper.RageAssert;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestAIService(Rage4jResource.class)
class Rage4jResourceTest {

    @Inject
    RageAssert rageAssert;

    @Test
    void shouldFail() {
        rageAssert
                .question("What is the answer to life, the universe and everything?")
                .groundTruth("42")
                .threshold(0.5)
                .assertAnswerCorrectness();
    }
}
