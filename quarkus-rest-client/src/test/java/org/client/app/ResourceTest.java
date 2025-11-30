package org.client.app;

import static io.restassured.RestAssured.given;
import java.io.File;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.client.helper.ServerResource;
import org.client.helper.TestUtils;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(value = ServerResource.class, restrictToAnnotatedClass = true)
class ResourceTest {

    private static final String INPUT_FILE = "src/test/resources/zip_2MB.zip";

    @Test
    void testRest() {
        final File inputFile = new File(INPUT_FILE);
        given().config(TestUtils.config)
            .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM)
            .body(inputFile)
            .when()
                .post("/test")
            .then()
                .statusCode(200);
    }
}
