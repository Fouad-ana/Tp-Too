package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bank.infrastructure.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class LoggerTest {

    @Test
    void testLoggerWritesFile() throws IOException {
        // On écrit un log
        Logger.logInfo("Test de log unitaire");
        
        // On vérifie que le fichier existe et contient la ligne
        Path path = Path.of("bank.log");
        assertTrue(Files.exists(path), "Le fichier bank.log doit exister");
        
        List<String> lines = Files.readAllLines(path);
        assertFalse(lines.isEmpty());
        
        // On regarde si la dernière ligne contient bien "INFO" et notre message
        String lastLine = lines.get(lines.size() - 1);
        assertTrue(lastLine.contains("INFO"));
        assertTrue(lastLine.contains("Test de log unitaire"));
    }
}