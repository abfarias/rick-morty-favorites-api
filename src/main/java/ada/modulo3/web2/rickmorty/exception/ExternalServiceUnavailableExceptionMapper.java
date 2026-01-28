package ada.modulo3.web2.rickmorty.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

/**
 * Mapper para ExternalServiceUnavailableException.
 * Retorna HTTP 503 Service Unavailable com mensagem JSON.
 */
@Provider
public class ExternalServiceUnavailableExceptionMapper implements ExceptionMapper<ExternalServiceUnavailableException> {

    @Override
    public Response toResponse(ExternalServiceUnavailableException exception) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(Map.of("message", exception.getMessage()))
                .build();
    }
}
