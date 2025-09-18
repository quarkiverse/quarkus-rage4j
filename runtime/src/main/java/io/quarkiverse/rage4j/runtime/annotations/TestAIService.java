package io.quarkiverse.rage4j.runtime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import io.quarkiverse.rage4j.runtime.junitextension.Rage4jTestExtension;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(Rage4jTestExtension.class)
public @interface TestAIService {
}
