package org.client.config;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;

/**
 * Handle lifecycle events of the application.
 */
@ApplicationScoped
@Slf4j
public class AdapterApplicationLifecycle {

    @Startup
    void onStart() {
        log.info("The rest client application is starting up...");
    }

    @Shutdown
    void onStop() {
        log.info("The rest client application is shutting down...");
    }
}
