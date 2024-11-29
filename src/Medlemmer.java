public class Medlemmer extends Person
{
    private int     MedlemsID;
    private boolean aktiv;
    private int     alder;
    private boolean konkurrencesvoemmer;


    public Medlemmer(String navn, int telefonnummer, String email, String adresse, CprNr cprNr, int MedlemsID, boolean aktiv, int alder)
    {
        super(navn, telefonnummer, email, adresse, cprNr);
        this.MedlemsID = MedlemsID;
        this.aktiv = aktiv;
        this.alder = alder;
        this.konkurrencesvoemmer = konkurrencesvoemmer;
    }

    public int Kontigent()
    {
        if (!aktiv) return 500;
        if (alder <= 18) return 1000;
        if (alder >= 60) return (int) (1600 * 0.75);
        return 1600;
    }
    public boolean iskonkurrencesvoemmer() {
        return konkurrencesvoemmer;
    }

    public int getalder() {
        return alder;
    }

    public void setMedlemsID(int id) {
        this.MedlemsID = MedlemsID;
    }

    public int getMedlemsID() {
        return MedlemsID;
    }

}
