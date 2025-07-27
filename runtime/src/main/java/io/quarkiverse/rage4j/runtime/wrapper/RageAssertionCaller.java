package io.quarkiverse.rage4j.runtime.wrapper;

public class RageAssertionCaller {
    private String question;
    private String groundTruth;
    private double threshold;

    public RageAssertionCaller(String question, String groundTruth, double threshold) {
        this.question = question;
        this.groundTruth = groundTruth;
        this.threshold = threshold;
    }

    public int assertFaithfulness() {

    }

    public int assertAnswerCorrectness() {

    }

    public int assertAnswerRelevance() {

    }

    public int assertSemanticSimilarity() {

    }

    private String getAiAnswer() {

    }
}
