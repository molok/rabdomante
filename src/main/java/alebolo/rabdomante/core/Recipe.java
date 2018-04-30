package alebolo.rabdomante.core;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Recipe {
    public final List<Water> waters;
    public final List<Salt> salts;
    public final int distanceFromTarget;

    public Recipe(List<Water> waters, List<Salt> salts, int distanceFromTarget) {
        this.waters = waters;
        this.salts = salts;
        this.distanceFromTarget = distanceFromTarget;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "waters=" + ingredientsToString(waters) +
                ",\nsalts=" + ingredientsToString(salts) +
                ",\ndistanceFromTarget=" + distanceFromTarget +
                ", mix: \n" + mix() +
                '}';
    }

    private String mix() {
        return "Ca (mg/L): "   + ca() +
            ",\nMg (mg/L): "   + mg() +
            ",\nNa (mg/L): "   + na() +
            ",\nSO4 (mg/L): "  + so4() +
            ",\nCl (mg/L): "   + cl() +
            ",\nHCO3 (mg/L): " + hco3();

    }

    public int liters() { return waters.stream().mapToInt(w1 -> w1.liters).sum(); }

    public int hco3() { return weightedSum(waters, w -> w.hco3, salts, s -> s.hco3, liters()); }
    public int cl()   { return weightedSum(waters, w -> w.cl, salts, s -> s.cl, liters()); }
    public int so4()  { return weightedSum(waters, w -> w.so4, salts, s -> s.so4, liters()); }
    public int na()   { return weightedSum(waters, w -> w.na, salts, s -> s.na, liters()); }
    public int mg()   { return weightedSum(waters, w -> w.mg, salts, s -> s.mg, liters()); }
    public int ca()   { return weightedSum(waters, w -> w.ca, salts, s -> s.ca, liters()); }

    private int weightedSum(List<Water> wvs,
                            Function<Water, Integer> waterGetter,
                            List<Salt> ss,
                            Function<Salt, Integer> saltGetter,
                            int liters) {
        int wSum = wvs.stream().mapToInt(w -> waterGetter.apply(w) * w.liters).sum();
        int sSum = ss.stream().mapToInt(w -> saltGetter.apply(w) * w.dg).sum();
        return (wSum + sSum)/ liters;
    }

    private String ingredientsToString(List<?> list) {
        return list.stream()
            .map(e -> "\n" + e.toString())
            .collect(Collectors.joining()) + "\n";


    }
}
