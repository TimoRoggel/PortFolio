package org.freezer;

public class Product {
    private static int lastID = 0;
    String naam;
    String overdatum;
    int id;

    public Product(String naam, String overdatum) {
        this.naam = naam;
        this.overdatum = overdatum;
        this.id = ++lastID;
    }

    public Product(int id, String naam, String overdatum) {
        this.naam = naam;
        this.overdatum = overdatum;
        this.id = id;
        lastID = Math.max(lastID, id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
