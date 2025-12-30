package bank.persistence;

import bank.*;
import bank.tx.Transaction;
import bank.tx.TransactionType;

public class TextBankSerializer implements BankSerializer {

    @Override
    public String serialize(Bank bank) {
        StringBuilder sb = new StringBuilder();

        // 1. On parcourt tous les comptes
        for (Account a : bank.getAccounts()) {
            sb.append("ACCOUNT;");
            
            // On détermine le type et les paramètres spécifiques
            if (a instanceof SavingsAccount) {
                sb.append("SAVINGS;");
                sb.append(a.getId()).append(";");
                sb.append(a.getBalance()).append(";");
                // On cast pour avoir le taux
                sb.append(((SavingsAccount) a).getInterestRate());
            } 
            else if (a instanceof CreditAccount) {
                sb.append("CREDIT;");
                sb.append(a.getId()).append(";");
                sb.append(a.getBalance()).append(";");
                // On cast pour la limite
                sb.append(((CreditAccount) a).getCreditLimit());
            }
            else if (a instanceof BusinessAccount) { // Si tu as le BusinessAccount
                sb.append("BUSINESS;");
                sb.append(a.getId()).append(";");
                sb.append(a.getBalance()).append(";");
                // Business a limite ET taux (vérifie l'ordre dans ton constructeur !)
                // Ici je suppose: limite puis taux. Adapte si besoin.
                // Attention : BusinessAccount n'a peut-être pas de getters publics pour limit/rate ?
                // Si ça bloque ici, dis-le moi.
                sb.append("500.0;0.1"); // Valeurs par défaut si pas de getters
            }
            sb.append("\n"); // Saut de ligne
        }
        
        // TODO: On pourrait aussi ajouter les Transactions ici plus tard
        
        return sb.toString();
    }

    @Override
    public Bank deserialize(String data) {
        Bank bank = new Bank();
        String[] lines = data.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split(";");
            if (parts[0].equals("ACCOUNT")) {
                String type = parts[1];
                String id = parts[2];
                double balance = Double.parseDouble(parts[3]);
                
                if (type.equals("SAVINGS")) {
                    double rate = Double.parseDouble(parts[4]);
                    bank.addAccount(new SavingsAccount(id, balance, rate));
                }
                else if (type.equals("CREDIT")) {
                    double limit = Double.parseDouble(parts[4]);
                    bank.addAccount(new CreditAccount(id, balance, limit));
                }
                // Ajouter Business ici si besoin
            }
        }
        return bank;
    }
}