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

    public boolean isOefening(String naam){
        return this.naam.equals(naam);
    }
}

class Trainingsschema {
    String klantNaam;
    String trainer;
    List<Oefening> oefeningen;

    public Trainingsschema(String klantNaam){
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

    public boolean isSchemaVoor(String klant){
        return klantNaam.equals(klant);
    }

    private void toonTrainer(){
        if(this.trainer == null){
            System.out.println("geen trainer");
        } else {
            System.out.println(this.trainer);
        }
    }

    public void toonGegevens(){
        if(this.trainer != null){
            System.out.println(this.trainer);
            for(Oefening oefening : oefeningen){
                System.out.printf("Oefening voor %s: Herhaal %d keer (rust door %d seconden) %d %s", oefening.spiergroep, oefening.aantalSets, oefening.rusttijd, oefening.aantalHerhalingen, oefening.naam);
            }
        } else {
            System.out.println("U komt niet in ons systeem voor");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Wat is uw naam? ");
        Scanner scanner = new Scanner(System.in);
        String naam = scanner.nextLine();

        if ("Arnold Schwarzenegger".equalsIgnoreCase(naam)) {
            Oefening pushUps = new Oefening("Push-ups", "Armen", 3, 30, 3);
            Oefening sitUps = new Oefening("Sit-ups", "Buik", 3, 30, 4);
            Oefening squats = new Oefening("Squats", "Benen", 2, 30, 5);
            Oefening supermans = new Oefening("Supermans", "Rug", 3, 15, 3);
            Oefening chestDips = new Oefening("Chest-dips", "Borst", 3, 30, 3);

            pushUps.toonGegevens();
            sitUps.toonGegevens();
            squats.toonGegevens();
            supermans.toonGegevens();
            chestDips.toonGegevens();
        } else if ("Kim Kardashian".equalsIgnoreCase(naam)) {
            System.out.println("Uw trainer: Daphne Jongejans");
            Oefening supermans = new Oefening("Supermans", "Rug", 3, 15, 3);
            Oefening sitUps = new Oefening("Sit-ups", "Buik", 3, 30, 4);

            supermans.toonGegevens();
            sitUps.toonGegevens();
        } else {
            System.out.println("U komt niet voor in ons systeem.");
        }
    }
}
