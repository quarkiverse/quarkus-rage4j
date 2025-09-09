package io.quarkiverse.rage4j.runtime;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.runtime.annotations.Recorder;

@Recorder
@ApplicationScoped
public class TestClassNameRecorder {

    private String className;

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
