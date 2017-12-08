package alebolo.rabdomante;

import com.google.common.math.DoubleMath;

import java.util.Objects;

public class Water {
    private final double liters;
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;

    public Water(double liters, WaterProfile profile) {
        this(liters,profile.calcioMgPerL() * liters
        , profile.magnesioMgPerL() * liters
        , profile.sodioMgPerL() * liters
        , profile.bicarbonatiMgPerL() * liters
        , profile.solfatoMgPerL() * liters
        , profile.cloruroMgPerL() * liters);
    }

    public Water(double liters, double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro) {
        this.liters = liters;
        this.calcio = calcio;
        this.magnesio = magnesio;
        this.sodio = sodio;
        this.bicarbonati = bicarbonati;
        this.solfato = solfato;
        this.cloruro = cloruro;
    }

    public WaterProfile profile() {
        return new WaterProfile(calcioMg() / liters,
                magnesioMg() / liters,
                sodioMg() / liters,
                bicarbonatiMg() / liters,
                solfatoMg() / liters,
                cloruroMg() / liters);
    }

    public double calcioMg() { return calcio; }
    public double magnesioMg() { return magnesio; }
    public double sodioMg() { return sodio; }
    public double bicarbonatiMg() { return bicarbonati; }
    public double solfatoMg() { return solfato; }
    public double cloruroMg() { return cloruro; }
    public double liters() { return liters; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water water = (Water) o;
        return DoubleMath.fuzzyCompare(water.liters, liters, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.calcio, calcio, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.magnesio, magnesio, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.sodio, sodio, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.bicarbonati, bicarbonati, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.solfato, solfato, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.cloruro, cloruro, 0.001) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(liters, calcio, magnesio, sodio, bicarbonati, solfato, cloruro);
    }

    @Override
    public String toString() {
        return "Water{" +
                "liters=" + liters +
                ", calcio=" + calcio +
                ", magnesio=" + magnesio +
                ", sodio=" + sodio +
                ", bicarbonati=" + bicarbonati +
                ", solfato=" + solfato +
                ", cloruro=" + cloruro +
                '}';
    }
}
