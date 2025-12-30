package bank.infrastructure;

import bank.domain.Bank; // Note le changement de package
// Si BankRepository est resté dans persistence, adapte l'import, 
// mais normalement il est maintenant dans infrastructure :
// import bank.infrastructure.BankRepository; 

public class InMemoryBankRepository implements BankRepository {

    // On stocke la banque juste dans une variable (en mémoire vive)
    private Bank storedBank;

    @Override
    public void save(Bank bank) {
        // Dans une vraie app, on ferait une copie complète (clone).
        // Ici, pour le TP, on stocke juste la référence.
        this.storedBank = bank;
        System.out.println("💾 (Mémoire) Banque sauvegardée en RAM.");
    }

    @Override
    public Bank load() {
        if (this.storedBank == null) {
            return new Bank(); // Retourne une banque vide si rien en mémoire
        }
        System.out.println("📂 (Mémoire) Banque chargée depuis la RAM.");
        return this.storedBank;
    }
}