package bank;

import bank.errors.*; // Pour les exceptions

public class SavingsAccount extends Account {
    
    private double interestRate;

    public SavingsAccount(String id, double balance, double interestRate) {
        super(id, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        this.balance += this.balance * interestRate;
    }

    // --- TEMPLATE METHOD IMPLEMENTATION ---

    @Override
    protected void checkSpecificRules(double amount) {
        // Règle du Compte Épargne : On ne peut pas être à découvert
        if (this.balance < amount) {
            // On utilise l'exception existante (ou BusinessRuleViolation si tu l'as gardée)
            throw new TransferException("Solde insuffisant pour le retrait", new Exception("Règle Epargne"));
        }
    }

    @Override
    protected void applyWithdraw(double amount) {
        // Action simple : on enlève l'argent
        this.balance -= amount;
    }
    public double getInterestRate() {
        return this.interestRate;
    }
}
