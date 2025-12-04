package bank.errors;

public class TransferException extends RuntimeException {
    // Constructeur spécial qui prend un message ET la cause réelle de l'erreur
    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }
}