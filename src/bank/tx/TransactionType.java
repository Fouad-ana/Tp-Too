package bank.tx;

public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER_IN,    // Si tu l'as ajouté avant
    TRANSFER_OUT,   // Si tu l'as ajouté avant
    BANK_FEE        // 👈 AJOUTE CELUI-CI (Frais bancaires)
}