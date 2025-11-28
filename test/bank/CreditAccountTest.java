package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bank.tx.TransactionType;

class CreditAccountTest {

    @Test
    void testWithdrawLimit() {
        // On a le droit de descendre jusqu'à -limit
        double limit = 500.0;
        CreditAccount ca = new CreditAccount("CA-1", 0.0, limit);
        
        // Retrait de 500 (solde devient -500) -> OK
        ca.withdraw(500.0, TransactionType.WITHDRAW);
        assertEquals(-500.0, ca.getBalance());
    }

    @Test
    void testWithdrawOverLimit() {
        // On dépasse la limite -> Exception
        double limit = 500.0;
        CreditAccount ca = new CreditAccount("CA-1", 0.0, limit);
        
        assertThrows(BusinessRuleViolation.class, () -> {
            ca.withdraw(501.0, TransactionType.WITHDRAW);
        });
    }
}