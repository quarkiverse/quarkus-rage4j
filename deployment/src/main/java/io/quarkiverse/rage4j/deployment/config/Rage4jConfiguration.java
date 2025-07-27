package io.quarkiverse.rage4j.deployment.config;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.rage4j")
// ToDo: Test if it works with ConfigPhase.Build_TIME only
// ToDo: Add documentation
@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public interface Rage4jConfiguration {

    String apiKey();
}
