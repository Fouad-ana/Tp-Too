package bank;

import bank.persistence.FileBankRepository;
import bank.persistence.TextBankSerializer;
import java.nio.file.Path;

public class MainPersistence {

    public static void main(String[] args) {
        // 1. Création de la banque originale
        System.out.println("--- 1. Création de la banque en mémoire ---");
        Bank originalBank = new Bank();
        originalBank.addAccount(new SavingsAccount("SA-100", 1000.0, 0.05));
        originalBank.addAccount(new CreditAccount("CR-200", -50.0, 500.0));
        originalBank.addAccount(new BusinessAccount("BA-300", 2000.0, 1000.0, 0.10));

        // 2. Préparation du système de sauvegarde (Repository)
        // On va sauvegarder dans un fichier nommé "ma_banque.txt" à la racine du projet
        FileBankRepository repo = new FileBankRepository(
            Path.of("ma_banque.txt"), 
            new TextBankSerializer()
        );

        // 3. Test de SAUVEGARDE
        System.out.println("--- 2. Sauvegarde dans le fichier 'ma_banque.txt' ---");
        try {
            repo.save(originalBank);
            System.out.println("✅ Sauvegarde réussie !");
        } catch (Exception e) {
            System.err.println("❌ Erreur de sauvegarde : " + e.getMessage());
            e.printStackTrace();
        }

        // 4. Test de CHARGEMENT
        System.out.println("\n--- 3. Chargement depuis le fichier ---");
        try {
            Bank loadedBank = repo.load(); // On lit le fichier
            
            // On affiche ce qu'on a récupéré
            System.out.println("Comptes récupérés :");
            for (Account a : loadedBank.getAccounts()) {
                System.out.println(" - " + a.getId() + " | Solde : " + a.getBalance());
            }
            System.out.println("✅ Chargement terminé !");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur de chargement : " + e.getMessage());
            e.printStackTrace();
        }
    }
}