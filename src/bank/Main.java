package bank;

import bank.strategies.PercentageFeePolicy;

public class Main {

    public static void main(String[] args) {
        // 1. Création de la banque
        Bank bank = new Bank();

        // 2. Création des comptes
        SavingsAccount a1 = new SavingsAccount("SA1", 100.0, 0.02);
        CreditAccount a2 = new CreditAccount("CA1", 50.0, 100.0);
        
        // 3. Ajout des comptes à la banque (TRES IMPORTANT !)
        bank.addAccount(a1);
        bank.addAccount(a2);

        // 4. Affichage avant
        System.out.println("Solde A1 : " + a1.getBalance());
        System.out.println("Solde A2 : " + a2.getBalance());

        // 5. Test du Transfert (Correction ici : on utilise getId())
        System.out.println("\n--- Transfert de 10 EUR de A1 vers A2 ---");
        try {
            // AVANT : bank.transfer(a1, a2, 10.0); -> ERREUR
            // MAINTENANT : On utilise les IDs
        	// CHANGE ÇA :
        	// bank.transfer(a1, a2, 100);

        	// PAR ÇA :
        	bank.transfer(a1.getId(), a2.getId(), 100.0);
        } catch (Exception e) {
            System.err.println("Erreur de transfert : " + e.getMessage());
        }

        // 6. Test Strategy : On change les frais pour A2 (2%)
        System.out.println("\n--- Changement de stratégie (2% frais) pour A2 ---");
        a2.setFeePolicy(new PercentageFeePolicy(0.02));
        try {
            a2.withdraw(10.0); // Retrait direct pour tester les frais
        } catch (Exception e) {
            System.err.println("Erreur retrait : " + e.getMessage());
        }

        // Affichage final
        System.out.println("Solde A1 : " + a1.getBalance());
        System.out.println("Solde A2 : " + a2.getBalance());
    }
}