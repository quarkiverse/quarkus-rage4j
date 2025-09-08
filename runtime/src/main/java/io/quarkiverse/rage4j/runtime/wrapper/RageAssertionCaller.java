package io.quarkiverse.rage4j.runtime.wrapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import dev.rage4j.asserts.RageAssert;
import dev.rage4j.asserts.openai.OpenAiLLMBuilder;
import dev.rage4j.evaluation.rougescore.RougeScoreEvaluator;
import io.quarkiverse.rage4j.runtime.AIService;
import io.quarkiverse.rage4j.runtime.AIServiceHolder;

@ApplicationScoped
public class RageAssertionCaller {

    @Inject
    AIServiceHolder aiServiceHolder;

    @Inject
    AIService aiService;

    private RageAssert rageAssert = new OpenAiLLMBuilder().fromApiKey(aiServiceHolder.getApiKey());

    private String question;
    private String groundTruth;
    private double threshold;

    public RageAssertionCaller(String question, String groundTruth, double threshold) {
        this.question = question;
        this.groundTruth = groundTruth;
        this.threshold = threshold;
    }

    public RageAssertionCaller assertFaithfulness() {
        rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then()
                .assertFaithfulness(threshold);
        return this;
    }

    public RageAssertionCaller assertAnswerCorrectness() {
        rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then()
                .assertAnswerCorrectness(threshold);
        return this;
    }

    public RageAssertionCaller assertAnswerRelevance() {
        rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then()
                .assertAnswerRelevance(threshold);
        return this;
    }

    public RageAssertionCaller assertSemanticSimilarity() {
        rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then()
                .assertSemanticSimilarity(threshold);
        return this;
    }

    public RageAssertionCaller assertBleuScore() {
        rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then()
                .assertBleuScore(threshold);
        return this;
    }

    public RageAssertionCaller assertRougeScore(RougeScoreEvaluator.RougeType rougeType,
            RougeScoreEvaluator.MeasureType measureType) {
        rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then()
                .assertRougeScore(threshold, rougeType, measureType);
        return this;
    }

    private String callAIService() {
        return aiService.getAiAnswer(question);
    }
}
