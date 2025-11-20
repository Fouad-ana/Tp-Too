package bank;

public final class CreditAccount extends Account {

    private final double creditLimit;

    public CreditAccount(String id, double initial, double creditLimit) {
        super(id, initial);
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new BusinessRuleViolation("Le montant doit être positif !");
        }

        // Règle : Le solde ne doit pas descendre en dessous de -creditLimit
        if (this.balance - amount < -creditLimit) {
            throw new BusinessRuleViolation("Limite de crédit dépassée !");
        }

        this.balance -= amount;
    }
}