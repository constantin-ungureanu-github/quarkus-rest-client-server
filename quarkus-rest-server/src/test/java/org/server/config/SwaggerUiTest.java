package org.server.config;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SwaggerUiTest {

    @Test
    void testSwaggerUi() {
        given()
            .when()
                .get("/api")
            .then()
                .statusCode(200)
                .body(containsString("/openapi"));
    }
}
