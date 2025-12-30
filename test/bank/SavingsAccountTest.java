package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bank.domain.SavingsAccount;
import bank.errors.TransferException; // ✅ Nouvelle exception

class SavingsAccountTest {

    @Test
    void testApplyInterest() {
        // Solde 100, Taux 5% (0.05)
        SavingsAccount sa = new SavingsAccount("SA-1", 100.0, 0.05);
        
        sa.applyInterest();
        
        // 100 + 5% = 105
        assertEquals(105.0, sa.getBalance());
    }

    @Test
    void testWithdrawNominal() {
        // Solde 100
        SavingsAccount sa = new SavingsAccount("SA-2", 100.0, 0.05);
        
        // ✅ CORRECTION : Juste le montant
        sa.withdraw(50.0);
        
        assertEquals(50.0, sa.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        // Solde 100
        SavingsAccount sa = new SavingsAccount("SA-3", 100.0, 0.05);
        
        // ❌ On essaie de retirer 150 (Impossible pour un livret)
        // ✅ CORRECTION : On attend TransferException
        assertThrows(TransferException.class, () -> {
            sa.withdraw(150.0);
        });
    }
}