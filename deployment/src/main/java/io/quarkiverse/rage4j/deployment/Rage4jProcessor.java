package io.quarkiverse.rage4j.deployment;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.BooleanSupplier;

import jakarta.enterprise.context.ApplicationScoped;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

import io.quarkiverse.rage4j.deployment.builditem.AIServiceBuildItem;
import io.quarkiverse.rage4j.deployment.config.Rage4jConfiguration;
import io.quarkiverse.rage4j.runtime.AIServiceHolder;
import io.quarkiverse.rage4j.runtime.TestClassNameRecorder;
import io.quarkiverse.rage4j.runtime.wrapper.RageAssert;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.IsTest;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.BuildSteps;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.TestClassBeanBuildItem;

@BuildSteps(onlyIf = IsTest.class)
class Rage4jProcessor {

    private static final String FEATURE = "rage4j";
    private static final String AI_SERVICE_ANNOTATION = "io.quarkiverse.rage4j.runtime.annotations.TestAIService";

    Rage4jConfiguration rage4jConfiguration;

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    AIServiceBuildItem buildAIServiceBuildItem(TestClassNameRecorder testClassNameRecorder,
            CombinedIndexBuildItem combinedIndexBuildItem) {
        IndexView index = combinedIndexBuildItem.getIndex();

        DotName testAiServiceAnnotation = DotName.createSimple(AI_SERVICE_ANNOTATION);
        Collection<AnnotationInstance> annotationInstances = index.getAnnotations(testAiServiceAnnotation);

        return annotationInstances.stream()
                .filter(a -> isCorrectTestClass(a, testClassNameRecorder.getClassName()))
                .map(this::getAiCallMethod)
                .toList()
                .get(0);
    }

    @BuildStep
    void buildRageAssert(Rage4jConfiguration rage4jConfiguration, BuildProducer<SyntheticBeanBuildItem> beans,
            AIServiceBuildItem aiServiceBuildItem) {
        beans.produce(SyntheticBeanBuildItem.configure(AIServiceHolder.class)
                .scope(ApplicationScoped.class)
                .supplier(() -> new AIServiceHolder(aiServiceBuildItem.getAiServiceClass(), aiServiceBuildItem.getMethodName(),
                        rage4jConfiguration.apiKey()))
                .done());
    }

    @BuildStep
    AdditionalBeanBuildItem buildRageAssertBean() {
        return AdditionalBeanBuildItem.builder().addBeanClass(RageAssert.class).build();
    }

    @BuildStep
    AdditionalBeanBuildItem buildTestClassRecorderBean() {
        return AdditionalBeanBuildItem.builder().addBeanClass(TestClassNameRecorder.class).build();
    }

    private boolean isCorrectTestClass(AnnotationInstance annotationInstance, String actuallTestClassName) {
        String className = annotationInstance.target().asClass().toString();
        return actuallTestClassName.equals(className);
    }

    private AIServiceBuildItem getAiCallMethod(AnnotationInstance annotationInstance) {
        String aiServiceClassName = annotationInstance.value().asClass().name().toString();
        try {
            return getMethodFromClassName(aiServiceClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private AIServiceBuildItem getMethodFromClassName(String aiServiceClassName) throws ClassNotFoundException {
        Class<?> aiServiceClass = Class.forName(aiServiceClassName);
        Method aiCallMethod = aiServiceClass.getMethods()[0];
        return new AIServiceBuildItem(aiCallMethod, aiServiceClass);
    }

    static class IsRageTest implements BooleanSupplier {
        CombinedIndexBuildItem combinedIndexBuildItem;
        TestClassBeanBuildItem testClassBeanBuildItem;

        @Override
        public boolean getAsBoolean() {
            return hasRageAnnotation();
        }

        private boolean hasRageAnnotation() {
            IndexView index = combinedIndexBuildItem.getIndex();
            DotName testAiServiceAnnotation = DotName.createSimple(AI_SERVICE_ANNOTATION);

            return index.getAnnotations(testAiServiceAnnotation)
                    .stream()
                    .map(a -> a.target().asClass().toString())
                    .anyMatch(className -> className.equals(testClassBeanBuildItem.getTestClassName()));
        }
    }
}
