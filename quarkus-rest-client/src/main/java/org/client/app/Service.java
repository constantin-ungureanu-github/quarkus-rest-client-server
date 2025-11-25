package org.client.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.IntStream;
import jakarta.inject.Singleton;
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

    public String test(final File file) {
        IntStream.range(0, 1000)
//            .parallel()
            .forEach(i -> {
            try {
                final MultipartResponse multipartResponse = testApi.test(file);

                if (multipartResponse != null && multipartResponse.getFile() != null) {
                    Files.delete(multipartResponse.getFile().toPath());
                }
                log.info("Processed file {}", i);
            } catch (IOException e) {
                log.error("IO exception: {}", e.getMessage(), e);
            } catch (RuntimeException e) {
                log.error("Runtime exception: {}", e.getMessage(), e);
            }
        });

        return "ok";
    }
}
