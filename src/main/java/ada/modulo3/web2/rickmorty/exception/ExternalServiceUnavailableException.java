package ada.modulo3.web2.rickmorty.exception;

/**
 * Exceção lançada quando a API externa está indisponível.
 * Mapeada para HTTP 503 Service Unavailable.
 */
public class ExternalServiceUnavailableException extends RuntimeException {
    
    public ExternalServiceUnavailableException(String message) {
        super(message);
    }
    
    public ExternalServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
