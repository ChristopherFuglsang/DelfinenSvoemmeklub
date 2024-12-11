import java.util.ArrayList;
import java.util.List;

public class Medlemmer extends Person
{
    private Betaling betaling;
    private int medlemsID;
    private boolean aktiv;
    private int alder;
    private boolean konkurrencesvoemmer;


    public Medlemmer()
    {
    }

    public Medlemmer (String navn, int telefonnummer, String email, String adresse, CprNr cprNr, int medlemsID, boolean aktiv, int alder, boolean konkurrencesvoemmer)
    {
        super(navn, telefonnummer, email, adresse, cprNr);
        this.medlemsID = medlemsID;
        this.aktiv = aktiv;
        this.alder = alder;
        this.konkurrencesvoemmer = konkurrencesvoemmer;
        this.betaling = new Betaling(beregnKontingent());
    }

    public int getMedlemsID()
    {
        return medlemsID;
    }

    public boolean isAktiv()
    {
        return aktiv;
    }

    public int getAlder()
    {
        return alder;
    }

    public boolean isKonkurrencesvoemmer()
    {
        return konkurrencesvoemmer;
    }

    public double beregnKontingent()
    {
        int alder = getCprNr().getAlder();
        if (!aktiv)
        {
            return 500.0; // Passivt medlemskab
        }
        if (alder < 18)
        {
            return 1000.0; // Junior
        } else if (alder >= 60)
        {
            return 1600.0 * 0.75; // Senior med rabat
        } else
        {
            return 1600.0; // Senior
        }
    }

    public Betaling getBetaling()
    {
        return betaling;
    }
}