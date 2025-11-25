package org.client.config;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import java.net.URL;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class HealthChecksTest {

    private static final String ALIVE = """
            {
                "status": "UP",
                "checks": [
                    {
                        "name": "alive",
                        "status": "UP"
                    }
                ]
            }""";

    private static final String READY = """
            {
                "status": "UP",
                "checks": [
                    {
                        "name": "ready",
                        "status": "UP"
                    }
                ]
            }""";

    private static final String STARTED = """
            {
                "status": "UP",
                "checks": [
                    {
                        "name": "started",
                        "status": "UP"
                    }
                ]
            }""";

    @TestHTTPResource(value="/health/live", management=true)
    URL livenessProbeURL;

    @TestHTTPResource(value="/health/ready", management=true)
    URL readinessProbeURL;

    @TestHTTPResource(value="/health/started", management=true)
    URL startupProbeURL;

    @Test
    void testLivenessProbe() {
        given()
            .when()
                .get(livenessProbeURL)
            .then()
                .statusCode(200)
                .body(is(ALIVE));
    }

    @Test
    void testReadinessCheck() {
        given()
            .when()
                .get(readinessProbeURL)
            .then()
                .statusCode(200)
                .body(is(READY));
    }

    @Test
    void testStartupCheck() {
        given()
            .when()
                .get(startupProbeURL)
            .then()
                .statusCode(200)
                .body(is(STARTED));
    }
}
