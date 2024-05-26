    package org.freezer;

    import java.util.Scanner;

    public class AdminAppMenu extends AppMenu {
        private final Scanner scanner = new Scanner(System.in);
        private final Manager manager = new Manager();
        private final PrinterManager printerManager = new PrinterManager();

        @Override
        public void run() {
            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Voeg Product Toe");
                System.out.println("2. Verwijderen Product");
                System.out.println("3. Product Lijst");
                System.out.println("4. Sluiten");
                System.out.println("-----------------------");
                System.out.println("5. Product Lijst Printen");
                System.out.println("6. Stuur lijst via email");
                System.out.print("Kies een optie: ");

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
                        System.out.println("Verkeerde keuze. Probeer opnieuw.");
                }
            }
        }
    }
