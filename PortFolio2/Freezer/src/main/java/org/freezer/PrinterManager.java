package org.freezer;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrinterManager {
    // Het pad naar het bestand dat de productinformatie bevat
    private static final String PRODUCT_FILE_PATH = "/Users/timobrouwer/Documents/HHS/Github/PortFolio/PortFolio2/Freezer/src/main/java/org/freezer/producten.txt";

    // Methode om de productenlijst af te drukken
    public void printProductList() {
        try {
            // Lees alle lijnen van het bestand
            List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));
            // Maak een StringBuilder om de afdruk op te bouwen
            StringBuilder sb = new StringBuilder();
            // Voeg een titel toe aan de afdruk
            sb.append("Productenlijst:\n");

            // Ga door elke regel in het bestand
            for (String line : lines) {
                // Splits de regel op de komma en spatie om de onderdelen te krijgen
                String[] parts = line.split(", ");
                // Controleer of de regel minimaal 3 onderdelen heeft
                if (parts.length >= 3) {
                    // Verkrijg de ID, naam en datum van de productinformatie
                    String id = parts[0].split(": ")[1];
                    String naam = parts[1].split(": ")[1];
                    String datum = parts[2].split(": ")[1];
                    // Voeg de opgemaakte informatie toe aan de StringBuilder
                    sb.append(String.format("ID: %-8s Naam: %-20s Datum: %-20s\n", id, naam, datum));
                }
            }

            // Zoek de standaard printservice
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            if (service != null) {
                // Maak een afdruktaak aan met de standaard printservice
                DocPrintJob job = service.createPrintJob();
                // Creëer een InputStream van de afdrukgegevens
                InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
                // Creëer een Doc van het InputStream-object
                Doc doc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
                // Voer de afdruktaak uit
                job.print(doc, null);
            } else {
                // Geef een foutmelding weer als er geen printservices zijn gevonden
                System.err.println("Geen printservices gevonden.");
            }
        } catch (IOException | PrintException e) {
            // Vang en geef eventuele fouten weer die tijdens het printen optreden
            System.err.println(" " + e.getMessage());
        }
    }
}
