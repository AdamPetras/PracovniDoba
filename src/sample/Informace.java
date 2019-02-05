package sample;

/**
 * Created by Adam on 11.5.2015.
 */
public class Informace 
{
    private String datum;
    private double castka;
    private String hodiny;

    public Informace(String datum, String hodiny, double castka) {
        this.datum = datum;
        this.hodiny = hodiny;
        this.castka = castka;
    }

    public String getDatum() {
        return datum;
    }
    public String getHodiny() {
        return hodiny;
    }
    public String toString() {
        return hodiny;
    }
    public double getCastka() {
        return castka;
    }
}
