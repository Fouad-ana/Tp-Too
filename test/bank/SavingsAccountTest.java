package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bank.tx.TransactionType;

class SavingsAccountTest {

    @Test
    void testDepositNominal() {
        // Cas nominal : Dépôt positif
        SavingsAccount sa = new SavingsAccount("SA-1", 100.0, 0.05);
        sa.deposit(50.0);
        assertEquals(150.0, sa.getBalance(), "Le solde doit être de 150.0");
    }

    @Test
    void testWithdrawNominal() {
        // Cas nominal : Retrait autorisé
        SavingsAccount sa = new SavingsAccount("SA-1", 100.0, 0.05);
        sa.withdraw(50.0, TransactionType.WITHDRAW);
        assertEquals(50.0, sa.getBalance());
    }

    @Test
    void testWithdrawOverBalance() {
        // Cas d'erreur : Retrait supérieur au solde
        SavingsAccount sa = new SavingsAccount("SA-1", 100.0, 0.05);
        
        // On vérifie que ça lance bien l'exception
        assertThrows(BusinessRuleViolation.class, () -> {
            sa.withdraw(200.0, TransactionType.WITHDRAW);
        });
    }
    
    @Test
    void testDepositNegative() {
        // Cas d'erreur : Dépôt négatif
        SavingsAccount sa = new SavingsAccount("SA-1", 100.0, 0.05);
        assertThrows(BusinessRuleViolation.class, () -> sa.deposit(-10.0));
    }
}