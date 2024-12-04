import java.util.ArrayList;
import java.util.List;

public class Betaling
{
    private double beloeb;
    private boolean status;
    private String betalingsDato;

    public Betaling()
    {
    }

    public Betaling (double beloeb)
    {
        this.beloeb = beloeb;
        this.status = false;
        this.betalingsDato = null;
    }

    public double getBeloeb()
    {
        return beloeb;
    }

    public boolean isStatus()
    {
        return status;
    }

    public String getBetalingsDato()
    {
        return betalingsDato;
    }

    public void registrerBetaling (String betalingsDato)
    {
        this.status = true;
        this.betalingsDato = betalingsDato;
    }
}