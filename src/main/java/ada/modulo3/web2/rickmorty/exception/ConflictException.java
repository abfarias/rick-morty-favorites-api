package ada.modulo3.web2.rickmorty.exception;

/**
 * Exceção lançada quando há tentativa de criar recurso duplicado.
 * Mapeada para HTTP 409 Conflict.
 */
public class ConflictException extends RuntimeException {
    
    public ConflictException(String message) {
        super(message);
    }
    
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
