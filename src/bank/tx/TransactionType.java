package bank.tx;

public enum TransactionType {
    DEPOSIT,        // Dépôt
    WITHDRAW,       // Retrait
    TRANSFER_IN,    // Virement reçu
    TRANSFER_OUT,   // Virement émis
    INTEREST        // Intérêts
}