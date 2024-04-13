package org.freezer;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class Manager implements ProductManager{
    private final ArrayList<Product> products; // Lijst van producten
    private final TreeSet<Integer> availableIDs = new TreeSet<>(); // Beschikbare ID's voor hergebruik
    private final String filePath = "/Users/timobrouwer/Documents/HHS/Github/PortFolio/PortFolio2/Freezer" +
            "/src/main/java/org/freezer/producten.txt"; // Pad naar het productenbestand
    private final StickerPrinter printer; // Stickerprinter-object

    public Manager() {
        this.products = new ArrayList<>(); // Initialiseren van de lijst met producten
        this.printer = new StickerPrinter(); // Initialiseren van de stickerprinter
        loadProductsFromFile(); // Laden van producten uit het bestand
    }

    /*
    Deze methode voegt een product toe aan een lijst met de; Naam en Datum. Hierbij wordt ook automatisch
    een ID gekoppeld zodat er geen dubbele producten in kunnen staan.
    Hierbij wordt ook een QR-code gegenereerd en automatisch afgedrukt.
     */
    @Override
    public void addAndGenerateProduct(Scanner scanner) {
        System.out.println("Naam product:");
        String productName = scanner.nextLine();

        // Datum invoeren en controleren
        String expirationDateStr = null;
        // Controleert of de vervaldatum van het product geldig is door de gebruikersinvoer te parsen.
        // Parse veranderd de datatype in dit geval naar een Localdate object
        LocalDate expirationDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while(expirationDate == null){
            System.out.println("Datum (bijv. 01-01-2024):");
            expirationDateStr = scanner.nextLine();

            try{
                expirationDate = LocalDate.parse(expirationDateStr, formatter);
            } catch (DateTimeParseException e){
                System.out.println("Ongeldige Datum, probeer opnieuw.");
            }
        }

        // Nieuw product maken en toevoegen aan de lijst
        Product newProduct = new Product(productName, expirationDate);
        addProduct(newProduct);
        System.out.println("Product toegevoegd!");

        // QR-code genereren en afdrukken
        String qrData = "Product ID: " + newProduct.getId() + ", Naam: " + productName +
                ", Vervaldatum: " + formatter.format(expirationDate);
        String qrPath = "/Users/timobrouwer/Documents/HHS/Semester 2/Project OPT1/QRcode/"
                + newProduct.getId() + "_" + productName.replaceAll("\\s+", "_") + ".jpg";
        QRcode.generateQRCode(qrData, qrPath);

        // QR-code afdrukken
        printer.printQRCode(qrPath);
    }

    /*
    In deze methode wordt een product verwijderd uit de lijst inclusief de QRcode.
     */
    @Override
    public void removeProductAndQrCode(Scanner scanner) {
        System.out.println("ID van het product die u wilt verwijderen:");
        int productIdToRemove = scanner.nextInt();
        scanner.nextLine();

        // checkt of product met ID wel bestaat
        Product productToRemove = getProductById(productIdToRemove);
        if (productToRemove == null) {
            System.out.println("Product niet gevonden!");
            return;
        }

        // verwijderd product door middel van ID
        deleteProduct(productIdToRemove);
        System.out.println("Product met ID '" + productIdToRemove + "' verwijderd!");

        //verwijderd de QR code uit de map
        String qrPath = "/Users/timobrouwer/Documents/HHS/Semester 2/Project OPT1/QRcode/"
                + productIdToRemove + "_" + productToRemove.naam.replaceAll("\\s+", "_") + ".jpg";
        File qrFile = new File(qrPath);
        if (qrFile.delete()) {
            System.out.println("Bijbehorende QR-code verwijderd.");
        } else {
            System.out.println("Kon QR-code niet verwijderen of bestand bestaat niet.");
        }
    }

    /*
    In deze methode wordt de lijst weergegeven met ID, Naam, Datum.
     */
    @Override
    public void displayProductList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Product Lijst:");
        for (Product product : products) {
            String formattedDate = product.overdatum.format(formatter);
            System.out.println("ID: " + product.getId() + ", Naam: " + product.naam + ", Datum: " + formattedDate);
        }
    }



    // Voegt een nieuw product toe aan de lijst van producten.
    // Als er herbruikbare ID's beschikbaar zijn, wordt de eerste beschikbare ID gebruikt.
    // Anders wordt een nieuwe ID toegewezen op basis van het hoogste ID in de huidige lijst, plus één.
    // Na het toewijzen van een ID wordt het product toegevoegd aan de lijst van producten en wordt het opgeslagen naar een bestand.

    public void addProduct(Product product) {
        if (!availableIDs.isEmpty()) {
            int reusedId = availableIDs.pollFirst();
            product.setId(reusedId);
        } else {
            int newId = products.isEmpty() ? 1 : products.stream().max(Comparator.comparingInt(Product::getId)).
                    get().getId() + 1;
            product.setId(newId);
        }

        products.add(product);
        saveProductToFile(product);
    }

    // Verwijdert een product uit de lijst van producten op basis van de opgegeven product-ID.
    // Als het product wordt verwijderd, wordt de bijbehorende ID toegevoegd aan de lijst van beschikbare ID's.
    // Nadat het product is verwijderd, wordt het bijgewerkte productbestand opgeslagen.
    public void deleteProduct(int productId) {
        products.removeIf(product -> {
            boolean toRemove = product.getId() == productId;
            if (toRemove) {
                availableIDs.add(productId);
            }
            return toRemove;
        });
        updateProductFile();
    }

    // Zoekt en retourneert een product uit de lijst van producten op basis van de opgegeven ID.
    // Als er geen overeenkomst wordt gevonden, wordt null geretourneerd.
    public Product getProductById(int id) {
        for (Product product : this.products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Laadt productgegevens uit het opgegeven bestandspad.
    // Verwacht een CSV-bestandsindeling met drie kolommen: ID, naam en vervaldatum.
    // Als het bestand niet kan worden gevonden, wordt een foutbericht weergegeven.
    // Na het laden van de producten, worden eventuele niet-gebruikte ID's toegevoegd aan de lijst van beschikbare ID's.
    private void loadProductsFromFile() {
        File file = new File(filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int highestId = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].substring(parts[0].indexOf(":") + 2));
                    highestId = Math.max(highestId, id);

                    String name = parts[1].substring(parts[1].indexOf(":") + 2);
                    LocalDate expirationDate = LocalDate.parse(parts[2].substring(parts[2].indexOf(":") + 2),
                            formatter);

                    Product product = new Product(id, name, expirationDate);

                    boolean productExists = products.stream().anyMatch(p -> p.getId() == id);
                    if (!productExists) {
                        products.add(product);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(" " + e.getMessage());
        }

        for (int i = 1; i <= highestId; i++) {
            final int id = i;
            boolean idUsed = products.stream().anyMatch(p -> p.getId() == id);
            if (!idUsed) {
                availableIDs.add(id);
            }
        }
    }


    // Slaat de gegevens van het opgegeven product op naar het bestand.
    // Gebruikt het opgegeven bestandspad en formatteert de vervaldatum volgens het patroon "dd-MM-yyyy".
    // Voegt de productdetails toe aan het bestand in een CSV-indeling met kolommen voor ID, naam en vervaldatum.
    // Als er een fout optreedt tijdens het schrijven naar het bestand, wordt een foutbericht weergegeven.
    private void saveProductToFile(Product product) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (FileWriter fw = new FileWriter(filePath, true); PrintWriter pw = new PrintWriter(fw)) {
            String formattedDate = product.overdatum.format(formatter);

            String productDetails = String.format("ID: %d, Naam: %s, Datum: %s",
                    product.getId(), product.naam, formattedDate);
            pw.println(productDetails);
        } catch (IOException e) {
            System.err.println();
        }
    }


    // Werkt het productbestand bij met de gegevens van de huidige productenlijst.
    // Gebruikt het opgegeven bestandspad en formatteert de vervaldatums volgens het patroon "dd-MM-yyyy".
    // Overschrijft het bestand met de bijgewerkte productdetails in een CSV-indeling met kolommen voor ID, naam en vervaldatum.
    // Als er een fout optreedt tijdens het schrijven naar het bestand, wordt een foutbericht weergegeven.
    private void updateProductFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (PrintWriter pw = new PrintWriter(filePath)) {
            for (Product product : products) {
                String formattedDate = product.overdatum.format(formatter);
                String productDetails = String.format("ID: %d, Naam: %s, Datum: %s",
                        product.getId(), product.naam, formattedDate);
                pw.println(productDetails);
            }
        } catch (IOException e) {
            System.err.println();
        }
    }

    // Verzendt de lijst van producten per e-mail naar de opgegeven ontvanger.
    // Controleert eerst of het bestand met de productenlijst bestaat.
    // Stelt het onderwerp en de inhoud van de e-mail in.
    // Maakt gebruik van de Email klasse om de e-mail met bijlage te versturen.
    // Geeft een melding weer nadat de e-mail is verzonden.
    @Override
    public void sendProductListByEmail(String recipientEmail) {
        File productListFile = new File(filePath);
        if (!productListFile.exists()) {
            System.out.println("Productenlijst bestand bestaat niet.");
            return;
        }

        Email emailSender = new Email();
        String subject = "Productenlijst";
        String body = "Hierbij de productenlijst als bijlage.\n" +
                "\n" +
                "|￣￣￣￣￣￣￣￣￣￣￣|\n" +
                "                 Thanks for\n" +
                "                  using\n" +
                "                  Freezer!\n" +
                "|＿＿＿＿＿＿＿＿＿＿＿| \n" +
                "                \\ (•◡•) / \n" +
                "                  \\      / \n" +
                "                    ---\n" +
                "                    |   |\n" +
                "\n" +
                "\n" +
                "Met vriendelijke groet,\n" +
                "Team Freezer";

        emailSender.sendEmailWithAttachment(recipientEmail, subject, body, productListFile);
        System.out.println("E-mail is verzonden naar " + recipientEmail);
    }
}
