package bank.application;

import java.nio.file.Path;

import bank.domain.Account;
import bank.domain.Bank;
import bank.domain.CreditAccount;
import bank.domain.SavingsAccount;
import bank.infrastructure.BankRepository;
import bank.infrastructure.FileBankRepository;
import bank.infrastructure.TextBankSerializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 🏦 DÉMARRAGE DE L'APPLICATION UPPA BANK ===");

        // 1. Choix de l'infrastructure (Fichier ou Mémoire)
        // Ici on prend Fichier pour la vraie démo
        BankRepository repository = new FileBankRepository(
            Path.of("banque_finale.txt"), 
            new TextBankSerializer()
        );

        // 2. Chargement des données existantes
        Bank bank;
        try {
            bank = repository.load();
            System.out.println("✅ Données chargées. Nombre de comptes : " + bank.getAccounts().toString().split(",").length); 
            // (Note: l'affichage du nombre est approximatif ici, c'est juste pour l'exemple)
        } catch (Exception e) {
            System.out.println("⚠️ Aucune donnée trouvée, création d'une nouvelle banque.");
            bank = new Bank();
        }

        // 3. Opérations Métier (Si la banque est vide, on ajoute des trucs)
        if (true) { // Tu peux changer la condition
            System.out.println("\n--- 💼 Exécution des opérations bancaires ---");
            
            try {
                // Création de comptes
                Account compteCourant = new SavingsAccount("SA-DEMO", 500.0, 0.02);
                Account compteCredit = new CreditAccount("CR-DEMO", -20.0, 1000.0);
                
                // Ajout à la banque (vérifie si l'ID n'existe pas déjà idéalement)
                // Pour la démo simple, on ajoute brute :
                bank.addAccount(compteCourant);
                bank.addAccount(compteCredit);
                
                System.out.println("Ajout de comptes effectué.");

                // Virement
                System.out.println("Tentative de virement de 100€...");
                // Note : Il faudrait récupérer les comptes via bank.getAccount(...) pour bien faire
                // Mais pour cette démo rapide, on manipule les objets qu'on vient de créer
                compteCourant.withdraw(100.0);
                compteCredit.deposit(100.0);
                System.out.println("virement terminé !");

            } catch (Exception e) {
                System.err.println("Erreur bancaire : " + e.getMessage());
            }
        }

        // 4. Sauvegarde Finale
        System.out.println("\n--- 💾 Sauvegarde des données ---");
        try {
            repository.save(bank);
            System.out.println("✅ Banque sauvegardée avec succès dans 'banque_finale.txt'");
        } catch (Exception e) {
            System.err.println("❌ Erreur critique de sauvegarde : " + e.getMessage());
        }

        System.out.println("\n=== 👋 FIN DU PROGRAMME ===");
    }
}