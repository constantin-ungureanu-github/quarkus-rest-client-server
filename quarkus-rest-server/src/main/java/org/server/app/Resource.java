package org.server.app;

import java.io.File;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@Path("/test")
@Slf4j
public class Resource {

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public DownloadFormData test(File file) {
        log.info("Received file {}", file.getName());
        return DownloadFormData.builder()
                .fileName("output_file")
                .file(file)
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Jacksonized
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DownloadFormData {
        @RestForm("fileName")
        @PartType(MediaType.TEXT_PLAIN)
        String fileName;

        @RestForm("file")
        @PartType(MediaType.APPLICATION_OCTET_STREAM)
        File file;
    }
}
