package io.quarkiverse.rage4j.deployment.config;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.rage4j")
@ConfigRoot(phase = ConfigPhase.BUILD_TIME)
public interface Rage4jConfiguration {
    /**
     * This is a Javadoc
     */
    String apiKey();
}
