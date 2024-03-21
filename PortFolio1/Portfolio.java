import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Oefening {

    private String naam;
    private String spiergroep;
    private int aantalSets;
    private int aantalHerhalingen;
    private int rusttijd;

    public Oefening(String naam, String spiergroep, int aantalSets, int aantalHerhalingen, int rusttijd){
        this.naam = naam;
        this.spiergroep = spiergroep;
        this.aantalSets = aantalSets;
        this.aantalHerhalingen = aantalHerhalingen;
        this.rusttijd = rusttijd;
    }

    public void toonGegevens () {
        System.out.printf ("Oefening voor %s: herhaal %d keer " +
                        "(rust tussendoor %d seconden) %d %s%n",
                spiergroep, aantalSets, rusttijd, aantalHerhalingen, naam);
    }

    public boolean isOefening(String naam){
        return this.naam.equals(naam);
    }
}

class Trainingsschema {
    private String klant;
    private String trainer;
    private List<Oefening> oefeningen;

    public Trainingsschema(String klant){
        this.klant = klant;
        this.trainer = null;
        this.oefeningen = new ArrayList<>();
    }

    public Trainingsschema(String klant, String trainer){
        this(klant);
        this.trainer = trainer;
    }

    public void voegOefeningToe(Oefening oefening){
        this.oefeningen.add(oefening);
    }

    public boolean isSchemaVoor(String klant){
        return this.klant.equals(klant);
    }

    private void toonTrainer(){
        if(trainer != null){
            System.out.println("Uw trainer: " + trainer);
        }
    }

    public void toonGegevens(){
        toonTrainer();
        for(Oefening oefening : oefeningen){
            oefening.toonGegevens();
        }
    }
}

public class Main {

    public static void main (String [] args) {


        System.out.println ("Wat is uw naam? ");

        // Schrijf hieronder verder aan het hoofdprogramma in de methode 'main'.
        Scanner scanner = new Scanner(System.in);
        String naam = scanner.nextLine();

        Trainingsschema schemaArnold = new Trainingsschema("Arnold Schwarzenegger");
        schemaArnold.voegOefeningToe(new Oefening("Push-ups", "Armen", 3, 3, 30));
        schemaArnold.voegOefeningToe(new Oefening("Sit-ups", "Buik", 3, 4, 30));
        schemaArnold.voegOefeningToe(new Oefening("Squats", "Benen", 2, 5, 30));
        schemaArnold.voegOefeningToe(new Oefening("Supermans", "Rug", 3, 3, 15));
        schemaArnold.voegOefeningToe(new Oefening("Chest-dips", "Borst", 3, 3, 30));

        Trainingsschema schemaKim = new Trainingsschema("Kim Kardashian", "Daphne Jongejans");
        schemaKim.voegOefeningToe(new Oefening("Supermans", "Rug", 3, 3, 15));
        schemaKim.voegOefeningToe(new Oefening("Sit-ups", "Buik", 3, 4, 30));

        if (schemaArnold.isSchemaVoor(naam)) {
            schemaArnold.toonGegevens();
        } else if (schemaKim.isSchemaVoor(naam)) {
            schemaKim.toonGegevens();
        } else {
            System.out.println("U komt niet voor in ons systeem.");
        }
    }
}