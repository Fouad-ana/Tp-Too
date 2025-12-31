package bank.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bank.domain.Account;
import bank.domain.Bank;
import bank.domain.CreditAccount;
import bank.domain.SavingsAccount;

public class DatabaseBankRepository implements BankRepository {

    private final String dbUrl;

    // Exemple d'URL : "jdbc:sqlite:banque.db"
    public DatabaseBankRepository(String dbUrl) {
        this.dbUrl = dbUrl;
        initializeDatabase();
    }

    private void initializeDatabase() {
        // On crée la table si elle n'existe pas
        String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                     "id TEXT PRIMARY KEY, " +
                     "type TEXT NOT NULL, " +
                     "balance REAL NOT NULL, " +
                     "param1 REAL, " + // Taux ou Découvert
                     "param2 REAL" +   // 2ème param pour Business
                     ")";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erreur init BDD : " + e.getMessage());
        }
    }

    @Override
    public void save(Bank bank) {
        String insertSql = "INSERT OR REPLACE INTO accounts(id, type, balance, param1, param2) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            
            // On démarre une transaction pour que ce soit rapide
            conn.setAutoCommit(false);

            for (Account a : bank.getAccounts()) {
                pstmt.setString(1, a.getId());
                pstmt.setDouble(3, a.getBalance());

                if (a instanceof SavingsAccount) {
                    pstmt.setString(2, "SAVINGS");
                    pstmt.setDouble(4, ((SavingsAccount) a).getInterestRate());
                    pstmt.setDouble(5, 0.0);
                } else if (a instanceof CreditAccount) {
                    pstmt.setString(2, "CREDIT");
                    pstmt.setDouble(4, ((CreditAccount) a).getCreditLimit());
                    pstmt.setDouble(5, 0.0);
                } 
                // Ajouter BusinessAccount ici si besoin
                
                pstmt.executeUpdate();
            }
            conn.commit();
            System.out.println("💾 (SQL) Banque sauvegardée dans la BDD.");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur sauvegarde SQL", e);
        }
    }

    @Override
    public Bank load() {
        Bank bank = new Bank();
        String sql = "SELECT * FROM accounts";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                String id = rs.getString("id");
                double balance = rs.getDouble("balance");
                double p1 = rs.getDouble("param1");

                if ("SAVINGS".equals(type)) {
                    bank.addAccount(new SavingsAccount(id, balance, p1));
                } else if ("CREDIT".equals(type)) {
                    bank.addAccount(new CreditAccount(id, balance, p1));
                }
                // Gérer les autres types...
            }
            System.out.println("📂 (SQL) Banque chargée depuis la BDD.");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur chargement SQL", e);
        }
        return bank;
    }
}