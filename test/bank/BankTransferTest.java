package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bank.domain.Bank;
import bank.domain.CreditAccount;
import bank.domain.SavingsAccount;
import bank.errors.*;

class BankTransferTest {

    @Test
    void testTransferSuccess() {
        Bank bank = new Bank();
        SavingsAccount src = new SavingsAccount("A", 100.0, 0.01);
        CreditAccount dst = new CreditAccount("B", 0.0, 100.0);

        // On enregistre les comptes
        bank.addAccount(src);
        bank.addAccount(dst);

        // Correction : on utilise getId()
        bank.transfer(src.getId(), dst.getId(), 50.0);

        assertEquals(50.0, src.getBalance());
        assertEquals(50.0, dst.getBalance());
    }

    @Test
    void testTransferFail_InsufficientFunds() {
        Bank bank = new Bank();
        SavingsAccount src = new SavingsAccount("A", 20.0, 0.01); // Pas assez d'argent
        CreditAccount dst = new CreditAccount("B", 0.0, 100.0);

        bank.addAccount(src);
        bank.addAccount(dst);

        // On vérifie que ça lance bien l'exception
        assertThrows(TransferException.class, () -> {
            bank.transfer(src.getId(), dst.getId(), 50.0);
        });
    }
}