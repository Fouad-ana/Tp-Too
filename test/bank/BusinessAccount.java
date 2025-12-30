package bank;

import bank.domain.Account;
import bank.errors.*;

public class BusinessAccount extends Account {
    
    private double creditLimit;
    private double interestRate;

    public BusinessAccount(String id, double balance, double creditLimit, double interestRate) {
        super(id, balance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    // 👇 AJOUTE CETTE MÉTHODE POUR RÉPARER LE TEST 👇
    public void applyInterest() {
        this.balance += this.balance * interestRate;
    }

    // --- TEMPLATE METHOD ---
    @Override
    protected void checkSpecificRules(double amount) {
        if (this.balance - amount < -creditLimit) {
            throw new TransferException("Limite Business dépassée", new Exception("Règle Business"));
        }
    }

    @Override
    protected void applyWithdraw(double amount) {
        this.balance -= amount;
    }
}