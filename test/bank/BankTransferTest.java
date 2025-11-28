package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BankTransferTest {

    @Test
    void testTransferSuccess() {
        // Cas nominal
        SavingsAccount src = new SavingsAccount("A", 100.0, 0.01);
        CreditAccount dst = new CreditAccount("B", 0.0, 100.0);
        Bank bank = new Bank();

        bank.transfer(src, dst, 50.0);

        assertEquals(50.0, src.getBalance()); // 100 - 50
        assertEquals(50.0, dst.getBalance()); // 0 + 50
    }

    @Test
    void testTransferFail_InsufficientFunds() {
        // Cas d'erreur : Pas assez d'argent sur la source
        SavingsAccount src = new SavingsAccount("A", 10.0, 0.01); // Seulement 10.0
        CreditAccount dst = new CreditAccount("B", 0.0, 100.0);
        Bank bank = new Bank();

        // On essaie de virer 50.0 -> Doit échouer
        assertThrows(BusinessRuleViolation.class, () -> {
            bank.transfer(src, dst, 50.0);
        });

        // VERIFICATION DE L'ATOMICITÉ :
        // Le solde source ne doit pas avoir bougé (pas de retrait partiel)
        assertEquals(10.0, src.getBalance(), "Le solde source ne doit pas changer");
        assertEquals(0.0, dst.getBalance(), "Le solde destination ne doit pas changer");
    }
}