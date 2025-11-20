 package bank;

import bank.tx.Transaction;
import bank.tx.TransactionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {

    protected final String accountNumber;
    protected double balance;
    
    // Liste pour stocker l'historique (TP2)
    private final List<Transaction> transactions = new ArrayList<>();

    protected Account(String accountNumber, double initial) {
        this.accountNumber = accountNumber;
        this.balance = initial;
    }

    // Méthode interne pour créer et ajouter une transaction
    protected void recordTransaction(TransactionType type, double amount) {
        Transaction tx = new Transaction(type, amount, this.balance);
        transactions.add(tx);
    }

    // --- MÉTHODES DE DÉPÔT ---

    // Version 1 (Complète) : Permet de préciser le type (ex: TRANSFER_IN)
    public final void deposit(double amount, TransactionType type) {
        if (amount <= 0) {
            throw new BusinessRuleViolation("Montant invalide (doit être > 0)");
        }
        this.balance += amount;
        recordTransaction(type, amount);
    }

    // Version 2 (Simplifiée) : Par défaut, c'est un DEPOSIT
    public final void deposit(double amount) {
        deposit(amount, TransactionType.DEPOSIT);
    }

    // --- MÉTHODES DE RETRAIT ---

    // Version 1 (Abstraite) : Force les enfants à gérer le type de transaction
    public abstract void withdraw(double amount, TransactionType type);

    // Version 2 (Simplifiée) : Par défaut, c'est un WITHDRAW
    public void withdraw(double amount) {
        withdraw(amount, TransactionType.WITHDRAW);
    }

    // --- ACCESSEURS & HISTORIQUE ---

    public final String getAccountNumber() { 
        return accountNumber; 
    }
    
    public final double getBalance() { 
        return balance; 
    }

    // Retourne une copie non modifiable de la liste (Sécurité)
    public List<Transaction> history() {
        return Collections.unmodifiableList(transactions);
    }
}