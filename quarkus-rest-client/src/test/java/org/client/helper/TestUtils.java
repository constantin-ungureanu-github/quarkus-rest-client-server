package org.client.helper;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import lombok.experimental.UtilityClass;

/**
 * Test utility helper class.
 */
@UtilityClass
public class TestUtils {
    private static final int HTTP_CLIENT_TIMEOUT = 60000;
    private static final int HTTP_CLIENT_READ_TIMEOUT = 180000;

    public static final RestAssuredConfig config = RestAssuredConfig.config().
            httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", HTTP_CLIENT_TIMEOUT)           // Connection timeout
                    .setParam("http.connection-manager.timeout", HTTP_CLIENT_TIMEOUT)   // Connection request timeout
                    .setParam("http.socket.timeout", HTTP_CLIENT_READ_TIMEOUT));        // Read timeout
}
