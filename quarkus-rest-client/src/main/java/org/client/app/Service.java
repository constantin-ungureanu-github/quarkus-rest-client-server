package org.client.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.server.client.ServerApi;
import org.server.client.ServerApi.MultipartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Singleton
@Slf4j
public class Service {

    @RestClient
    private ServerApi testApi;

    @ConfigProperty(name = "iteration.number", defaultValue = "1000")
    private Integer iterationNumber;

    public String test(final File file) {
//        IntStream.range(0, iterationNumber)
////            .parallel()
//            .forEach(i -> {
//            try {
//                final MultipartResponse multipartResponse = testApi.test(file);
//
//                if (multipartResponse != null && multipartResponse.getFile() != null) {
//                    Files.delete(multipartResponse.getFile().toPath());
//                }
//                log.info("Processed file {} # {}", file.getName(), i);
//            } catch (IOException e) {
//                log.error("IO exception: {}", e.getMessage(), e);
//            } catch (RuntimeException e) {
//                log.error("Runtime exception: {}", e.getMessage(), e);
//            }
//        });

        try {
            for (int i = 0; i < iterationNumber; i++) {
                final MultipartResponse multipartResponse = testApi.test(file);

                if (multipartResponse != null && multipartResponse.getFile() != null) {
                    Files.delete(multipartResponse.getFile().toPath());
                }
                log.info("Processed file {} # {}", file.getName(), i);
            }
        } catch (IOException e) {
            log.error("IO exception: {}", e.getMessage(), e);
            return "NOK";
        } catch (RuntimeException e) {
            log.error("Runtime exception: {}", e.getMessage(), e);
            return "NOK";
        }

        return "OK";
    }
}
