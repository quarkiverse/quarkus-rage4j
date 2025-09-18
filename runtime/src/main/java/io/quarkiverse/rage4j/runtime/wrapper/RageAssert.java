package io.quarkiverse.rage4j.runtime.wrapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RageAssert {

    @Inject
    RageCaller rageCaller;

    private String question;
    private String groundTruth;

    public RageAssert question(String question) {
        this.question = question;
        return this;
    }

    public RageAssert groundTruth(String groundTruth) {
        this.groundTruth = groundTruth;
        return this;
    }

    public RageCaller threshold(double threshold) {
        rageCaller.setThreshold(threshold);
        rageCaller.setQuestion(question);
        rageCaller.setGroundTruth(groundTruth);
        return rageCaller;
    }
}
