import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Persistens {
    private static final String FILNAVN = "svømmeklub_data.txt";
    private static final String FILNAVN2 = "svømmeresultater.txt";
    private static final String FILNAVN3 = "træner_data.txt";

    // Metode til at gemme data
    public static void gemData(List<Medlemmer> medlemmerListe) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILNAVN))) {
            for (Medlemmer medlem : medlemmerListe) {
                String data = formatMedlem(medlem);
                writer.write(data);
                writer.newLine();
            }
            System.out.println("Data gemt succesfuldt!");
        } catch (IOException e) {
            System.out.println("Fejl ved gemning af data: " + e.getMessage());
        }
    }

    // Metode til at læse data
    public static List<Medlemmer> laesData() {
        List<Medlemmer> medlemmerListe = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILNAVN))) {
            StringBuilder medlemData = new StringBuilder();
            String linje;

            while ((linje = reader.readLine()) != null) {
                if (linje.trim().isEmpty()) {
                    if (medlemData.length() > 0) {
                        try {
                            Medlemmer medlem = parseMedlem(medlemData.toString());
                            medlemmerListe.add(medlem);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Springer ugyldig medlem: " + medlemData);
                        }
                        medlemData.setLength(0);
                    }
                } else {
                    medlemData.append(linje).append("%n");
                }
            }
            if (medlemData.length() > 0) {
                try {
                    Medlemmer medlem = parseMedlem(medlemData.toString());
                    medlemmerListe.add(medlem);
                } catch (IllegalArgumentException e) {
                    System.out.println("Springer ugyldig medlem: " + medlemData);
                }
            }

            System.out.println("Data indlæst succesfuldt!");
        } catch (FileNotFoundException e) {
            System.out.println("Ingen tidligere data fundet. Starter med tom liste.");
        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af data: " + e.getMessage());
        }
        return medlemmerListe;
    }

    private static String formatMedlem(Medlemmer medlem) {
        return String.format(
                "Navn: %s%nTelefonnummer: %s%nEmail: %s%nAdresse: %s%nCPR: %s%nMedlemsID: %d%nAktiv: %b%nKonkurrencesvømmer: %b%nBetalt: %b%nBetalingsdato: %s%nUdløbsdato: %s%n",
                medlem.getNavn(),
                medlem.getTelefonnummer(),
                medlem.getEmail(),
                medlem.getAdresse(),
                medlem.getCprNr().getCprNr(),
                medlem.getMedlemsID(),
                medlem.isAktiv(),
                medlem.isKonkurrencesvoemmer(),
                medlem.getBetaling().isStatus(),
                medlem.getBetaling().getBetalingsDato() != null ? medlem.getBetaling().getBetalingsDato() : "Ingen",
                medlem.getBetaling().getUdloebsDato() != null ? medlem.getBetaling().getUdloebsDato() : "Ingen");
    }
    private static Medlemmer parseMedlem(String linje) {
        String[] lines = linje.split("%n");
        String navn = lines[0].split(": ")[1].trim();
        int telefonnummer = Integer.parseInt(lines[1].split(": ")[1].trim());
        String email = lines[2].split(": ")[1].trim();
        String adresse = lines[3].split(": ")[1].trim();
        CprNr cprNr = new CprNr(lines[4].split(": ")[1].trim());
        int medlemsID = Integer.parseInt(lines[5].split(": ")[1].trim());
        boolean aktiv = Boolean.parseBoolean(lines[6].split(": ")[1].trim());
        boolean konkurrencesvoemmer = Boolean.parseBoolean(lines[7].split(": ")[1].trim());
        boolean betalt = Boolean.parseBoolean(lines[8].split(": ")[1].trim());
        String betalingsDato = lines[9].split(": ")[1].trim().equals("Ingen") ? null : lines[9].split(": ")[1].trim();
        String udloebsDato = lines[10].split(": ")[1].trim().equals("Ingen") ? null : lines[10].split(": ")[1].trim();

        if (konkurrencesvoemmer)
        {
            return new Konkurrencesvoemmer(navn, telefonnummer, email, adresse, cprNr, medlemsID, aktiv, cprNr.getAlder(), true);
        }
        else
        {
            return new Medlemmer(navn, telefonnummer, email, adresse, cprNr, medlemsID, aktiv, cprNr.getAlder(), false);
        }
    }

    public static void gemTid(int medlemsID, String disciplin, double tid, String dato, String navn) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILNAVN2, true))) {
            String data = String.format("%d;%s;%.2f;%s;%s", medlemsID, disciplin, tid, dato, navn);
            writer.write(data);
            writer.newLine();
            System.out.println("Svømmetid gemt succesfuldt!");
        } catch (IOException e) {
            System.out.println("Fejl ved gemning af svømmetid: " + e.getMessage());
        }
    }

    public static List<String> hentTop5Svømmetider(String disciplin) {
        String filnavn = "svømmeresultater.txt";
        List<String> tider = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filnavn))) {
            String linje;
            while ((linje = reader.readLine()) != null) {
                String[] data = linje.split(";");
                if (data[1].equalsIgnoreCase(disciplin)) {
                    tider.add(linje);
                }
            }
            tider.sort(Comparator.comparingDouble(s -> Double.parseDouble(s.split(";")[2].replace(",", "."))));
        } catch (IOException e)
        {
            System.out.println("Fejl ved læsning af svømmetider: " + e.getMessage());
        }
        return tider.size() > 5 ? tider.subList(0, 5) : tider;
    }

    private static Traener parseTraener(String traenerData) {
        String[] lines = traenerData.split("%n");
        if (lines.length != 5) {
            throw new IllegalArgumentException("Trænerdata skal have præcis 5 linjer.");
        }

        String navn = lines[0].split(": ")[1].trim();
        int telefonnummer = Integer.parseInt(lines[1].split(": ")[1].trim());
        String email = lines[2].split(": ")[1].trim();
        String adresse = lines[3].split(": ")[1].trim();
        int traenerID = Integer.parseInt(lines[4].split(": ")[1].trim());

        return new Traener(navn, telefonnummer, email, adresse, new CprNr("0000000000"), traenerID);
    }

    public static void gemTraenerData(List<Traener> traenerListe) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILNAVN3))) {
            for (Traener traener : traenerListe) {
                String data = formatTraener(traener);
                writer.write(data);
                writer.newLine();
            }
            System.out.println("Trænerdata gemt succesfuldt!");
        } catch (IOException e) {
            System.out.println("Fejl ved gemning af trænerdata: " + e.getMessage());
        }
    }

    public static List<Traener> laesTraenerData() {
        List<Traener> traenerListe = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILNAVN3))) {
            StringBuilder traenerData = new StringBuilder();
            String linje;

            while ((linje = reader.readLine()) != null) {
                if (linje.trim().isEmpty()) {
                    if (traenerData.length() > 0) {
                        try {
                            Traener traener = parseTraener(traenerData.toString());
                            traenerListe.add(traener);
                        } catch (IllegalArgumentException e) {
                        }
                        traenerData.setLength(0);
                    }
                } else {
                    traenerData.append(linje).append("%n");
                }
            }
            if (traenerData.length() > 0) {
                try {
                    Traener traener = parseTraener(traenerData.toString());
                    traenerListe.add(traener);
                } catch (IllegalArgumentException e) {
                }
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return traenerListe;
    }
    private static String formatTraener (Traener traener) {
        return String.format(
                "Navn: %s%nTelefonnummer: %d%nEmail: %s%nAdresse: %s%nTræneerID: %d%n",
                traener.getNavn(),
                traener.getTelefonnummer(),
                traener.getEmail(),
                traener.getAdresse(),
                traener.getTraenerID());
    }
}