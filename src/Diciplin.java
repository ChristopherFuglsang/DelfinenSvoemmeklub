import java.time.LocalDate;
public class Diciplin
{
    protected String crawl;
    protected String butterfly;
    protected String bryst;
    protected String rygcrawl;
    protected double bedsteTid;
    protected Dato dato;

    public void Disciplin(String crawl, String butterfly, String bryst, String rygcrawl, double bedsteTid, Dato dato)
    {
        this.crawl = crawl;
        this.butterfly = butterfly;
        this.bryst = bryst;
        this.rygcrawl = rygcrawl;
        this.bedsteTid = bedsteTid;
        this.dato = dato;

    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }
}
