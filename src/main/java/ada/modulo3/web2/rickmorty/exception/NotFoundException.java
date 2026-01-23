package ada.modulo3.web2.rickmorty.exception;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * Mapeada para HTTP 404 Not Found.
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
