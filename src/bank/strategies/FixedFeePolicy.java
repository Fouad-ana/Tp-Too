package bank.strategies;

public class FixedFeePolicy implements FeePolicy {
    private double fee;

    public FixedFeePolicy(double fee) {
        this.fee = fee;
    }

    @Override
    public double computeFee(double amount) {
        return this.fee; // Toujours le même montant
    }
}