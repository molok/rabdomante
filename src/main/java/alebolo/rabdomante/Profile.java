package alebolo.rabdomante;

import com.google.common.math.DoubleMath;

import java.util.Objects;

class Profile {
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;
    private final String name;

    Profile(double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro, String name) {
        this.calcio = calcio;
        this.magnesio = magnesio;
        this.sodio = sodio;
        this.bicarbonati = bicarbonati;
        this.solfato = solfato;
        this.cloruro = cloruro;
        this.name = name;
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

    @Override
    public int hashCode() {
        return Objects.hash(calcio, magnesio, sodio, bicarbonati, solfato, cloruro);
    }

    @Override
    public String toString() {
        return "Profile ("+name+"){" +
                "calcio=" + calcio +
                ", magnesio=" + magnesio +
                ", sodioRatio=" + sodio +
                ", bicarbonati=" + bicarbonati +
                ", solfato=" + solfato +
                ", cloruro=" + cloruro +
                '}';
    }

    public String name() { return name; }
}
