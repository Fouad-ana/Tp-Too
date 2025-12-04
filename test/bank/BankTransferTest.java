package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BankTransferTest {

	@Test
    void testTransferSuccess() {
        // 1. Préparation
        Bank bank = new Bank();
        SavingsAccount src = new SavingsAccount("A", 100.0, 0.01);
        CreditAccount dst = new CreditAccount("B", 0.0, 100.0);

        // ✅ AJOUT OBLIGATOIRE : On enregistre les comptes dans la banque
        bank.addAccount(src);
        bank.addAccount(dst);

        // 2. Action (On utilise bien les IDs maintenant)
        bank.transfer(src.getId(), dst.getId(), 50.0);

        // 3. Vérification
        assertEquals(50.0, src.getBalance());
        assertEquals(50.0, dst.getBalance());
    } }