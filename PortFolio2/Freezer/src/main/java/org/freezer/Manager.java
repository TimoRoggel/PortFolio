package org.freezer;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class Manager {
    private final ArrayList<Product> products;
    private final TreeSet<Integer> availableIDs = new TreeSet<>();
    private final String filePath = "/Users/timobrouwer/Documents/HHS/Github/PortFolio/PortFolio2/Freezer" +
            "/src/main/java/org/freezer/producten.txt";

    public Manager() {
        this.products = new ArrayList<>();
        loadProductsFromFile();
    }

    /*
    Deze methode voegt een product toe aan een lijst met de; Naam en Datum. Hiierbij wordt ook automatisch
    een ID gekoppeld zodat er dubbele producten in kunnen staan.
    Hierbij wordt ook een QRcode genereert.
     */
    public void addAndGenerateProduct(Scanner scanner) {
        System.out.println("Naam product:");
        String productName = scanner.nextLine();


        String expirationDateStr = null;
        LocalDate expirationDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while(expirationDate == null){
            System.out.println("Datum (bijv. 01-01-2024)");
            expirationDateStr = scanner.nextLine();

            try{
                expirationDate = LocalDate.parse(expirationDateStr, formatter);
            } catch (DateTimeParseException e){
                System.out.println("Ongeldige Datum, probeer opnieuw.");
            }
        }

        Product newProduct = new Product(productName, expirationDate);
        addProduct(newProduct);
        System.out.println("Product toegevoegd!");

        String qrData = "Product ID: " + newProduct.getId() + ", Naam: " + productName +
                ", Vervaldatum: " + formatter.format(expirationDate);
        String qrPath = "/Users/timobrouwer/Documents/HHS/Semester 2/Project OPT1/QRcode/"
                + newProduct.getId() + "_" + productName.replaceAll("\\s+", "_") + ".jpg";
        QRcode.generateQRCode(qrData, qrPath);

        // Nieuw: Afdrukken van de QR-code

    }

    /*
    In deze methode wordt een product verwijderd uit de lijst inclusief de QRcode.
     */
    public void removeProductAndQrCode(Scanner scanner) {
        System.out.println("ID van het product die u wilt verwijderen:");
        int productIdToRemove = scanner.nextInt();
        scanner.nextLine();

        Product productToRemove = getProductById(productIdToRemove);
        if (productToRemove == null) {
            System.out.println("Product niet gevonden!");
            return;
        }

        deleteProduct(productIdToRemove);
        System.out.println("Product met ID '" + productIdToRemove + "' verwijderd!");

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
    public void displayProductList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the European style date format
        System.out.println("Product Lijst:");
        for (Product product : products) {
            String formattedDate = product.overdatum.format(formatter); // Format the LocalDate to a String in the desired format
            System.out.println("ID: " + product.getId() + ", Naam: " + product.naam + ", Datum: " + formattedDate);
        }
    }



    /*
    Volgende 3 methodes spreken voor zich.
     */
    public void addProduct(Product product) {
        if (!availableIDs.isEmpty()) {
            int reusedId = availableIDs.pollFirst(); // Use and remove an available ID
            product.setId(reusedId);
        } else {
            // Generate a new ID, ensuring it's unique by starting from the highest current ID + 1
            int newId = products.isEmpty() ? 1 : products.stream().max(Comparator.comparingInt(Product::getId)).
                    get().getId() + 1;
            product.setId(newId);
        }

        products.add(product);
        saveProductToFile(product);
    }


    public void deleteProduct(int productId) {
        products.removeIf(product -> {
            boolean toRemove = product.getId() == productId;
            if (toRemove) {
                availableIDs.add(productId); // Add the ID back to available IDs
            }
            return toRemove;
        });
        updateProductFile();
    }


    public Product getProductById(int id) {
        for (Product product : this.products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /*
    Volgende 3 methodes zorgen zodat de txt files wordt bijgewerkt op basis van de functies die worden gebruikt.
     */
    private void loadProductsFromFile() {
        File file = new File(filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int highestId = 0; // To keep track of the highest ID encountered

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
            System.err.println("File not found: " + e.getMessage());
        }

        // Populate availableIDs with gaps in the sequence up to highestId
        for (int i = 1; i <= highestId; i++) {
            final int id = i;
            boolean idUsed = products.stream().anyMatch(p -> p.getId() == id);
            if (!idUsed) {
                availableIDs.add(id); // Add ID to availableIDs if it's not used
            }
        }
    }



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
