package bank;

import bank.tx.TransactionType; // Import nécessaire

public final class SavingsAccount extends Account {

    private final double interestRate;

    public SavingsAccount(String id, double initial, double interestRate) {
        super(id, initial);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount, TransactionType type) {
        // 1. Vérifier le montant
        if (amount <= 0) {
            throw new BusinessRuleViolation("Le montant doit être > 0");
        }
        
        // 2. Vérifier le solde (Pas de découvert autorisé)
        if (balance - amount < 0) {
            throw new BusinessRuleViolation("Solde insuffisant (Épargne)");
        }
        
        // 3. Retirer et Enregistrer avec le bon type
        this.balance -= amount;
        recordTransaction(type, amount);
    }

    // Méthode pour les intérêts
    public void applyInterest() {
        double interest = this.balance * this.interestRate;
        this.balance += interest;
        recordTransaction(TransactionType.INTEREST, interest);
    }
}