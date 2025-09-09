package io.quarkiverse.rage4j.runtime.wrapper;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkiverse.rage4j.runtime.TestClassNameRecorder;

@ApplicationScoped
public class RageAssert {

    @Inject
    TestClassNameRecorder testClassNameRecorder;

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
        saveTestClassName();
        return new RageAssertionCaller(question, groundTruth, threshold);
    }

    private void saveTestClassName() {
        String testClassName = StackWalker
                .getInstance(RETAIN_CLASS_REFERENCE)
                .getCallerClass()
                .getName();
        testClassNameRecorder.setClassName(testClassName);
    }
}
