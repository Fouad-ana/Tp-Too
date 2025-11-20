package bank;

import bank.tx.TransactionType;

public class Bank {

    // Méthode pour effectuer le virement de manière atomique
    public void transfer(Account from, Account to, double amount) {
        // 1. Vérification basique
        if (amount <= 0) {
            throw new BusinessRuleViolation("Le montant du virement doit être positif");
        }

        // 2. Retrait chez l'émetteur (Type: TRANSFER_OUT)
        // Si le solde est insuffisant, withdraw() lance une exception et tout s'arrête ici.
        from.withdraw(amount, TransactionType.TRANSFER_OUT);
        
        // 3. Dépôt chez le bénéficiaire (Type: TRANSFER_IN)
        try {
            to.deposit(amount, TransactionType.TRANSFER_IN);
        } catch (Exception e) {
            // Cas critique (ex: compte destinataire fermé/invalide).
            // Pour ce TP, on laisse l'erreur remonter.
            throw e; 
        }
    }
}