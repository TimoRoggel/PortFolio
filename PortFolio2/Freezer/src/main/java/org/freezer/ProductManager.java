package org.freezer;

import java.util.Scanner;

/**
 * Een interface die de vereiste methoden definieert voor het beheer van producten.
 * Omvat methoden voor het toevoegen, verwijderen, tonen, en verzenden van productlijsten.
 */
public interface ProductManager {
    void addAndGenerateProduct(Scanner scanner);

    void removeProductAndQrCode(Scanner scanner);

    void displayProductList();

    void sendProductListByEmail(String recipientEmail);
}
