package org.freezer;

import java.util.Scanner;

/**
 * Een interface die de vereiste methoden definieert voor het beheer van producten.
 * Omvat methoden voor het toevoegen, verwijderen, tonen, en verzenden van productlijsten.
 */
public interface ProductManager {

    /**
     * Voegt een nieuw product toe en genereert een QR-code.
     * Gebruikt een scanner om invoer van de gebruiker te ontvangen.
     */
    void addAndGenerateProduct(Scanner scanner);

    /**
     * Verwijdert een product en de bijbehorende QR-code uit het systeem.
     * Vereist gebruikersinvoer via de scanner.
     */
    void removeProductAndQrCode(Scanner scanner);

    /**
     * Toont een lijst van alle producten in het systeem.
     */
    void displayProductList();

    /**
     * Verstuurt de productlijst naar een opgegeven e-mailadres.
     */
    void sendProductListByEmail(String recipientEmail);
}
