package org.freezer;

import java.time.LocalDate;

public class Product {
    private static final int lastID = 0; // Houdt het laatste gebruikte ID bij, momenteel niet gebruikt

    String naam; // Naam van het product
    LocalDate overdatum; // Vervaldatum van het product
    int id; // Uniek ID van het product

    // Constructor voor het maken van een nieuw product zonder ID
    public Product(String naam, LocalDate overdatum) {
        this.naam = naam;
        this.overdatum = overdatum;
    }

    // Constructor voor het maken van een nieuw product met een specifiek ID
    public Product(int id, String naam, LocalDate overdatum) {
        this.naam = naam;
        this.overdatum = overdatum;
        this.id = id;
    }

    // Methode om het ID van het product op te halen
    public int getId() {
        return id;
    }

    // Methode om het ID van het product in te stellen
    public void setId(int id) {
        this.id = id;
    }
}
