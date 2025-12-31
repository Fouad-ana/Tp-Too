package bank.application;

import bank.domain.*;
import bank.infrastructure.*;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 🏦 DÉMARRAGE DE L'APPLICATION UPPA BANK (Final) ===");

        // ✅ ON REVIENT A LA VERSION FICHIER (Stable et fonctionnelle)
        BankRepository repository = new FileBankRepository(
            Path.of("banque_finale.txt"), 
            new TextBankSerializer()
        );

        // Chargement
        Bank bank;
        try {
            bank = repository.load();
            System.out.println("✅ Données chargées."); 
        } catch (Exception e) {
            bank = new Bank();
        }

        // Opérations
        System.out.println("\n--- 💼 Exécution des opérations ---");
        try {
            Account c1 = new SavingsAccount("SA-FINAL", 500.0, 0.03);
            bank.addAccount(c1);
            System.out.println("Compte ajouté : " + c1.getId());
        } catch (Exception e) {
            // Ignorer si existe déjà
        }

        // Sauvegarde
        System.out.println("\n--- 💾 Sauvegarde ---");
        try {
            repository.save(bank);
            System.out.println("✅ Banque sauvegardée dans 'banque_finale.txt'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== 👋 PROJET TERMINÉ ===");
    }
}