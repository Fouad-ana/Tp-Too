package bank.infrastructure;

import bank.domain.Bank;

public interface BankRepository {
    
    // Sauvegarder la banque entière
    void save(Bank bank) throws PersistenceException;

    // Charger la banque
    Bank load() throws PersistenceException;
}