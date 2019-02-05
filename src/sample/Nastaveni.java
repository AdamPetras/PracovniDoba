package sample;

/**
 * Created by Adam on 2.6.2015.
 */
public class Nastaveni
{
    private String defaultCastka;
    private String defaultHodiny;

    public Nastaveni()
    {
        defaultHodiny="5";
        defaultCastka="5";
    }

    public String getDefaultCastka() {
        return defaultCastka;
    }

    public void setDefaultCastka(String defaultCastka) {
        this.defaultCastka = defaultCastka;
    }

    public String getDefaultHodiny() {
        return defaultHodiny;
    }

    public void setDefaultHodiny(String defaultHodiny) {
        this.defaultHodiny = defaultHodiny;
    }
}
