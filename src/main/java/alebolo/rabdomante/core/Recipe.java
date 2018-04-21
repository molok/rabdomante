package alebolo.rabdomante.core;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Recipe {
    final List<Water> waters;
    final List<Salt> salts;
    int distanceFromTarget;

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
        int targetLiters = waters.stream().mapToInt(w -> w.liters).sum();
        return "Ca (mg/L): "   + weightedSum(waters, w -> w.ca, salts, s -> s.ca, targetLiters) +
            ",\nMg (mg/L): "   + weightedSum(waters, w -> w.mg, salts, s -> s.mg, targetLiters) +
            ",\nNa (mg/L): "   + weightedSum(waters, w -> w.na, salts, s -> s.na, targetLiters) +
            ",\nSO4 (mg/L): "  + weightedSum(waters, w -> w.so4, salts, s -> s.so4, targetLiters) +
            ",\nCl (mg/L): "   + weightedSum(waters, w -> w.cl, salts, s -> s.cl, targetLiters) +
            ",\nHCO3 (mg/L): " + weightedSum(waters, w -> w.hco3, salts, s -> s.hco3, targetLiters);

    }

    private int weightedSum(List<Water> wvs, Function<Water, Integer> waterGetter, List<Salt> ss, Function<Salt, Integer> saltGetter, int liters) {
        int wSum = wvs.stream().mapToInt(w -> waterGetter.apply(w) * w.liters).sum();
        int sSum = ss.stream().mapToInt(w -> saltGetter.apply(w) * w.dg).sum();
        return (wSum + sSum)/ liters;
    }

    private String ingredientsToString(List<? extends Object> list) {
        return list.stream()
            .map(e -> "\n" + e.toString())
            .collect(Collectors.joining()) + "\n";


    }
}
