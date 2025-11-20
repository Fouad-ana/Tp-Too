package bank.tx;

import java.time.LocalDateTime;

// "final" pour empêcher l'héritage, immuable
public final class Transaction {

    // Attributs "final" pour l'immutabilité
    private final LocalDateTime date;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter; // Solde après l'opération

    // Constructeur
    public Transaction(TransactionType type, double amount, double balanceAfter) {
        this.date = LocalDateTime.now(); // Date/heure actuelle
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    // Getters (pas de setters !)
    public LocalDateTime getDate() { return date; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }

    @Override
    public String toString() {
        return String.format("[%s] %s %.2f -> Solde: %.2f", 
                date, type, amount, balanceAfter);
    }
}