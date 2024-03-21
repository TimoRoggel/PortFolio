package org.freezer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Manager {
    private final ArrayList<Product> products;
    private final String filePath = "/Users/timobrouwer/Documents/HHS/Semester 2/Project OPT1/" +
            "Freezer/src/main/java/org/freezer/producten.txt";

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

        System.out.println("Datum (bijv. 01-01-2024):");
        String expirationDate = scanner.nextLine();

        Product newProduct = new Product(productName, expirationDate);
        addProduct(newProduct);
        System.out.println("Product toegevoegd!");

        String qrData = "Product ID: " + newProduct.getId() + ", Naam: " + productName +
                ", Vervaldatum: " + expirationDate;
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
        System.out.println("Product Lijst:");
        for (Product product : products) {
            System.out.println("ID: " + product.getId() + ", Naam: " + product.naam + ", Datum: " + product.overdatum);
        }
    }


    /*
    Volgende 3 methodes spreken voor zich.
     */
    public void addProduct(Product product) {
        products.add(product);
        saveProductToFile(product);
    }

    public void deleteProduct(int productId) {
        products.removeIf(product -> product.getId() == productId);
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
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].substring(parts[0].indexOf(":") + 2));
                    String name = parts[1].substring(parts[1].indexOf(":") + 2);
                    String expirationDate = parts[2].substring(parts[2].indexOf(":") + 2);
                    Product product = new Product(name, expirationDate);
                    product.setId(id);
                    products.add(product);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println();
        }
    }

    private void saveProductToFile(Product product) {
        try (FileWriter fw = new FileWriter(filePath, true); PrintWriter pw = new PrintWriter(fw)) {
            String productDetails = String.format("ID: %d, Naam: %s, Datum: %s",
                    product.getId(), product.naam, product.overdatum);
            pw.println(productDetails);
        } catch (IOException e) {
            System.err.println();
        }
    }


    private void updateProductFile() {
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
            List<String> updatedContent = fileContent.stream()
                    .filter(line -> products.stream().anyMatch(p -> line.contains("Naam: " + p.naam) &&
                            line.contains("Datum: " + p.overdatum))).toList();

            try (PrintWriter pw = new PrintWriter(filePath)) {
                updatedContent.forEach(pw::println);
            }
        } catch (IOException e) {
            System.err.println();
        }
    }
}
