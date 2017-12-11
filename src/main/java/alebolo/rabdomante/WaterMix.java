package alebolo.rabdomante;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WaterMix implements IWater {
    private final static DecimalFormat mf = new DecimalFormat("###.##");
    private final List<Water> composition;
    private final double liters;

    @Override public double calcioMg() { return sum(composition, IWater::calcioMg); }
    @Override public double magnesioMg() { return sum(composition, IWater::magnesioMg); }
    @Override public double sodioMg() { return sum(composition, IWater::sodioMg); }
    @Override public double bicarbonatiMg() { return sum(composition, IWater::bicarbonatiMg); }
    @Override public double solfatoMg() { return sum(composition, IWater::solfatoMg); }
    @Override public double cloruroMg() { return sum(composition, IWater::cloruroMg); }
    @Override public double liters() { return sum(composition, IWater::liters); }

    @Override
    public List<SourceRatio> sources() {
        return composition.stream()
                .map(w -> new SourceRatio(w.profile(), w.liters() / liters))
                .collect(Collectors.toList());
    }

    public WaterMix(List<IWater> ws) {
        this.composition = flatten(ws);
        this.liters = tot(composition);
    }

    private List<Water> flatten(List<IWater> ws) {
        ws.stream()
          .flatMap(w -> w.sources().stream().map(sr -> sr.))
    }

    private static Double tot(List<? extends IWater> ws) {
        return ws.stream().mapToDouble(i -> i.liters()).sum();
    }


    private static double sum(List<? extends IWater> ws, Function<IWater, Double> getter) {
        return ws.stream().map(getter).mapToDouble(i -> i).sum();
    }

    public static IWater create(List<IWater> ws) {
        return new WaterMix(ws);
    }

    @Override
    public String toString() {
        return "Water (" + comp() +") {" +
                "liters=" + String.format("%.2f", liters()) +
                ", calcio=" + String.format("%.2f", calcioMg()) +
                ", magnesio=" + String.format("%.2f", magnesioMg()) +
                ", sodio=" + String.format("%.2f", sodioMg()) +
                ", bicarbonati=" + String.format("%.2f", bicarbonatiMg()) +
                ", solfato=" + String.format("%.2f", solfatoMg()) +
                ", cloruro=" + String.format("%.2f", cloruroMg()) +
                '}';
    }

    private String comp() {
        return ""; // TODO
    }

//    public String composition() {
//        return mixRatio(1., new HashMap<>()).entrySet().stream()
//                .map(e -> String.format("%sL of %s", mf.format(liters() * e.getValue()), e.getKey()))
//                .reduce((a, b) -> a + ", " + b)
//                .orElseThrow(() -> new RuntimeException("mybad"));
//    }

//    public Map<String, Double> mixRatio(Double currFraction, Map<String, Double> acc) {
//        if (this.composition.size() == 0) {
//            acc.put(this.profile().name(), currFraction + acc.getOrDefault(this.profile().name(), 0.));
//        } else {
//            for (Map.Entry<Water, Double> wc : this.composition.entrySet()) {
//                wc.getKey().aggregateComposition(currFraction * wc.getValue(), acc);
//            }
//        }
//        return acc;
//    }

//    private static String normalize(List<Water> ws) {
//        Double tot = tot(ws);
//        return groupBy(ws).stream()
//                .map(w -> String.format("%s%% %s", mf.format(100* w.liters() / tot), w.name()))
//                .reduce((a, b) -> a + ", " + b)
//                .get();
//    }
//
//    private static List<Water> groupBy(List<Water> ws) {
//        return ws.stream()
//                .filter(w -> w.liters() > 0.)
//                .collect(Collectors.groupingBy(w -> w.name()))
//                .entrySet().stream()
//                .map(entry -> entry.getValue())
//                .map(ls -> new Water(ls.stream().mapToDouble(i -> i.liters()).sum(), ls.get(0).profile()))
//                .sorted()
//                .collect(Collectors.toList());
//    }
//
//    private static Map<Water,Double> compose(List<Water> ws) {
//        return ws.stream().collect(Collectors.toMap(k -> k, v -> v.liters() / tot(ws)));
//    }
//
//
}
