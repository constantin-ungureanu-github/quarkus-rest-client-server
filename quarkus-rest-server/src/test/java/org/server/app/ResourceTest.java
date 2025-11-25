package org.server.app;

import static io.restassured.RestAssured.given;
import java.io.File;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ResourceTest {

    private static final String INPUT_FILE = "src/test/resources/zip_2MB.zip";

    @Test
    void testRest() {
        final File inputFile = new File(INPUT_FILE);
        given()
            .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM)
            .body(inputFile)
            .when()
                .post("/test")
            .then()
                .statusCode(200);
    }
}
