package ada.modulo3.web2.rickmorty.exception;

/**
 * Exceção lançada quando há erro de validação ou parâmetros inválidos.
 * Mapeada para HTTP 400 Bad Request.
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
