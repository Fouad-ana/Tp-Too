package bank.domain;

import bank.errors.*;

public class CreditAccount extends Account {
    
    private double creditLimit;

    public CreditAccount(String id, double balance, double creditLimit) {
        super(id, balance);
        this.creditLimit = creditLimit;
    }

    // --- TEMPLATE METHOD IMPLEMENTATION ---

    @Override
    protected void checkSpecificRules(double amount) {
        // Règle du Compte Crédit : On ne doit pas dépasser la limite de découvert
        // Ex: Solde 0, Limite 100, Retrait 150 -> -150 < -100 (INTERDIT)
        if (this.balance - amount < -creditLimit) {
            throw new TransferException("Limite de crédit dépassée", new Exception("Règle Crédit"));
        }
    }

    @Override
    protected void applyWithdraw(double amount) {
        // Action simple : on enlève l'argent
        this.balance -= amount;
    }
    public double getCreditLimit() {
        return this.creditLimit;
    }
}