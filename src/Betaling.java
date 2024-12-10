import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
public class Betaling
{
    private double beloeb;
    private boolean status;
    private String betalingsDato;
    private String udloebsDato;

    public Betaling()
    {
    }

    public Betaling (double beloeb)
    {
        this.beloeb = beloeb;
        this.status = false;
        this.betalingsDato = null;
        this.udloebsDato = null;
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

    public String getUdloebsDato() {
        return udloebsDato;
    }

    public void registrerBetaling(String betalingsDato) {
        this.status = true;
        this.betalingsDato = betalingsDato;
        this.udloebsDato = beregnUdloebsDato(betalingsDato);
    }

    private String beregnUdloebsDato(String betalingsDato) {
        // Formater dato-strengen til LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dato = LocalDate.parse(betalingsDato, formatter);

        // Tilføj et år til betalingsdatoen
        LocalDate udloebsDato = dato.plusYears(1);

        // Returner som formatteret string
        return udloebsDato.format(formatter);
    }
}