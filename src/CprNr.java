import java.time.LocalDate;

public class CprNr
{
    String nummeret;

    public CprNr() {}

    public CprNr(String n)
    {
        nummeret = n;
    }

    public String getCprNr()
    {
        return nummeret;
    }

    public void setCprNr(String n)
    {
        nummeret = n;
    }

    public int getDag()
    {
        return Integer.parseInt(nummeret.substring(0,2));
    }

    public int getMaaned()
    {
        return Integer.parseInt(nummeret.substring(2,4));
    }

    public int getAar()
    {
        int aarhundrede = 0;
        if (nummeret.charAt(6) >= '0' && nummeret.charAt(6) <= '3')
            aarhundrede = 1900;
        else
            aarhundrede = 2000;

        return aarhundrede + Integer.parseInt(nummeret.substring(4,6));
    }

    public Dato getDato()
    {
        Dato d = new Dato(getAar(),getMaaned(),getDag());
        return d;
    }

    public int getAlder()
    {
        LocalDate dd = LocalDate.now();
        Dato iDag = new Dato(dd.getYear(),dd.getMonthValue(),dd.getDayOfMonth());

        return (iDag.getDatoen() - getDato().getDatoen()) / 10000;
    }

    public boolean erMand()
    {
        if (Integer.parseInt(nummeret.substring(9,10)) % 2 != 0)
            return true;
        return false;
    }

    public boolean erKvinde()
    {
        return !erMand();
    }

    public boolean erValid()
    {
        if (nummeret.length() != 10)
            return false;

        for (int i = 0;i < 10; i++)
            if (nummeret.charAt(i) < '0' || nummeret.charAt(i) > '9')
                return false;

        if (!getDato().validerDato())
            return false;

        int udregnet = 0;
        int[] vaegte = {4,3,2,7,6,5,4,3,2,1};

        for (int i = 0; i < 10; i++)
            udregnet += vaegte[i] * Integer.parseInt(""+nummeret.charAt(i));
        //udregnet += vaegte[i] * (nummeret.charAt(i) - '0');
        if (udregnet % 11 != 0)
            return false;

        return true;
    }
}