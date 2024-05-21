package org.freezer;

import java.util.Scanner;

public class User {
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.print("Voer de admin code in: ");
        String code = scanner.nextLine();

        UserFactory userFactory;
        if ("admin".equals(code)) {
            userFactory = new AdminFactory();
        } else {
            userFactory = new GebruikerFactory();
        }

        AppMenu appMenu = userFactory.createAppMenu();
        appMenu.run();
    }
}
