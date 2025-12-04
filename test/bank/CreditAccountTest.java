package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bank.errors.TransferException; // ✅ Important : Nouvelle exception TP4/TP5

class CreditAccountTest {

    @Test
    void testWithdrawNominal() {
        // Compte avec 100€ de solde et 500€ de découvert autorisé
        CreditAccount ca = new CreditAccount("C-1", 100.0, 500.0);
        
        // ✅ CORRECTION : Un seul argument (plus de TransactionType)
        ca.withdraw(50.0);
        
        // 100 - 50 = 50
        assertEquals(50.0, ca.getBalance());
    }

    @Test
    void testWithdrawInOverdraft() {
        // Solde à 0, découvert autorisé de 100
        CreditAccount ca = new CreditAccount("C-2", 0.0, 100.0);
        
        // On retire 50 (on passe en négatif, mais autorisé)
        ca.withdraw(50.0);
        
        assertEquals(-50.0, ca.getBalance());
    }

    @Test
    void testWithdrawLimitReached() {
        // Solde à 0, découvert de 100
        CreditAccount ca = new CreditAccount("C-3", 0.0, 100.0);
        
        // On retire pile le maximum autorisé (100)
        ca.withdraw(100.0);
        
        assertEquals(-100.0, ca.getBalance());
    }

    @Test
    void testWithdrawOverLimit() {
        // Solde à 0, découvert de 100
        CreditAccount ca = new CreditAccount("C-4", 0.0, 100.0);
        
        // ❌ On essaie de retirer 101 (trop !)
        // ✅ CORRECTION : On attend l'exception TransferException
        assertThrows(TransferException.class, () -> {
            ca.withdraw(101.0);
        });
    }
}