package alebolo.rabdomante;

import org.javatuples.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Finder {
    static double diffCoeff(Water target, Water candidate) {
        return Math.abs(target.calcioMg() - candidate.calcioMg()) +
               Math.abs(target.magnesioMg() - candidate.magnesioMg()) +
               Math.abs(target.sodioMg() - candidate.sodioMg()) +
               Math.abs(target.bicarbonatiMg() - candidate.bicarbonatiMg()) +
               Math.abs(target.solfatoMg() - candidate.solfatoMg()) +
               Math.abs(target.cloruroMg() - candidate.cloruroMg());
    }

    public static Water closest(Water target, List<Water> candidates) {
        return candidates.stream()
                .map(c -> new Pair<>(c, diffCoeff(target, c)))
                .sorted(Comparator.comparingDouble(Pair::getValue1))
                .findFirst()
                .map(pair -> pair.getValue0())
                .get(); /* oh yeah */
    }
}


class WaterProfile {
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;

    WaterProfile(double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro) {
        this.calcio = calcio;
        this.magnesio = magnesio;
        this.sodio = sodio;
        this.bicarbonati = bicarbonati;
        this.solfato = solfato;
        this.cloruro = cloruro;
    }

    public double calcioMgPerL() { return calcio; }
    public double magnesioMgPerL() { return magnesio; }
    public double sodioMgPerL() { return sodio; }
    public double bicarbonatiMgPerL() { return bicarbonati; }
    public double solfatoMgPerL() { return solfato; }
    public double cloruroMgPerL() { return cloruro; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterProfile that = (WaterProfile) o;
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
        return "WaterProfile{" +
                "calcio=" + calcio +
                ", magnesio=" + magnesio +
                ", sodioPerc=" + sodio +
                ", bicarbonati=" + bicarbonati +
                ", solfato=" + solfato +
                ", cloruro=" + cloruro +
                '}';
    }
}
