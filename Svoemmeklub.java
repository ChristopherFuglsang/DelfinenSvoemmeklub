import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Svoemmeklub
{
    private static List<Medlemmer> medlemmerListe = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Velkommen til Svømmeklubben Delfinens system!");
            System.out.println("1. Opret nyt medlem");
            System.out.println("2. Vis alle medlemmer");
            System.out.println("3. Beregning af kontingent");
            System.out.println("4. Vis samlet kontingent for alle medlemmer");
            System.out.println("5. Vis medlemmer i restance");
            System.out.println("6. Registrer konkurrenceresultater");
            System.out.println("7. Vis Top-5 svømmere");
            System.out.println("8. Afslut programmet");
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
                    visSamletKontingent();
                    break;
                case 5:
                    visRestanceMedlemmer();
                    break;
                case 6:
                    registrerResultat(scanner);
                    break;
                case 7:
                    visTop5();
                    break;
                case 8:
                    System.out.println("Programmet afsluttes.");
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

        Medlemmer nytMedlem = new Medlemmer(navn, telefonnummer, email, adresse, cpr, medlemsID, aktiv, cpr.getAlder(), konkurrencesvømmer);
        medlemmerListe.add(nytMedlem);
        System.out.println("Medlem oprettet: " + nytMedlem.getNavn());
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
                System.out.println("Navn: " + medlem.getNavn() + ", ID: " + medlem.getMedlemsID());
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

    private static void registrerResultat(Scanner scanner) {
        System.out.print("Indtast disciplin (BUTTERFLY/CRAWL/RYGCRAWL/BRYSTSVØMNING): ");
        String disciplinInput = scanner.nextLine();
        StilType disciplin = StilType.valueOf(disciplinInput.toUpperCase());

        System.out.print("Indtast tid: ");
        double tid = scanner.nextDouble();

        System.out.print("Indtast dato (år, måned, dag separat): ");
        int år = scanner.nextInt();
        int måned = scanner.nextInt();
        int dag = scanner.nextInt();

        Dato dato = new Dato(år, måned, dag);
        Resultat resultat = new Resultat(disciplin, tid, dato, ResultatType.TRÆNING);
        System.out.println("Resultat registreret for disciplin: " + disciplin.getNavn() + " med tid: " + tid);
    }

    private static void visTop5()
    {
        System.out.println("Viser Top-5 svømmere...");
    }
}
