package bank;

import java.util.HashMap;
import java.util.Map;
import bank.errors.*; // On importe nos nouvelles exceptions

public class Bank {
    
    private Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account a) {
        accounts.put(a.getId(), a);
    }
    
    // Nouvelle version atomique et loggée
    public void transfer(String fromId, String toId, double amount) {
        // 1. Vérifications (Exceptions)
        if (!accounts.containsKey(fromId) || !accounts.containsKey(toId)) {
            throw new UnknownAccountException("Compte introuvable : " + fromId + " ou " + toId);
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException("Le montant doit être positif : " + amount);
        }

        try {
            Account src = accounts.get(fromId);
            Account dst = accounts.get(toId);

            // 2. Action
            src.withdraw(amount); 
            dst.deposit(amount);
            
            // 3. SUCCÈS -> On écrit dans le journal (Logger)
            Logger.logInfo("Transfert OK " + amount + " EUR " + fromId + " -> " + toId);

        } catch (Exception e) {
            // 4. ECHEC -> On écrit l'erreur dans le journal
            Logger.logError("Transfert FAILED " + fromId + " -> " + toId + " : " + e.getMessage());
            
            // Et on lance l'exception pour prévenir le programme
            throw new TransferException("Echec du transfert", e);
        }

}
 // Nécessaire pour le TP6 (Sérialisation)
    public Iterable<Account> getAccounts() {
        return this.accounts.values();
    }
    }