import java.util.ArrayList;
import java.util.List;

public class Traener extends Person
{
    private int traenerID;
    private List<Konkurrencesvoemmer> svoemmere;

    public Traener(String navn, int telefonnummer, String email, String adresse, CprNr cprNr, int traenerID)
    {
        super(navn, telefonnummer, email, adresse, cprNr);
        this.traenerID = traenerID;
        this.svoemmere = new ArrayList<>();
    }

    public int getTraenerID()
    {
        return traenerID;
    }

    public void tilfoejSvoemmer(Konkurrencesvoemmer svoemmer)
    {
        svoemmere.add(svoemmer);
    }

    public void fjernSvoemmer()
    {
        System.out.println("Konkurrencesvømmere under træner " + getNavn() + ":");
        for (Konkurrencesvoemmer sv : svoemmere) {
            System.out.println("- " + sv.getNavn() + " (ID: " + sv.getMedlemsID() + ")");
        }
    }
}