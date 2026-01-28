package ada.modulo3.web2.rickmorty.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

/**
 * Mapper para ConflictException.
 * Retorna HTTP 409 Conflict com mensagem JSON.
 */
@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("message", exception.getMessage()))
                .build();
    }
}
