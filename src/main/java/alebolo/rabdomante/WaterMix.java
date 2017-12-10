package alebolo.rabdomante;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WaterMix extends Water {
    private final static DecimalFormat mf = new DecimalFormat("###.##");

    @Override
    public String name() { throw new UnsupportedOperationException("mix non ha nome!"); }

    public WaterMix(List<Water> ws) {
        super(sum(ws, Water::liters)
                , sum(ws, Water::calcioMg)
                , sum(ws, Water::magnesioMg)
                , sum(ws, Water::sodioMg)
                , sum(ws, Water::bicarbonatiMg)
                , sum(ws, Water::solfatoMg)
                , sum(ws, Water::cloruroMg)
                , normalize(groupBy(ws))
                , compose(groupBy(ws)));
    }

    private static String normalize(List<Water> ws) {
        Double tot = tot(ws);
        return groupBy(ws).stream()
                .map(w -> String.format("%s%% %s", mf.format(100* w.liters() / tot), w.name()))
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
        return ws.stream().collect(Collectors.toMap(k -> k, v -> v.liters() / tot(ws)));
    }

    private static Double tot(List<Water> ws) {
        return ws.stream().mapToDouble(i -> i.liters()).sum();
    }

    private static double sum(List<Water> ws, Function<Water, Double> getter) {
        return ws.stream().map(getter).mapToDouble(i -> i).sum();
    }
}
