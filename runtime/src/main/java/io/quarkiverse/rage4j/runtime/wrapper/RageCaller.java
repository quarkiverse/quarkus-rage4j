package io.quarkiverse.rage4j.runtime.wrapper;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import dev.rage4j.asserts.RageAssert;
import dev.rage4j.asserts.RageAssertTestCaseAssertions;
import dev.rage4j.asserts.openai.OpenAiLLMBuilder;
import io.quarkiverse.rage4j.runtime.AIMethodInvoker;
import io.quarkiverse.rage4j.runtime.holder.AIMethodHolder;
import io.quarkiverse.rage4j.runtime.holder.ApiKeyHolder;

@ApplicationScoped
public class RageCaller {

    @Inject
    AIMethodInvoker aiMethodInvoker;

    @Inject
    ApiKeyHolder apiKeyHolder;

    @Inject
    AIMethodHolder aiMethodHolder;

    private RageAssert rageAssert;
    private String question;
    private String groundTruth;
    private double threshold;

    @PostConstruct
    void initializeRageAssert() {
        String apiKey = apiKeyHolder.getApiKey();
        rageAssert = new OpenAiLLMBuilder().fromApiKey(apiKey);
    }

    public RageCaller assertFaithfulness() {
        prepareAssertion().assertFaithfulness(threshold);
        return this;
    }

    public RageCaller assertAnswerCorrectness() {
        prepareAssertion().assertAnswerCorrectness(threshold);
        return this;
    }

    public RageCaller assertAnswerRelevance() {
        prepareAssertion().assertAnswerRelevance(threshold);
        return this;
    }

    public RageCaller assertSemanticSimilarity() {
        prepareAssertion().assertSemanticSimilarity(threshold);
        return this;
    }

    private RageAssertTestCaseAssertions prepareAssertion() {
        return rageAssert
                .given()
                .groundTruth(groundTruth)
                .question(question)
                .when()
                .answer(callAIService())
                .then();
    }

    private String callAIService() {
        return aiMethodInvoker.getAiAnswer(question);
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setGroundTruth(String groundTruth) {
        this.groundTruth = groundTruth;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
