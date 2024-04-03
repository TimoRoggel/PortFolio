package org.freezer;

import java.time.LocalDate;

public class Product {
    private static int lastID = 0;
    String naam;
    LocalDate overdatum;
    int id;

    public Product(String naam, LocalDate overdatum) {
        this.naam = naam;
        this.overdatum = overdatum;
    }

    public Product(int id, String naam, LocalDate overdatum) {
        this.naam = naam;
        this.overdatum = overdatum;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
