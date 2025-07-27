package io.quarkiverse.rage4j.runtime.wrapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RageAssert {

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

    public RageAssertionCaller threshold(double threshold) {
        return new RageAssertionCaller(question, groundTruth, threshold);
    }
}
