package alebolo.rabdomante;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Finder {
    static double diffCoeff(Water target, Water candidate) {
        return Math.abs(target.calcioMg() - candidate.calcioMg()) +
               Math.abs(target.magnesioMg() - candidate.magnesioMg()) +
               Math.abs(target.sodioMg() - candidate.sodioMg()) +
               Math.abs(target.bicarbonatiMg() - candidate.bicarbonatiMg()) +
               Math.abs(target.solfatoMg() - candidate.solfatoMg()) +
               Math.abs(target.cloruroMg() - candidate.cloruroMg());
    }
    public static List<Water> top(int n, Water target, List<Water> waters, List<SaltAddition> salts) {

        return waters.parallelStream()
                .flatMap(w -> saltsCombinations(w, salts).stream())
                .map(c -> new Pair<>(c, diffCoeff(target, c)))
                .sorted(Comparator.comparingDouble(Pair::getValue1))
                .map(pair -> pair.getValue0())
                .limit(n)
                .collect(Collectors.toList());
    }

    public static Water closest(Water target, List<Water> waters, List<SaltAddition> salts) {
        return top(1, target, waters, salts).get(0);
    }

    public static Water closest(Water target, List<Water> waters) {
        return closest(target, waters, new ArrayList<>());
    }

    private static List<Water> saltsCombinations(Water w, List<SaltAddition> salts) {
        List<Water> res = new ArrayList<>();
        res.add(w);
        for (SaltAddition s : salts) {
            res.addAll(saltAddition(res, s));
        }
        return res;
    }

    private static List<Water> saltAddition(List<Water> res, SaltAddition s) {
        List<Water> added = new ArrayList<>();
        for (Water wx : res) {
            double grams = s.grams();
            while (grams >= 0) {
                added.add(Modifier.add(wx, new SaltAddition(grams, s.profile())));
                grams -= 0.01;
            }
        }
        return added;
    }
}


class WaterProfile {
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;
    private final String name;

    WaterProfile(double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro, String name) {
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
        return "WaterProfile ("+name+"){" +
                "calcio=" + calcio +
                ", magnesio=" + magnesio +
                ", sodioPerc=" + sodio +
                ", bicarbonati=" + bicarbonati +
                ", solfato=" + solfato +
                ", cloruro=" + cloruro +
                '}';
    }

    public String name() { return name; }
}
