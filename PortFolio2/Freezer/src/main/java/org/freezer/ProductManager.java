package org.freezer;

import java.util.Scanner;

//Interface voor het beheren van producten.
public interface ProductManager {

    /**
     * Voegt een product toe en genereert een bijbehorende QR-code.
     * @param scanner Scanner object voor gebruikersinvoer.
     */
    void addAndGenerateProduct(Scanner scanner);

    /**
     * Verwijdert een product uit de lijst en de bijbehorende QR-code.
     * @param scanner Scanner object voor gebruikersinvoer.
     */
    void removeProductAndQrCode(Scanner scanner);

    //Geeft de lijst van producten weer.
    void displayProductList();

    /**
     * Verzendt de lijst van producten via e-mail.
     * @param recipientEmail Het e-mailadres van de ontvanger.
     */
    void sendProductListByEmail(String recipientEmail);
}
