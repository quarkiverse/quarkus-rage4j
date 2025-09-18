package io.quarkiverse.rage4j.deployment;

import io.quarkiverse.rage4j.deployment.config.Rage4jConfiguration;
import io.quarkiverse.rage4j.runtime.AIMethodInvoker;
import io.quarkiverse.rage4j.runtime.holder.AIMethodHolder;
import io.quarkiverse.rage4j.runtime.holder.ApiKeyHolder;
import io.quarkiverse.rage4j.runtime.recorder.ApiKeyHolderRecorder;
import io.quarkiverse.rage4j.runtime.wrapper.RageAssert;
import io.quarkiverse.rage4j.runtime.wrapper.RageCaller;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.IsTest;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.BuildSteps;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;

@BuildSteps(onlyIf = IsTest.class)
class Rage4jProcessor {
    private static final String FEATURE = "rage4j";

    Rage4jConfiguration rage4jConfiguration;

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem buildApiKeyHolderBean() {
        return AdditionalBeanBuildItem
                .builder()
                .addBeanClass(ApiKeyHolder.class)
                .build();
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void setApiKey(ApiKeyHolderRecorder apiKeyHolderRecorder,
            Rage4jConfiguration rage4jConfiguration) {
        apiKeyHolderRecorder.initApiKey(rage4jConfiguration.apiKey());
    }

    @BuildStep
    AdditionalBeanBuildItem buildAIMethodHolderBean() {
        return AdditionalBeanBuildItem
                .builder()
                .addBeanClass(AIMethodHolder.class)
                .build();
    }

    @BuildStep
    AdditionalBeanBuildItem buildRageAssertBean() {
        return AdditionalBeanBuildItem
                .builder()
                .addBeanClass(RageAssert.class)
                .build();
    }

    @BuildStep
    AdditionalBeanBuildItem buildRageCallerBean() {
        return AdditionalBeanBuildItem
                .builder()
                .addBeanClass(RageCaller.class)
                .build();
    }

    @BuildStep
    AdditionalBeanBuildItem buildAIMethodInvokerBean() {
        return AdditionalBeanBuildItem
                .builder()
                .addBeanClass(AIMethodInvoker.class)
                .build();
    }
}
