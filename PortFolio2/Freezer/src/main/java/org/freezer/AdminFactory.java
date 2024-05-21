package org.freezer;

public class AdminFactory implements UserFactory {
    @Override
    public AppMenu createAppMenu() {
        return new AppMenu(true); // True staat voor admin toegang
    }
}
