package bank;

import bank.tx.TransactionType;

public final class BusinessAccount extends Account {
    
    private final double creditLimit;
    private final double interestRate;

    public BusinessAccount(String id, double initial, double creditLimit, double interestRate) {
        super(id, initial);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount, TransactionType type) {
        if (amount <= 0) throw new BusinessRuleViolation("Montant <= 0");
        // Règle hybride : découvert autorisé comme CreditAccount
        if (balance - amount < -creditLimit) {
            throw new BusinessRuleViolation("Limite de crédit dépassée");
        }
        this.balance -= amount;
        recordTransaction(type, amount);
    }

    public void applyInterest() {
        // Règle spéciale : intérêts seulement si solde POSITIF
        if (this.balance > 0) {
            double interest = this.balance * this.interestRate;
            this.balance += interest;
            recordTransaction(TransactionType.INTEREST, interest);
        }
    }
}