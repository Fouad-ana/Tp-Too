package bank.strategies;

public interface FeePolicy {
    // Calcule les frais pour un montant donné
    double computeFee(double amount);
}