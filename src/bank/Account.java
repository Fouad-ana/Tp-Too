package bank;

public abstract class Account {

    // Attributs protected pour que les enfants (Savings/Credit) puissent les voir
    protected final String accountNumber;
    protected double balance;

    // Constructeur
    protected Account(String accountNumber, double initial) {
        this.accountNumber = accountNumber;
        this.balance = initial;
    }

    // Méthode finale : on ne peut pas la modifier dans les sous-classes
    public final void deposit(double amount) {
        // Règle : le montant doit être positif
        if (amount <= 0) {
            throw new BusinessRuleViolation("Le montant du dépôt doit être positif !");
        }
        this.balance += amount;
    }

    // Méthode abstraite : les enfants DEVRONT écrire leur propre logique de retrait
    public abstract void withdraw(double amount);

    // Getters (lecture seule)
    public final String getAccountNumber() {
        return accountNumber;
    }

    public final double getBalance() {
        return balance;
    }
}