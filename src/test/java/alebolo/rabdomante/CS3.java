package alebolo.rabdomante;

import com.google.common.primitives.Ints;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.math3.util.IntegerSequence;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.function.Function;

@RunWith(JUnit4.class)
public class CS3 {
    class Water {
        private final int ca;
        private final int mg;
        private final int na;
        Water(int ca, int mg, int na) { this.ca = ca; this.mg = mg; this.na = na; }
        @Override public String toString() { return "Water{" + "ca=" + ca + ", mg=" + mg + ", na=" + na + '}'; }
    }

    class Salt {
        private final int ca;
        private final int mg;
        private final int na;
        Salt(int ca, int mg, int na) { this.ca = ca; this.mg = mg; this.na = na; }
        @Override public String toString() { return "Salt{" + "ca=" + ca + ", mg=" + mg + ", na=" + na + '}'; }
    }


    @Test public void x() {
        Water distillata = new Water(0, 0, 0);
        Water sa = new Water(50, 20, 20);
        Water milano = new Water(100, 100, 100);
        Water target = new Water(75, 75, 75);
        int targetL = 100; /* a 1000 si spacca... qualche overflow ? */

        Model model = new Model("waterModel");

        Salt gypsum = new Salt(23, 0, 0);
        Salt tableSalt = new Salt(0, 0, 39);
        IntVar vGypsum = model.intVar("gypsum (g)", 0, 100);
        IntVar vTableSalt = model.intVar("tablesalt (g)", 0, 100);

        IntVar vDistillata = model.intVar("distillata (L)", range(targetL, 10));
        IntVar vSantanna = model.intVar("santanna (L)", range(targetL, 10));
        IntVar vMilano = model.intVar("milano (L)", range(targetL, 10));

        Map<Salt, IntVar> ss = new HashMap<>();
        ss.put(gypsum, vGypsum);
        ss.put(tableSalt, vTableSalt);

        Map<Water, IntVar> wvs = new HashMap<>();
        wvs.put(distillata, vDistillata);
        wvs.put(sa, vSantanna);
        wvs.put(milano, vMilano);

        IntVar sumCaWs = waterSum(wvs, w1 -> w1.ca).add(sumSalt(ss, s -> s.ca)).intVar();
        IntVar sumMgWs = waterSum(wvs, w1 -> w1.mg).add(sumSalt(ss, s -> s.mg)).intVar();
        IntVar sumNaWs = waterSum(wvs, w1 -> w1.na).add(sumSalt(ss, s -> s.na)).intVar();

        IntVar cost = square(sumCaWs.sub(target.ca * targetL).intVar())
                 .add(square(sumMgWs.sub(target.mg * targetL).intVar()))
                 .add(square(sumNaWs.sub(target.na * targetL).intVar()))
                 .intVar();

        model.sum(wvs.values().toArray(new IntVar[0]), "=", targetL).post();

        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();

        List<IntVar> toWatch = new ArrayList<>(wvs.values());
        toWatch.addAll(ss.values());

        while(solver.solve()) {
//            solver.printStatistics();
            System.out.println("\n\ncost:"+cost.toString());
            toWatch.stream().forEach(w -> System.out.println(":"+w.toString()));
            System.out.println("target:" + target.toString());
            System.out.printf(
                    "Ca (mg/L): " + weightedSum(wvs, w -> w.ca, ss, s -> s.ca, targetL) +
                            ", Mg (mg/L): " + weightedSum(wvs, w -> w.mg, ss, s -> s.mg, targetL) +
                            ", Na (mg/L): " + weightedSum(wvs, w -> w.na, ss, s -> s.na, targetL) + "\n");
        }

    }

    /* ritorna un range con massimo targetL e step ogni perc, ad esempio:
        range(100, 10)  -> [0, 10, 20, 30, 40, 50, 60, 70, 80 , 90, 100]
        range(100, 50)  -> [0, 50, 100]
        range(100, 100) -> [0, 100]
        range(10, 10)   -> [1, 2, 3, 4 5, 6, 7, 8, 9, 10]
     */
    private int[] range(int targetL, int perc) {
        if ((targetL / perc ) <= 0 ) {
            throw new RuntimeException("targetL / perc <= 0");
        } else {
            return Ints.toArray(
                    IteratorUtils.toList(
                            new IntegerSequence.Range(0, targetL, targetL / perc).iterator()));
        }
    }

    private IntVar sumSalt(Map<Salt, IntVar> ss, Function<Salt, Integer> f) {
        return ss.entrySet().stream().map(s -> s.getValue().mul(f.apply(s.getKey())).intVar())
                             .reduce((a, b) -> a.add(b).intVar()).get();
    }

    private int weightedSum(Map<Water, IntVar> wvs, Function<Water, Integer> getter, Map<Salt, IntVar> ss, Function<Salt, Integer> sgetter, int liters) {
        int wSum = wvs.entrySet().stream()
                .mapToInt(wv -> wv.getValue().getValue() * getter.apply(wv.getKey()))
                .sum();
        int sSum = ss.entrySet().stream()
                .mapToInt(sv -> sv.getValue().getValue() * sgetter.apply(sv.getKey()))
                .sum();
        return (wSum + sSum)/ liters;
    }

    private IntVar waterSum(Map<Water, IntVar> wvs, Function<Water, Integer> getter) {
        return wvs.entrySet().stream()
                .map(wv -> wv.getValue().mul(getter.apply(wv.getKey())).intVar())
                .reduce((a, b) -> a.add(b).intVar()).get();
    }

    private IntVar square(IntVar x) {
        return x.mul(x).intVar();
    }
}
