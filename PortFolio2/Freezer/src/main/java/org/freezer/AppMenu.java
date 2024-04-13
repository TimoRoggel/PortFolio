package org.freezer;

import java.util.Scanner;

public class AppMenu {
    private final Manager manager = new Manager(); // Manager object om de bedrijfslogica te beheren
    private final Scanner scanner = new Scanner(System.in); // Scanner object om gebruikersinvoer te lezen
    private final PrinterManager printerManager = new PrinterManager();

    /**
     * Beheert de hoofdloop en menukeuzes. Elke case in de switch-instructie correspondeert met een menu-optie verbonden aan 'displayMenu'.
     */
    public void run() {
        while (true) {
            displayMenu(); // Toont het hoofdmenu
            int choice = scanner.nextInt(); // Leest de keuze van de gebruiker
            scanner.nextLine(); // Leegt de rest van de invoerregel

            switch (choice) {
                case 1:
                    manager.addAndGenerateProduct(scanner); // Voegt een product toe en genereert een QR-code
                    break;
                case 2:
                    manager.removeProductAndQrCode(scanner); // Verwijdert een product en de bijbehorende QR-code
                    break;
                case 3:
                    manager.displayProductList(); // Toont de lijst van producten
                    break;
                case 4:
                    System.out.println("Sluiten..."); // Sluit het programma
                    scanner.close(); // Sluit de scanner om lekken te voorkomen
                    return;
                case 5:
                    System.out.println("Lijst wordt uitgeprint...");
                    printerManager.printProductList();
                    break;
                case 6:
                    System.out.println("Uw email:"); // Vraagt om het e-mailadres van de gebruiker
                    String emailAddress = scanner.nextLine(); // Leest het e-mailadres van de gebruiker
                    manager.sendProductListByEmail(emailAddress); // Verstuurt de lijst van producten via e-mail
                    break;
                default:
                    System.out.println("Verkeerde keuze. Probeer opnieuw"); // Geeft een foutmelding bij een ongeldige keuze
                    break;
            }
        }
    }

    /**
     * Toont het hoofdmenu met opties. Elke optie is gekoppeld aan een functie die beheerd wordt in de 'run' methode.
     */
    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Voeg Product Toe");
        System.out.println("2. Verwijderen Product");
        System.out.println("3. Product Lijst");
        System.out.println("4. Sluiten");
        System.out.println("-----------------------");
        System.out.println("5. Product Lijst Printen");
        System.out.println("6. Stuur lijst via email");
        System.out.println();
        System.out.print("Kies een optie: ");
    }
}
