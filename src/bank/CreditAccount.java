package bank;

import bank.tx.TransactionType; // Import nécessaire

public final class CreditAccount extends Account {

    private final double creditLimit;

    public CreditAccount(String id, double initial, double creditLimit) {
        super(id, initial);
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdraw(double amount, TransactionType type) {
        if (amount <= 0) {
            throw new BusinessRuleViolation("Le montant doit être > 0");
        }

        // Vérifier la limite de crédit
        if (balance - amount < -creditLimit) {
            throw new BusinessRuleViolation("Limite de crédit dépassée !");
        }

        this.balance -= amount;
        recordTransaction(type, amount);
    }
}