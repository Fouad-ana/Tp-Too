package bank.persistence;

import bank.Bank;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBankRepository implements BankRepository {

    private final Path path;
    private final BankSerializer serializer;

    // Constructeur : on lui donne le chemin du fichier et le traducteur à utiliser
    public FileBankRepository(Path path, BankSerializer serializer) {
        this.path = path;
        this.serializer = serializer;
    }

    @Override
    public void save(Bank bank) throws PersistenceException {
        try {
            // 1. On transforme la banque en texte
            String data = serializer.serialize(bank);
            // 2. On écrit ce texte dans le fichier
            Files.writeString(path, data);
        } catch (IOException e) {
            throw new PersistenceException("Impossible d'écrire dans le fichier : " + path, e);
        }
    }

    @Override
    public Bank load() throws PersistenceException {
        try {
            // Si le fichier n'existe pas encore, on renvoie une banque vide
            if (!Files.exists(path)) {
                return new Bank();
            }
            // 1. On lit le texte du fichier
            String data = Files.readString(path);
            // 2. On re-transforme le texte en objets Bank
            return serializer.deserialize(data);
        } catch (IOException e) {
            throw new PersistenceException("Impossible de lire le fichier : " + path, e);
        }
    }
}