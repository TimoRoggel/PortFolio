package org.freezer;

import java.util.Scanner;

public class AppMenu {
    private final Manager manager = new Manager();
    private final Scanner scanner = new Scanner(System.in);
    private final PrinterManager printerManager = new PrinterManager();
    private boolean isAdmin;

    public AppMenu(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (!isAdmin && (choice == 1 || choice == 2)) {
                System.out.println("Geen toegangsrechten voor deze optie.");
                continue;
            }

            switch (choice) {
                case 1:
                    manager.addAndGenerateProduct(scanner);
                    break;
                case 2:
                    manager.removeProductAndQrCode(scanner);
                    break;
                case 3:
                    manager.displayProductList();
                    break;
                case 4:
                    System.out.println("Sluiten...");
                    scanner.close();
                    return;
                case 5:
                    System.out.println("Lijst wordt uitgeprint...");
                    printerManager.printProductList();
                    break;
                case 6:
                    System.out.println("Uw email:");
                    String emailAddress = scanner.nextLine();
                    manager.sendProductListByEmail(emailAddress);
                    break;
                default:
                    System.out.println("Verkeerde keuze. Probeer opnieuw");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        if (isAdmin) {
            System.out.println("1. Voeg Product Toe");
            System.out.println("2. Verwijderen Product");
        }
        System.out.println("3. Product Lijst");
        System.out.println("4. Sluiten");
        System.out.println("-----------------------");
        System.out.println("5. Product Lijst Printen");
        System.out.println("6. Stuur lijst via email");

        System.out.println();
        System.out.print("Kies een optie: ");
    }
}
