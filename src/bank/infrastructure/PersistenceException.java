package bank.infrastructure;

// Cette exception sert à masquer les erreurs techniques (IOException, SQL, etc.)
public class PersistenceException extends RuntimeException {
    
    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}