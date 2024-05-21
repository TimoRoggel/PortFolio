import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Oefening {
    String naam;
    String spiergroep;
    int aantalSets;
    int rusttijd;
    int aantalHerhalingen;

    public Oefening(String naam, String spiergroep, int aantalSets, int rusttijd, int aantalHerhalingen) {
        this.naam = naam;
        this.spiergroep = spiergroep;
        this.aantalSets = aantalSets;
        this.rusttijd = rusttijd;
        this.aantalHerhalingen = aantalHerhalingen;
    }

    public void toonGegevens() {
        System.out.printf("Oefening voor %s: herhaal %d keer (rust tussendoor %d seconden) %d %s%n",
                spiergroep, aantalSets, rusttijd, aantalHerhalingen, naam);
    }

    public boolean isOefening(String naam) {
        return this.naam.equals(naam);
    }
}

class Trainingsschema {
    String klantNaam;
    String trainer;
    List<Oefening> oefeningen;

    public Trainingsschema(String klantNaam) {
        this.klantNaam = klantNaam;
        this.oefeningen = new ArrayList<>();
    }

    public Trainingsschema(String klantNaam, String trainer) {
        this.klantNaam = klantNaam;
        this.trainer = trainer;
        this.oefeningen = new ArrayList<>();
    }

    public void voegOefeningToe(Oefening oefening) {
        oefeningen.add(oefening);
    }

    public boolean isSchemaVoor(String klant) {
        return klantNaam.equalsIgnoreCase(klant);
    }

    public void toonGegevens() {
        if (this.trainer != null) {
            System.out.println("Uw trainer: " + this.trainer);
        }
        for (Oefening oefening : oefeningen) {
            oefening.toonGegevens();
        }
    }
}

public class Main {
    static List<Oefening> beschikbareOefeningen = new ArrayList<>();
    static List<Trainingsschema> trainingsschemas = new ArrayList<>();

    public static void main(String[] args) {
        beschikbareOefeningen.add(new Oefening("Push-ups", "Armen", 3, 30, 3));
        beschikbareOefeningen.add(new Oefening("Sit-ups", "Buik", 3, 30, 4));
        beschikbareOefeningen.add(new Oefening("Squats", "Benen", 2, 30, 5));
        beschikbareOefeningen.add(new Oefening("Supermans", "Rug", 3, 15, 3));
        beschikbareOefeningen.add(new Oefening("Chest-dips", "Borst", 3, 30, 3));

        Trainingsschema arnoldSchema = new Trainingsschema("Arnold Schwarzenegger");
        arnoldSchema.voegOefeningToe(vindOefening("Push-ups"));
        arnoldSchema.voegOefeningToe(vindOefening("Sit-ups"));
        arnoldSchema.voegOefeningToe(vindOefening("Squats"));
        arnoldSchema.voegOefeningToe(vindOefening("Supermans"));
        arnoldSchema.voegOefeningToe(vindOefening("Chest-dips"));
        trainingsschemas.add(arnoldSchema);

        Trainingsschema kimSchema = new Trainingsschema("Kim Kardashian", "Daphne Jongejans");
        kimSchema.voegOefeningToe(vindOefening("Supermans"));
        kimSchema.voegOefeningToe(vindOefening("Sit-ups"));
        trainingsschemas.add(kimSchema);

        System.out.println("Wat is uw naam? ");
        Scanner scanner = new Scanner(System.in);
        String naam = scanner.nextLine();

        Trainingsschema gebruikersSchema = null;
        for (Trainingsschema schema : trainingsschemas) {
            if (schema.isSchemaVoor(naam)) {
                gebruikersSchema = schema;
                break;
            }
        }

        if (gebruikersSchema != null) {
            gebruikersSchema.toonGegevens();
        } else {
            System.out.println("U komt niet voor in ons systeem.");
        }
    }

    public static Oefening vindOefening(String naam) {
        for (Oefening oefening : beschikbareOefeningen) {
            if (oefening.isOefening(naam)) {
                return oefening;
            }
        }
        return null;
    }
}
