public abstract class Person
{
    protected String navn;
    protected int telefonnummer;
    protected String email;
    protected String adresse;
    protected CprNr cprNr;

    public Person ()
    {
    }

    public Person (String navn, int telefonnummer, String email, String adresse, CprNr cprNr)
    {
        this.navn = navn;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.adresse = adresse; 
        this.cprNr = cprNr;
    }

    public String getNavn()
    {
        return navn;
    }

    public int getTelefonnummer()
    {
        return telefonnummer;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public CprNr getCprNr()
    {
        return cprNr;
    }
}