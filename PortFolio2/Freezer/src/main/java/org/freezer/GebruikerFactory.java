package org.freezer;

public class GebruikerFactory implements UserFactory {
    @Override
    public AppMenu createAppMenu() {
        return new UserAppMenu();
    }
}
