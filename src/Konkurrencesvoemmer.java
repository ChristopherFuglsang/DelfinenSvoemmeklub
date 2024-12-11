import java.util.ArrayList;
import java.util.List;

public class Konkurrencesvoemmer extends Medlemmer
{
    private List<Resultat> resultater;
    private Traener traener;

    public Konkurrencesvoemmer (String navn, int telefonNummer, String email, String adresse, CprNr cprNr, int medlemsID, boolean aktiv, int alder, boolean konkurrencesvoemmer)
    {
        super(navn, telefonNummer, email, adresse, cprNr, medlemsID, aktiv, alder, konkurrencesvoemmer);
        this.resultater = new ArrayList<>();
    }

    /* public void tilfoejResulat(Resultat resultat)
    {
        resultater.add(resultat);
    }

    public void visResultater()
    {
        System.out.println("Resultater for: " + getNavn());
        for (Resultat resultat : resultater)
        {
            System.out.println(resultat);
        }
    }

    public List<Resultat> visTop5BedsteResultater()
    {
        return resultater.stream()
                .sorted(Comparator.comparing(Resultat::getTid))
                .limit(5)
                .collect(Collectors.toList());
    }
*/
    public Traener getTraener()
    {
        return traener;
    }

    public void setTraener(Traener traener)
    {
        this.traener = traener;
    }
}