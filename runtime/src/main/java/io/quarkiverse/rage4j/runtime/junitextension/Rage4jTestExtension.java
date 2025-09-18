package io.quarkiverse.rage4j.runtime.junitextension;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.quarkiverse.rage4j.runtime.annotations.TestAIService;
import io.quarkiverse.rage4j.runtime.holder.AIMethodHolder;
import io.quarkus.arc.Arc;

public class Rage4jTestExtension implements BeforeEachCallback, Extension {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AIMethodHolder aiMethodHolder = getAiMethodHolderInstance();
        getAiServiceMethod(context).ifPresent(aiMethodHolder::setMethod);
        aiMethodHolder.setAiServiceClass(getTestClassInstance(context));
    }

    private AIMethodHolder getAiMethodHolderInstance() {
        return Arc.container().instance(AIMethodHolder.class).get();
    }

    private Object getTestClassInstance(ExtensionContext context) {
        return Arc.container().instance(context.getTestInstance().get().getClass()).get();
    }

    private Optional<Method> getAiServiceMethod(ExtensionContext context) {
        return context.getTestInstance()
                .flatMap(this::getExistingTestMethod);
    }

    private Optional<Method> getExistingTestMethod(Object testInstance) {
        return Arrays.stream(testInstance.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(TestAIService.class))
                .findFirst();
    }
}
