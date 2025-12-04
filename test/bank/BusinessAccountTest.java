package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bank.errors.TransferException; // ✅ Important pour que l'erreur soit reconnue

class BusinessAccountTest {

    // --- TESTS DES INTÉRÊTS ---

    @Test
    void testInterestPositiveBalance() {
        // On crée un compte avec : ID="BA-1", Solde=100.0, Limite=500.0, Taux=10% (0.10)
        BusinessAccount ba = new BusinessAccount("BA-1", 100.0, 500.0, 0.10);
        
        // On applique les intérêts
        ba.applyInterest();
        
        // 100 + 10% = 110
        assertEquals(110.0, ba.getBalance());
    }

    @Test
    void testInterestNegativeBalance() {
        // Solde négatif -> Pas d'intérêts
        BusinessAccount ba = new BusinessAccount("BA-2", -100.0, 500.0, 0.10);
        
        ba.applyInterest();
        
        assertEquals(-100.0, ba.getBalance());
    }

    // --- TESTS DU RETRAIT (Version TP5 - Template Method) ---

    @Test
    void testWithdrawNominal() {
        // Solde 100. Limite 500. Retrait 200.
        BusinessAccount ba = new BusinessAccount("BA-3", 100.0, 500.0, 0.10);
        
        // ✅ CORRECTION : On met SEULEMENT le montant (pas de TransactionType)
        ba.withdraw(200.0); 
        
        // 100 - 200 = -100
        assertEquals(-100.0, ba.getBalance());
    }

    @Test
    void testWithdrawLimitExact() {
        // Solde 0. Limite 500. Retrait 500.
        BusinessAccount ba = new BusinessAccount("BA-4", 0.0, 500.0, 0.10);
        
        ba.withdraw(500.0);
        
        // 0 - 500 = -500 (C'est la limite exacte autorisée)
        assertEquals(-500.0, ba.getBalance());
    }

    @Test
    void testWithdrawOverLimit() {
        // Solde 0. Limite 500. Retrait 501 (Trop !)
        BusinessAccount ba = new BusinessAccount("BA-5", 0.0, 500.0, 0.10);
        
        // ✅ CORRECTION : On vérifie que ça lance bien TransferException
        assertThrows(TransferException.class, () -> {
            ba.withdraw(501.0);
        });
    }
}