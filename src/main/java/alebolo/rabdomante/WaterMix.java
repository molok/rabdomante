package alebolo.rabdomante;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WaterMix extends Water {
    public WaterMix(List<Water> ws) {
        super(sum(ws, Water::liters)
                , sum(ws, Water::calcioMg)
                , sum(ws, Water::magnesioMg)
                , sum(ws, Water::sodioMg)
                , sum(ws, Water::bicarbonatiMg)
                , sum(ws, Water::solfatoMg)
                , sum(ws, Water::cloruroMg)
                , "mix " + normalize(groupBy(ws))
                , compose(groupBy(ws)));
    }

    private static String normalize(List<Water> ws) {
        return groupBy(ws).stream()
                .map(w -> w.name())
                .reduce((a, b) -> a + ", " + b)
                .get();
    }

    private static List<Water> groupBy(List<Water> ws) {
        return ws.stream()
                .filter(w -> w.liters() > 0.)
                .collect(Collectors.groupingBy(w -> w.name()))
                .entrySet().stream()
                .map(entry -> entry.getValue())
                .map(ls -> new Water(ls.stream().mapToDouble(i -> i.liters()).sum(), ls.get(0).profile()))
                .sorted()
                .collect(Collectors.toList());
    }

    private static Map<Water,Double> compose(List<Water> ws) {
        Double tot = ws.stream().mapToDouble(i -> i.liters()).sum();
        return ws.stream().collect(Collectors.toMap(k -> k, v -> v.liters() / tot));
    }

    private static double sum(List<Water> ws, Function<Water, Double> getter) {
        return ws.stream().map(getter).mapToDouble(i -> i).sum();
    }
}
