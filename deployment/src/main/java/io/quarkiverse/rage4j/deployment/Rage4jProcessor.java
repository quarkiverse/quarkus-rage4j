package io.quarkiverse.rage4j.deployment;

import static io.quarkiverse.rage4j.deployment.ObjectBuildUtils.buildObject;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.BooleanSupplier;

import jakarta.enterprise.context.ApplicationScoped;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

import io.quarkiverse.rage4j.deployment.builditem.AIServiceBuildItem;
import io.quarkiverse.rage4j.deployment.config.Rage4jConfiguration;
import io.quarkiverse.rage4j.deployment.recorder.Rage4jRecorder;
import io.quarkiverse.rage4j.runtime.AIServiceHolder;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.IsTest;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.BuildSteps;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.TestClassBeanBuildItem;

@BuildSteps(onlyIf = { IsTest.class, Rage4jProcessor.IsRageTest.class })
class Rage4jProcessor {

    private static final String FEATURE = "rage4j";
    private static final String AI_SERVICE_ANNOTATION = "io.quarkiverse.rage4j.runtime.annotations.TestAIService";

    Rage4jConfiguration rage4jConfiguration;

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AIServiceBuildItem buildAIServiceBuildItem(TestClassBeanBuildItem testClassBeanBuildItem,
            CombinedIndexBuildItem combinedIndexBuildItem) {
        IndexView index = combinedIndexBuildItem.getIndex();

        DotName testAiServiceAnnotation = DotName.createSimple(AI_SERVICE_ANNOTATION);
        Collection<AnnotationInstance> annotationInstances = index.getAnnotations(testAiServiceAnnotation);

        return annotationInstances.stream()
                .filter(a -> isCorrectTestClass(a, testClassBeanBuildItem))
                .map(this::getAiCallMethod)
                .toList()
                .getFirst();
    }

    @BuildStep
    void BuildRageAssert(Rage4jConfiguration rage4jConfiguration, BuildProducer<SyntheticBeanBuildItem> beans,
            Rage4jRecorder rage4jRecorder, AIServiceBuildItem aiServiceBuildItem) {
        beans.produce(SyntheticBeanBuildItem.configure(AIServiceHolder.class)
                .scope(ApplicationScoped.class)
                .runtimeValue(rage4jRecorder.createAIServiceHolder(
                        buildObject(aiServiceBuildItem.getAiServiceClass()),
                        aiServiceBuildItem.getMethodName(),
                        rage4jConfiguration.apiKey()))
                .done());
    }

    private boolean isCorrectTestClass(AnnotationInstance annotationInstance, TestClassBeanBuildItem testClassBeanBuildItem) {
        String className = annotationInstance.target().asClass().toString();
        String executedTestClassName = testClassBeanBuildItem.getTestClassName();
        return executedTestClassName.equals(className);
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

    // ToDo: Vielleicht in externe Klasse auslagern?
    static class IsRageTest implements BooleanSupplier {

        private final CombinedIndexBuildItem combinedIndexBuildItem;
        private final TestClassBeanBuildItem testClassBeanBuildItem;

        public IsRageTest(CombinedIndexBuildItem combinedIndexBuildItem, TestClassBeanBuildItem testClassBeanBuildItem) {
            this.combinedIndexBuildItem = combinedIndexBuildItem;
            this.testClassBeanBuildItem = testClassBeanBuildItem;
        }

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
