package bank.infrastructure;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    
    // Le nom du fichier qui sera créé automatiquement à la racine du projet
    private static final String LOG_FILE = "bank.log";
    
    // Pour formater la date comme demandé : [2025-01-20 15:33:05]
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Méthode privée qui fait le travail d'écriture
    private static void writeLog(String level, String msg) {
        // On récupère l'heure actuelle
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        // On prépare la ligne : [DATE] INFO Message
        String line = String.format("[%s] %s %s", timestamp, level, msg);
        
        // Le "true" ici est très important : il permet d'ajouter à la suite (append)
        // sans effacer ce qu'il y avait avant.
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(line); // Écrit la ligne dans le fichier
        } catch (IOException e) {
            System.err.println("Impossible d'écrire dans le log : " + e.getMessage());
        }
    }

    // Méthodes publiques utilisables par la Banque
    public static void logInfo(String msg) {
        writeLog("INFO", msg); // [cite: 63]
    }

    public static void logError(String msg) {
        writeLog("ERROR", msg); // [cite: 64]
    }
}