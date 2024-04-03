package org.freezer;

import java.util.Scanner;

public class AppMenu {
    private Manager manager = new Manager();
    private Scanner scanner = new Scanner(System.in);

    /*
    Menu keuzes, zo staat case 1 voor optie 1.
    Dit staat gekoppeld aan de methode "displayMenu".
     */
    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

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
        System.out.println("1. Voeg Product Toe");
        System.out.println("2. Verwijderen Product");
        System.out.println("3. Product Lijst");
        System.out.println("4. Sluiten");
        System.out.println("-----------------------");
        System.out.println("5. Stuur lijst via email");
        System.out.println();
        System.out.print("Kies een optie: ");
    }
}
