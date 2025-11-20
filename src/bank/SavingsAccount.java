package bank;

public final class SavingsAccount extends Account {

    private final double interestRate; // Taux d'intérêt

    public SavingsAccount(String id, double initial, double interestRate) {
        super(id, initial); // Appel du constructeur du parent (Account)
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        // 1. Vérifier que le montant est positif
        if (amount <= 0) {
            throw new BusinessRuleViolation("Le montant du retrait doit être positif !");
        }
        
        // 2. Vérifier que le solde restera positif (PAS DE DÉCOUVERT)
        if (this.balance - amount < 0) {
            throw new BusinessRuleViolation("Fonds insuffisants (Compte Épargne)");
        }

        // 3. Effectuer le retrait
        this.balance -= amount;
    }
    
    // Méthode bonus (section 6 du PDF)
    public void applyInterest() {
        this.balance += this.balance * interestRate;
    }
}