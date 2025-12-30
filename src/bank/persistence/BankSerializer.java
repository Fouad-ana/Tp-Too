package bank.persistence;

import bank.Bank;

public interface BankSerializer {
    // Transforme l'objet Bank en texte
    String serialize(Bank bank);
    
    // Transforme le texte en objet Bank
    Bank deserialize(String data);
}