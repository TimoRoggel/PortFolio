package org.freezer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {
    private Manager manager;
    private InputStream inputStream;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        manager = new Manager();
        String input = "Test Product\n31-12-2025\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
    }

    @Test
    void addAndGenerateProduct() {
        manager.addAndGenerateProduct(scanner);
        Product product = manager.getProductById(1);
        assertEquals("Test Product", product.naam);
        assertEquals(LocalDate.of(2025, 12, 31), product.overdatum);
    }

    @Test
    void removeProductAndQrCode() {
        manager.addAndGenerateProduct(scanner);
        manager.removeProductAndQrCode(new Scanner("1\n"));
        Product product = manager.getProductById(1);
        assertEquals(null, product);
    }
    @Test
    void displayProductList() {

        manager.addProduct(new Product("Test Product 1", LocalDate.of(2025, 12, 31)));
        manager.addProduct(new Product("Test Product 2", LocalDate.of(2025, 12, 31)));
        manager.addProduct(new Product("Test Product 3", LocalDate.of(2025, 12, 31)));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        manager.displayProductList();


        String expectedOutput = "Product Lijst:\n" +
                "ID: 1, Naam: Test Product 1, Datum: 31-12-2025\n" +
                "ID: 2, Naam: Test Product 2, Datum: 31-12-2025\n" +
                "ID: 3, Naam: Test Product 3, Datum: 31-12-2025\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
