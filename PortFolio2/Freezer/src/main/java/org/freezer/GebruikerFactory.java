package org.freezer;

public class GebruikerFactory implements UserFactory {
    @Override
    public AppMenu createAppMenu() {
        return new AppMenu(false); // False staat voor gewone gebruiker
    }
}
