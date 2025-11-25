package org.client.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import jakarta.inject.Inject;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.client.helper.ServerResource;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(value = ServerResource.class, restrictToAnnotatedClass = true)
class ServiceTest {

    @Inject
    Service service;

    private static final String INPUT_FILE = "src/test/resources/zip_2MB.zip";
    private static final String INPUT_SMALL_FILE = "src/test/resources/file_example_ODS_5000.ods";


    @Test
    void testService() {
        final File inputFile = new File(INPUT_FILE);

        final String result = service.test(inputFile);

        assertEquals("ok", result);
    }

    @Test
    void testServiceSmall() {
        final File inputFile = new File(INPUT_SMALL_FILE);

        final String result = service.test(inputFile);

        assertEquals("ok", result);
    }
}
