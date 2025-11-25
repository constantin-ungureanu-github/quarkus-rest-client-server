package org.server.config;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class OpenApiTest {

    @Test
    void testOpenApi() {
        given()
            .when()
                .get("/openapi")
            .then()
                .statusCode(200)
                .body(containsString("openapi"));
    }
}
