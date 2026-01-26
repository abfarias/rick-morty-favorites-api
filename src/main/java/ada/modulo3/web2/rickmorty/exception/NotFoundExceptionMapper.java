package ada.modulo3.web2.rickmorty.exception;

import ada.modulo3.web2.rickmorty.exception.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        // Retorna HTTP 404 com uma mensagem JSON
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("message", exception.getMessage()))
                .build();
    }
}