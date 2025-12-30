package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import bank.domain.Bank;
import bank.domain.CreditAccount;
import bank.domain.SavingsAccount;
import bank.errors.*;

class TransferExceptionTest {

    @Test
    void testTransferExceptions() {
        Bank bank = new Bank();
        SavingsAccount sa = new SavingsAccount("A", 100.0, 0.01);
        bank.addAccount(sa);

        // 1. Test UnknownAccountException [cite: 244]
        assertThrows(UnknownAccountException.class, () -> {
            bank.transfer("A", "INCONNU", 10.0);
        });

        // 2. Test InvalidAmountException [cite: 243]
        // On ajoute le compte B pour que l'erreur ne soit pas "Compte inconnu"
        CreditAccount ca = new CreditAccount("B", 0.0, 100.0);
        bank.addAccount(ca);
        
        assertThrows(InvalidAmountException.class, () -> {
            bank.transfer("A", "B", -50.0);
        });
    }

    @Test
    void testTransferFailureLogsError() {
        Bank bank = new Bank();
        // Compte avec 100 euros
        SavingsAccount sa = new SavingsAccount("SRC", 100.0, 0.01);
        CreditAccount ca = new CreditAccount("DST", 0.0, 100.0);
        bank.addAccount(sa);
        bank.addAccount(ca);

        // On essaie de virer 200 (trop cher) -> Exception métier
        // Cela doit être capturé et relancé comme TransferException
        assertThrows(TransferException.class, () -> {
            bank.transfer("SRC", "DST", 200.0);
        });
    }
}