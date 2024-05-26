package org.freezer;

import java.util.Scanner;

public class User {
    private final Scanner scanner = new Scanner(System.in);
    UserFactory userFactory;

    public void run() {
        System.out.println("1. Admin");
        System.out.println("2. Gebruiker");
        System.out.println();


        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Voer de admin code in: ");
                String code = scanner.nextLine();
                if ("admin".equals(code)) {
                    userFactory = new AdminFactory();
                }
                break;
            case 2:
                userFactory = new GebruikerFactory();
                break;
            default:
                System.out.println("Verkeerde keuze.");

        }

        AppMenu appMenu = userFactory.createAppMenu();
        appMenu.run();
    }
}
