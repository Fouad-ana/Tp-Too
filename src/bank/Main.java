package bank;

public class Main {

    public static void main(String[] args) {
        try {
            // Création des comptes
            Account a1 = new SavingsAccount("SA-1001", 200.0, 0.018);
            Account a2 = new CreditAccount("CA-9001", 0.0, 500.0);

            // Opérations
            a1.deposit(50);      // Solde devient 250
            a1.withdraw(20);     // Solde devient 230
            
            a2.withdraw(100);    // Autorisé car creditLimit est 500. Solde devient -100.

            // Affichage
            System.out.printf("[Savings %s] Solde: %.2f%n", a1.getAccountNumber(), a1.getBalance());
            System.out.printf("[Credit %s] Solde: %.2f%n", a2.getAccountNumber(), a2.getBalance());

            // Test d'erreur (décommente pour tester l'exception)
            // a1.withdraw(1000); // Devrait planter

        } catch (BusinessRuleViolation e) {
            System.out.println("Erreur métier : " + e.getMessage());
        }
    }
}