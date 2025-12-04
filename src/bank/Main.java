package bank;

import bank.tx.Transaction; // Pour afficher l'historique

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Création des comptes et de la banque
            SavingsAccount a1 = new SavingsAccount("SA-1001", 200.0, 0.05); // 5% intérêt
            CreditAccount a2 = new CreditAccount("CA-9001", 0.0, 500.0);
            Bank bank = new Bank();

            System.out.println("--- État initial ---");
            printBalance(a1); // Solde 200
            printBalance(a2); // Solde 0

            // 2. Opérations classiques
            a1.deposit(100);      // +100 -> 300
            a1.withdraw(50);      // -50  -> 250
            a1.applyInterest();   // +5% de 250 = +12.5 -> 262.5
            
            System.out.println("\n--- Après opérations sur A1 ---");
            printBalance(a1);

            // 3. Test du Transfert (Nouveauté TP2)
            System.out.println("\n--- Transfert de 100.00 de A1 vers A2 ---");
            bank.transfer(a1.getId(), a2.getId(), 100.0);
            
            printBalance(a1); // Devrait être 162.5
            printBalance(a2); // Devrait être 100.0

            // 4. Affichage de l'historique complet (Transactions)
            System.out.println("\n=== HISTORIQUE DETAILLÉ ===");
            
            System.out.println("Historique A1 (Epargne):");
            for (Transaction t : a1.history()) {
                System.out.println("  " + t);
            }

            System.out.println("\nHistorique A2 (Crédit):");
            for (Transaction t : a2.history()) {
                System.out.println("  " + t);
            }

        } catch (BusinessRuleViolation e) {
            System.err.println("ERREUR METIER : " + e.getMessage());
        }
    }

    // Petite méthode utilitaire pour afficher le solde
    private static void printBalance(Account a) {
        System.out.printf("[%s] Solde : %.2f%n", a.getAccountNumber(), a.getBalance());
    }
}