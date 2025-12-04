package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bank.strategies.*; // Pour les frais (Ex 1)
import bank.tx.Transaction;
import bank.tx.TransactionType;
import bank.errors.*; // Pour les exceptions (TP4)

public abstract class Account {

    // --- STRATÉGIE (Exercice 1) ---
    private FeePolicy feePolicy = new NoFeePolicy(); 

    public void setFeePolicy(FeePolicy feePolicy) {
        this.feePolicy = feePolicy;
    }
    
    protected FeePolicy getFeePolicy() {
        return this.feePolicy;
    }

    // --- ATTRIBUTS ---
    protected final String accountNumber;
    protected double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    // --- CONSTRUCTEUR ---
    protected Account(String accountNumber, double initial) {
        this.accountNumber = accountNumber;
        this.balance = initial;
    }

    // --- OUTILS ---
    public String getId() { return this.accountNumber; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    public List<Transaction> history() {
        return Collections.unmodifiableList(transactions);
    }

    protected void recordTransaction(TransactionType type, double amount) {
        Transaction tx = new Transaction(type, amount, this.balance);
        transactions.add(tx);
    }

    // --- DÉPÔT ---
    public final void deposit(double amount) {
        if (amount <= 0) throw new InvalidAmountException("Montant invalide");
        this.balance += amount;
        recordTransaction(TransactionType.DEPOSIT, amount);
    }

    // =========================================================
    // === PATTERN TEMPLATE METHOD (Exercice 2) ===
    // =========================================================

    /**
     * Méthode FINALE : C'est le "Patron". 
     * Elle définit l'algorithme strict du retrait et personne ne peut la changer.
     */
    public final void withdraw(double amount) {
        // 1. Validation de base
        if (amount <= 0) {
            throw new InvalidAmountException("Le montant doit être positif");
        }

        // 2. Validation spécifique au type de compte (Méthode abstraite)
        // (Ex: Vérifier le découvert pour CreditAccount, ou le solde pour Savings)
        checkSpecificRules(amount);

        // 3. Application du retrait (Méthode abstraite)
        // (Ex: balance -= amount)
        applyWithdraw(amount);
        
        // 4. Enregistrement de la transaction principale
        recordTransaction(TransactionType.WITHDRAW, amount);

        // 5. Application des frais (STRATEGY PATTERN - Exercice 1)
        double fee = feePolicy.computeFee(amount);
        if (fee > 0) {
            this.balance -= fee;
            recordTransaction(TransactionType.BANK_FEE, fee);
        }

        // 6. Logger (TP4)
        Logger.logInfo("Withdraw OK: " + amount + " (Frais: " + fee + ") sur " + accountNumber);
    }

    // --- MÉTHODES ABSTRAITES (Hooks) ---
    // Les sous-classes DOIVENT implémenter ces méthodes pour préciser LEUR comportement
    
    protected abstract void checkSpecificRules(double amount);
    
    protected abstract void applyWithdraw(double amount);
}