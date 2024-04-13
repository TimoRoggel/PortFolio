package org.freezer;

/**
 * De hoofdklasse van de applicatie die het startpunt bevat voor de uitvoering.
 */
public class Main {
    /**
     * De hoofdmethode die de applicatie start. Creëert een instantie van AppMenu en roept de run methode aan om het menu te starten.
     */
    public static void main(String[] args) {
        AppMenu menu = new AppMenu();
        menu.run();
    }
}
