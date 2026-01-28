package ada.modulo3.web2.rickmorty.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

/**
 * Mapper para BadRequestException.
 * Retorna HTTP 400 Bad Request com mensagem JSON.
 */
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message", exception.getMessage()))
                .build();
    }
}
