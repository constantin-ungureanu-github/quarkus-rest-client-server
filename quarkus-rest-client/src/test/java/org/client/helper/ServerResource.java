package org.client.helper;

import static org.testcontainers.containers.wait.strategy.WaitAllStrategy.Mode.WITH_MAXIMUM_OUTER_TIMEOUT;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.utility.DockerImageName;
import lombok.extern.slf4j.Slf4j;

/**
 * Test helper class to start rest server container.
 */
@Slf4j
public class ServerResource implements QuarkusTestResourceLifecycleManager, DevServicesContext.ContextAware {
    private static final String TEST_SERVER_IMAGE = "test/quarkus-rest-server:0.0.1-SNAPSHOT";
    private static final Integer TEST_SERVER_PORT = 8080;

    private final GenericContainer<?> testServer = new GenericContainer<>(DockerImageName.parse(TEST_SERVER_IMAGE));
    private Optional<String> containerNetworkId;

    @Override
    public void setIntegrationTestContext(DevServicesContext context) {
        containerNetworkId = context.containerNetworkId();
    }

    @Override
    public Map<String, String> start() {
        String serverUrl;

        if (containerNetworkId.isPresent()) {
            final String network = containerNetworkId.get();

            testServer.withNetworkMode(network)
                    .withExposedPorts(TEST_SERVER_PORT)
//                    .withLogConsumer(new Slf4jLogConsumer(log).withSeparateOutputStreams())
                    .waitingFor(new WaitAllStrategy(WITH_MAXIMUM_OUTER_TIMEOUT)
                            .withStartupTimeout(Duration.ofSeconds(120))
                            .withStrategy(Wait.forLogMessage(".*Profile prod activated.*\\n", 1)))
                    .start();
            serverUrl = "http://" + testServer.getCurrentContainerInfo().getConfig().getHostName() + ":" + TEST_SERVER_PORT;
        } else {
            final Network network = Network.newNetwork();

            testServer.withNetwork(network)
                    .withNetworkAliases("test")
                    .withExposedPorts(TEST_SERVER_PORT)
//                    .withLogConsumer(new Slf4jLogConsumer(log).withSeparateOutputStreams())
                    .waitingFor(new WaitAllStrategy(WITH_MAXIMUM_OUTER_TIMEOUT)
                            .withStartupTimeout(Duration.ofSeconds(120))
                            .withStrategy(Wait.forLogMessage(".*Profile prod activated.*\\n", 1)))
                    .start();
            serverUrl = "http://" + testServer.getHost() + ":" + testServer.getMappedPort(TEST_SERVER_PORT);
        }

        log.info("TEST SERVER URL: {}", serverUrl);

        final Map<String, String> conf = new HashMap<>();
        conf.put("quarkus.rest-client.test-server.url", serverUrl);
        return conf;
    }

    @Override
    public void stop() {
        if (testServer != null) {
            testServer.stop();
        }

        await()
            .atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(100))
            .until(() -> !testServer.isCreated());
    }
}
