package org.server.client;

import java.io.File;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/**
 * Test rest client.
 */
@Path("/test")
@RegisterRestClient(configKey = "api-server")
@RegisterClientHeaders
public interface ServerApi {

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    MultipartResponse test(File file);

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	class MultipartResponse {
	    @FormParam("fileName")
        @PartType(MediaType.TEXT_PLAIN)
        String fileName;

		@RestForm
		@PartType(MediaType.APPLICATION_OCTET_STREAM)
		File file;
	}
}
