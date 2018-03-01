package alebolo.rabdomante;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;
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
        Water sindaco = new Water(100, 100, 100);
        Water target = new Water(75, 75, 75);
//        Water boh = new Water(15, 15, 15);
        int targetL = 100;

        Model model = new Model("waterModel");

        double litersPrecision = 0.1;

//        public final static MineralProfile GYPSUM = new MineralProfile.Builder().name("gypsum").calcioRatio(0.2328).solfatoRatio(0.558).build();
//        public final static MineralProfile TABLE_SALT = new MineralProfile.Builder().name("tableSalt").sodioRatio(0.3934).cloruroRatio(0.6066).build();

        Salt gypsum = new Salt(23, 0, 0);
        Salt tableSalt = new Salt(0, 0, 39);
        IntVar vGypsum = model.intVar("gypsum", 0, 100);
        IntVar vTableSalt = model.intVar("tablesalt", 0, 100);

        IntVar vDistillata = model.intVar("distillata", 0, targetL);
        IntVar vSantanna = model.intVar("santanna", 0, targetL);
        IntVar vSindaco = model.intVar("sindaco", 0, targetL);

        Map<Salt, IntVar> ss = new HashMap<>();
        ss.put(gypsum, vGypsum);
        ss.put(tableSalt, vTableSalt);

        Map<Water, IntVar> wvs = new HashMap<>();
        wvs.put(distillata, vDistillata);
        wvs.put(sa, vSantanna);
        wvs.put(sindaco, vSindaco);

        IntVar sumCaSalt = sumSalt(ss, s -> s.ca);
        IntVar sumMgSalt = sumSalt(ss, s -> s.mg);
        IntVar sumNaSalt = sumSalt(ss, s -> s.na);

        IntVar sumCa = sum(wvs, w1 -> w1.ca);
        IntVar sumMg = sum(wvs, w1 -> w1.mg);
        IntVar sumNa = sum(wvs, w1 -> w1.na);

        IntVar sumCaWs = sumCa.add(sumCaSalt).intVar();
        IntVar sumMgWs = sumMg.add(sumMgSalt).intVar();
        IntVar sumNaWs = sumNa.add(sumNaSalt).intVar();

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
                    "ca: " + weightedSum(wvs, w -> w.ca, targetL) +
                            ", mg: " + weightedSum(wvs, w -> w.mg, targetL) +
                            ", na: " + weightedSum(wvs, w -> w.na, targetL) + "\n");
        }

    }

    private IntVar sumSalt(Map<Salt, IntVar> ss, Function<Salt, Integer> f) {
        return ss.entrySet().stream().map(s -> s.getValue().mul(f.apply(s.getKey())).intVar())
                             .reduce((a, b) -> a.add(b).intVar()).get();
    }

    private IntVar costVar(Water target, int targetL, Map<Water, IntVar> wvs) {
        return square(sum(wvs, w1 -> w1.ca).sub(target.ca * targetL).intVar())
                 .add(square(sum(wvs, w1 -> w1.mg).sub(target.mg * targetL).intVar()))
                 .add(square(sum(wvs, w1 -> w1.na).sub(target.na * targetL).intVar()))
                 .intVar();
    }

    private int weightedSum(Map<Water, IntVar> wvs, Function<Water, Integer> getter, int liters) {
        return wvs.entrySet().stream()
                .map(wv -> wv.getValue().getValue() * getter.apply(wv.getKey()))
                .mapToInt(i -> i)
                .sum() / liters;
    }

    private IntVar sum(Map<Water, IntVar> wvs, Function<Water, Integer> getter) {
        return wvs.entrySet().stream()
                .map(wv -> wv.getValue().mul(getter.apply(wv.getKey())).intVar())
                .reduce((a, b) -> a.add(b).intVar()).get();
    }

    private IntVar square(IntVar x) {
        return x.mul(x).intVar();
    }
}
