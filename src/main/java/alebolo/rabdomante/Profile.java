package alebolo.rabdomante;

import com.google.common.math.DoubleMath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

class Profile implements IMgPerLDouble{
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;
    private final String name;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final int hashCode;

    public static final Profile levissima = new Profile( 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
    public static final Profile boario = new Profile( 131, 40, 5, 303, 240, 4, "boario");
    public static final Profile eva = new Profile( 10.2, 4, 0.28, 48, 1.7, 0.17, "evaProfile");
    public static final Profile santanna = new Profile( 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
    public static final Profile norda = new Profile( 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
    public static final Profile vera = new Profile( 35, 12.6, 2, 148, 19.2, 2.6, "vera");
    public static final Profile vitasnella = new Profile( 86, 26, 3, 301, 83, 2, "vitasnella");
    public static final Profile dolomiti = new Profile(8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");
    public static final Profile sanbern = new Profile(9.5, 0.6, 0.6, 30.2, 2.3, 0.7, "sanbernardo");
    public static final Profile distilled = new Profile(0, 0, 0, 0, 0, 0, "distilled");


    Profile(double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro, String name) {
        this.calcio = calcio;
        this.magnesio = magnesio;
        this.sodio = sodio;
        this.bicarbonati = bicarbonati;
        this.solfato = solfato;
        this.cloruro = cloruro;
        this.name = name;
        this.hashCode = Objects.hash(calcio, magnesio, sodio, bicarbonati, solfato, cloruro); /* tutto immutabile, no problem */
    }

    public double calcioMgPerL() { return calcio; }
    public double magnesioMgPerL() { return magnesio; }
    public double sodioMgPerL() { return sodio; }
    public double bicarbonatiMgPerL() { return bicarbonati; }
    public double solfatoMgPerL() { return solfato; }
    public double cloruroMgPerL() { return cloruro; }

    public boolean isSameAs(Profile wp) {
        return DoubleMath.fuzzyCompare(wp.calcioMgPerL(), this.calcioMgPerL(), 0.001) == 0 &&
               DoubleMath.fuzzyCompare(wp.magnesioMgPerL(), this.magnesioMgPerL(), 0.001) == 0 &&
               DoubleMath.fuzzyCompare(wp.sodioMgPerL(), this.sodioMgPerL(), 0.001) == 0 &&
               DoubleMath.fuzzyCompare(wp.bicarbonatiMgPerL(), this.bicarbonatiMgPerL(), 0.001) == 0 &&
               DoubleMath.fuzzyCompare(wp.solfatoMgPerL(), this.solfatoMgPerL(), 0.001) == 0 &&
               DoubleMath.fuzzyCompare(wp.cloruroMgPerL(), this.cloruroMgPerL(), 0.001) == 0;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile that = (Profile) o;
        return Double.compare(that.calcio, calcio) == 0 &&
                Double.compare(that.magnesio, magnesio) == 0 &&
                Double.compare(that.sodio, sodio) == 0 &&
                Double.compare(that.bicarbonati, bicarbonati) == 0 &&
                Double.compare(that.solfato, solfato) == 0 &&
                Double.compare(that.cloruro, cloruro) == 0;
    }

    @Override public int hashCode() { return hashCode; }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    public String name() { return name; }
}
