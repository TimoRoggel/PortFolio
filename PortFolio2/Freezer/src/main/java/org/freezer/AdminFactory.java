package org.freezer;

public class AdminFactory implements UserFactory {
    @Override
    public AppMenu createAppMenu() {
        return new AdminAppMenu();
    }
}
