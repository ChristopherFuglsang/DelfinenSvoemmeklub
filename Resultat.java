public class Resultat
{
    private StilType disciplin;
    private double tid;
    private Dato dato;
    private String staevne;
    private int placering;
    private ResultatType resultatType;

    public Resultat(StilType disciplin, double tid, Dato dato, ResultatType resultatType)
    {
        this.disciplin = disciplin;
        this.tid = tid;
        this.dato = dato;
        this.resultatType = resultatType;
    }

    public Resultat(StilType disciplin, double tid, Dato dato, String staevne, int placering)
    {
        this.disciplin = disciplin;
        this.tid = tid;
        this.dato = dato;
        this.staevne = staevne;
        this.placering = placering;
        this.resultatType = resultatType.KONKURRENCE;
    }

    public StilType getDisciplin()
    {
        return disciplin;
    }

    public double getTid()
    {
        return tid;
    }

    public Dato getDato()
    {
        return dato;
    }

    public String getStaevne()
    {
        return staevne;
    }

    public int getPlacering()
    {
        return placering;
    }

    public ResultatType getResultatType()
    {
        return resultatType;
    }
}