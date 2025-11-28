package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bank.tx.TransactionType;

class BusinessAccountTest {

    // --- TESTS DES INTÉRÊTS (Déjà validés) ---

    @Test
    void testInterestPositiveBalance() {
        BusinessAccount ba = new BusinessAccount("BA-1", 100.0, 500.0, 0.10);
        ba.applyInterest();
        assertEquals(110.0, ba.getBalance());
    }

    @Test
    void testInterestNegativeBalance() {
        BusinessAccount ba = new BusinessAccount("BA-1", -100.0, 500.0, 0.10);
        ba.applyInterest();
        assertEquals(-100.0, ba.getBalance(), "Pas d'intérêts si solde négatif");
    }

    // --- NOUVEAUX TESTS : LE RETRAIT (Section 5.1) ---

    @Test
    void testWithdrawNominal() {
        // Retrait autorisé dans la limite
        BusinessAccount ba = new BusinessAccount("BA-1", 100.0, 500.0, 0.10);
        ba.withdraw(200.0, TransactionType.WITHDRAW); 
        // 100 - 200 = -100 (Autorisé car limite = 500)
        assertEquals(-100.0, ba.getBalance());
    }

    @Test
    void testWithdrawLimitExact() {
        // On descend pile à la limite
        BusinessAccount ba = new BusinessAccount("BA-1", 0.0, 500.0, 0.10);
        ba.withdraw(500.0, TransactionType.WITHDRAW);
        assertEquals(-500.0, ba.getBalance());
    }

    @Test
    void testWithdrawOverLimit() {
        // On dépasse la limite -> Exception
        BusinessAccount ba = new BusinessAccount("BA-1", 0.0, 500.0, 0.10);
        assertThrows(BusinessRuleViolation.class, () -> {
            ba.withdraw(501.0, TransactionType.WITHDRAW);
        });
    }
}