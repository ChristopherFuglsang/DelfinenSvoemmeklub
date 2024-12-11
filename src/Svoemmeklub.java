import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Svoemmeklub {
    private static List<Medlemmer> medlemmerListe = new ArrayList<>();
    private static List<Traener> traenerListe = new ArrayList<>();

    public static void main(String[] args) {
        medlemmerListe = Persistens.laesData();
        if (medlemmerListe == null)
        {
            medlemmerListe = new ArrayList<>();
        }
        traenerListe = Persistens.laesTraenerData();
        if (traenerListe == null) {
            traenerListe = new ArrayList<>();
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Velkommen til Svømmeklubben Delfinens system!");
            System.out.println("1. Opret nyt medlem");
            System.out.println("2. Vis alle medlemmer");
            System.out.println("3. Beregning af kontingent");
            System.out.println("4. Registrer betaling af kontingent");
            System.out.println("5. Vis samlet kontingent for alle medlemmer");
            System.out.println("6. Vis medlemmer i restance");
            System.out.println("7. Opret en træner");
            System.out.println("8. Tildel træner til konkurrencesvømmer");
            System.out.println("9. Vis svømmere under en træner");
            System.out.println("10. Registrer konkurrenceresultater");
            System.out.println("11. Vis Top-5 svømmere");
            System.out.println("12. Afslut programmet");
            System.out.print("Vælg en mulighed: ");

            int valg = scanner.nextInt();
            scanner.nextLine();

            switch (valg) {
                case 1:
                    opretNytMedlem(scanner);
                    break;
                case 2:
                    visAlleMedlemmer();
                    break;
                case 3:
                    beregnKontingent(scanner);
                    break;
                case 4:
                    registrerBetaling(scanner);
                    break;
                case 5:
                    visSamletKontingent();
                    break;
                case 6:
                    visRestanceMedlemmer();
                    break;
                case 7:
                    opretTraener(scanner);
                    break;
                case 8:
                    tildelTraener(scanner);
                    break;
                case 9:
                    visSvoemmereUnderTraener(scanner);
                    break;
                case 10:
                    registrerResultat(scanner);
                    break;
                case 11:
                    visTop5(scanner);
                    break;
                case 12:
                    System.out.println("Gemmer data og programmet afsluttes.");
                    Persistens.gemData(medlemmerListe);
                    Persistens.gemTraenerData(traenerListe);
                    running = false;
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
        scanner.close();
    }

    private static void opretNytMedlem(Scanner scanner) {
        System.out.print("Indtast navn: ");
        String navn = scanner.nextLine();
        System.out.print("Indtast telefonnummer: ");
        int telefonnummer = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Indtast email: ");
        String email = scanner.nextLine();
        System.out.print("Indtast adresse: ");
        String adresse = scanner.nextLine();
        System.out.print("Indtast CPR-nummer: ");
        String cprNr = scanner.nextLine();

        CprNr cpr = new CprNr(cprNr);
        System.out.print("Indtast medlemsID: ");
        int medlemsID = scanner.nextInt();
        System.out.print("Er medlem aktiv? (true/false): ");
        boolean aktiv = scanner.nextBoolean();
        System.out.print("Er medlem konkurrencesvømmer? (true/false): ");
        boolean konkurrencesvømmer = scanner.nextBoolean();

        Medlemmer nytMedlem;
        if (konkurrencesvømmer) {
            nytMedlem = new Konkurrencesvoemmer(navn, telefonnummer, email, adresse, cpr, medlemsID, aktiv, cpr.getAlder(), true);
        } else {
            nytMedlem = new Medlemmer(navn, telefonnummer, email, adresse, cpr, medlemsID, aktiv, cpr.getAlder(), false);
        }
        medlemmerListe.add(nytMedlem);
    }

    private static void visAlleMedlemmer() {
        System.out.println("Viser alle medlemmer:");
        for (Medlemmer medlem : medlemmerListe) {
            System.out.println(medlem.getNavn() + " (ID: " + medlem.getMedlemsID() + ")");
        }
    }

    private static void beregnKontingent(Scanner scanner) {
        System.out.print("Indtast medlemsID: ");
        int medlemsID = scanner.nextInt();
        scanner.nextLine();

        Medlemmer medlem = findMedlemVedID(medlemsID);
        if (medlem != null) {
            double kontingent = medlem.beregnKontingent();
            System.out.println("Kontingent for medlem " + medlem.getNavn() + ": " + kontingent + " kr.");
        } else {
            System.out.println("Medlem ikke fundet.");
        }
    }

    private static void registrerBetaling(Scanner scanner) {
        System.out.print("Indtast medlemsID for at registrere betaling: ");
        int medlemsID = scanner.nextInt();
        scanner.nextLine(); // Ryd scanner-bufferen

        Medlemmer medlem = findMedlemVedID(medlemsID);
        if (medlem != null) {
            if (erBetalt(medlem)) {
                System.out.println("Medlemmet har allerede betalt.");
                return;
            }
            System.out.print("Indtast betalingsdato (format: ÅÅÅÅ-MM-DD): ");
            String betalingsDato = scanner.nextLine();

            medlem.getBetaling().registrerBetaling(betalingsDato);
            System.out.println("Betaling registreret for " + medlem.getNavn() + ". Betalingsdato: " + betalingsDato);
        } else {
            System.out.println("Medlem med ID " + medlemsID + " blev ikke fundet.");
        }
    }

    private static void visSamletKontingent()
    {
        double samletKontingent = 0.0;
        for (Medlemmer medlem : medlemmerListe)
        {
            samletKontingent += medlem.beregnKontingent();
        }
        System.out.println("Den samlede kontingent for alle medlemmer er: " + samletKontingent + " kr.");
    }

    private static void visRestanceMedlemmer() {
        System.out.println("Medlemmer i restance:");
        boolean found = false;

        for (Medlemmer medlem : medlemmerListe) {
            if (!erBetalt(medlem)) {
                found = true;
                System.out.println("Navn: " + medlem.getNavn() + ", ID: " + medlem.getMedlemsID() + " | Kontigent: " + medlem.beregnKontingent()+"kr.");
            }
        }
        if (!found) {
            System.out.println("Ingen medlemmer i restance.");
        }
    }

    private static boolean erBetalt(Medlemmer medlem) {
        Betaling betaling = medlem.getBetaling();
        return betaling != null && betaling.isStatus();
    }

    private static Medlemmer findMedlemVedID(int medlemsID) {
        for (Medlemmer medlem : medlemmerListe) {
            if (medlem.getMedlemsID() == medlemsID) {
                return medlem;
            }
        }
        return null;
    }

    private static void opretTraener(Scanner scanner) {
        System.out.print("Indtast navn: ");
        String navn = scanner.nextLine();
        System.out.print("Indtast telefonnummer: ");
        int telefonnummer = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Indtast email: ");
        String email = scanner.nextLine();
        System.out.print("Indtast adresse: ");
        String adresse = scanner.nextLine();
        System.out.print("Indtast trænerID: ");
        int traenerID = scanner.nextInt();
        scanner.nextLine();

        Traener nyTraener = new Traener(navn, telefonnummer, email, adresse, new CprNr("0000000000"), traenerID);
        traenerListe.add(nyTraener);
        Persistens.gemTraenerData(traenerListe);
        System.out.println("Træner tilføjet: " + nyTraener.getNavn());
    }

    private static void tildelTraener(Scanner scanner) {
        System.out.print("Indtast medlemsID for konkurrencesvømmeren: ");
        int medlemsID = scanner.nextInt();
        scanner.nextLine();

        Medlemmer medlem = findMedlemVedID(medlemsID);
        if (!(medlem instanceof Konkurrencesvoemmer)) {
            System.out.println("Kun konkurrencesvømmere kan tildeles en træner.");
            return;
        }

        Konkurrencesvoemmer svoemmer = (Konkurrencesvoemmer) medlem;

        if (svoemmer.getTraener() != null) {
            System.out.println("Denne svømmer har allerede en træner: " + svoemmer.getTraener().getNavn());
            return;
        }

        if (traenerListe.isEmpty()) {
            System.out.println("Der er ingen tilgængelige trænere. Opret en træner først.");
            return;
        }

        System.out.println("Tilgængelige trænere:");
        for (int i = 0; i < traenerListe.size(); i++) {
            Traener traener = traenerListe.get(i);
            System.out.printf("%d. %s (ID: %d)%n", i + 1, traener.getNavn(), traener.getTraenerID());
        }

        System.out.print("Vælg en træner ved at indtaste nummeret: ");
        int traenerValg = scanner.nextInt();
        scanner.nextLine();

        if (traenerValg < 1 || traenerValg > traenerListe.size()) {
            System.out.println("Ugyldigt valg.");
            return;
        }

        Traener valgtTraener = traenerListe.get(traenerValg - 1);

        svoemmer.setTraener(valgtTraener);
        valgtTraener.tilfoejSvoemmer(svoemmer);

        System.out.println("Træner " + valgtTraener.getNavn() + " tildelt til " + svoemmer.getNavn());
    }


    private static void visSvoemmereUnderTraener(Scanner scanner) {
        System.out.println("Tilgængelige trænere:");
        for (int i = 0; i < traenerListe.size(); i++) {
            Traener traener = traenerListe.get(i);
            System.out.printf("%d: %s (ID: %d)%n", i + 1, traener.getNavn(), traener.getTraenerID());
        }

        System.out.print("Vælg en træner ved at indtaste nummeret: ");
        int traenerValg = scanner.nextInt();
        scanner.nextLine();

        if (traenerValg < 1 || traenerValg > traenerListe.size()) {
            System.out.println("Ugyldigt valg.");
            return;
        }

        Traener valgtTraener = traenerListe.get(traenerValg - 1);
        valgtTraener.fjernSvoemmer();
    }

    private static void registrerResultat(Scanner scanner) {
        System.out.print("Indtast medlemsID: ");
        int medlemsID = scanner.nextInt();
        scanner.nextLine(); // Clear scanner buffer

        System.out.print("Indtast disciplin (BUTTERFLY/CRAWL/RYGCRAWL/BRYSTSVØMNING): ");
        String disciplin = scanner.nextLine();

        System.out.print("Indtast tid: ");
        double tid = scanner.nextDouble();
        scanner.nextLine(); // Clear scanner buffer

        System.out.print("Indtast dato (år-måned-dag): ");
        String dato = scanner.nextLine();

        // Retrieve the member by ID
        Medlemmer medlem = findMedlemVedID(medlemsID);
        if (medlem != null) {
            String navn = medlem.getNavn(); // Get the name of the member
            // Call Persistens to save the result
            Persistens.gemTid(medlemsID, disciplin, tid, dato, navn);
            System.out.println("Resultat registreret: Navn: " + navn + ", MedlemsID: " + medlemsID + ", Disciplin: " + disciplin + ", Tid: " + tid + ", Dato: " + dato);
        } else {
            System.out.println("Medlem med ID " + medlemsID + " blev ikke fundet.");
        }
    }

    private static void visTop5(Scanner scanner)
    {
        System.out.print("Indtast disciplin (BUTTERFLY/CRAWL/RYGCRAWL/BRYSTSVØMNING): ");
        String disciplin = scanner.nextLine();

        List<String> top5Tider = Persistens.hentTop5Svømmetider(disciplin);

        System.out.println("Top-5 tider for disciplin: " + disciplin);
        if (top5Tider.isEmpty()) {
            System.out.println("Ingen tider registreret for denne disciplin.");
        } else {
            for (int i = 0; i < top5Tider.size(); i++) {
                String[] data = top5Tider.get(i).split(";");
                System.out.printf("%d. MedlemsID: %s, Tid: %s, Dato: %s. Navn: %s%n" ,
                        i + 1, data[0], data[2], data[3], data[4]);
            }
        }
    }
}