public enum StilType
{
    BUTTERFLY("Butterfly"),
    CRAWL("Crawl"),
    RYGCRAWL ("Rygcrawl"),
    BRYSTSVØMNING("Brystsvømning");

    private String navn;

    StilType(String navn)
    {
        this.navn = navn;
    }

    public String getNavn()
    {
        return navn;
    }
}