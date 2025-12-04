package bank.strategies;

public class PercentageFeePolicy implements FeePolicy {
    private double rate; // Exemple 0.05 pour 5%

    public PercentageFeePolicy(double rate) {
        this.rate = rate;
    }

    @Override
    public double computeFee(double amount) {
        return amount * this.rate;
    }
}