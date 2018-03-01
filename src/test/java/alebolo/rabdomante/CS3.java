package alebolo.rabdomante;

import com.google.common.primitives.Ints;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.math3.util.IntegerSequence;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class CS3 {
    Water santanna = new Water(10, 0, 1, 8, 0, 26, "santanna");
    Water milano = new Water(70, 15, 12, 42, 27, 228, "milano");
    Water boario = new Water(131, 40, 5, 240, 4, 303, "boardio");
    Water levissima = new Water(21, 2, 2, 17, 0, 57, "levissima");
    Water eva = new Water(10, 4, 0, 2, 0, 48, "eva");
    Water norda = new Water(11, 3, 2, 6, 1, 52, "norda");
    Water vera = new Water(35, 12, 2, 19, 3, 148, "vera");
    Water vitasnella = new Water(86, 26, 3, 83, 2, 301, "vitasnella");
    Water dolomiti = new Water(8, 9, 1, 22, 1, 95, "dolomiti");
    Water sanbern = new Water(9, 1, 1, 2, 1, 30, "sanberardo");
    Water distilled = new Water(0, 0, 0, 0, 0, 0, "distillata");
    Salt gypsum = new Salt(23, 0, 0, 56, 0, 0, "gypsum");
    Salt tableSalt = new Salt(0, 0, 39, 0, 62, 0, "tableSalt");

    /*
    public final static MineralProfile CALCIUM_CHLORIDE = new MineralProfile.Builder().name("calcium chloride").calcioRatio(0.3611).cloruroRatio(0.6389).build();
    public final static MineralProfile ESPOM_SALT = new Builder().name("epsom salt").magnesiumRatio(0.0986).solfatoRatio(0.3487).build();
    public final static MineralProfile MAGNESIUM_CHLORIDE = new MineralProfile.Builder().name("magnesium chloride").magnesiumRatio(0.1195).cloruroRatio(0.3487).build();
    public final static MineralProfile BAKING_SODA = new Builder().name("baking soda").sodioRatio(0.2737).bicarbonateRatio(0.7263).build();
    public final static MineralProfile CHALK = new Builder().name("chalk").calcioRatio(0.4005).bicarbonateRatio(1.2198).build();
    public final static MineralProfile PICKLING_LIME = new Builder().name("pickling lime").calcioRatio(0.541).bicarbonateRatio(1.6455).build();
    */

    List<Water> waters = Arrays.asList(santanna, milano, boario, levissima, eva, norda, vera, vitasnella, dolomiti, sanbern, distilled);
    List<Salt> salts = Arrays.asList(gypsum, tableSalt);

//    List<Water> waters = Arrays.asList(santanna);
//    List<Salt> salts = Arrays.asList();

    @Test public void x() {
        Model model = new Model("waterModel");
        int targetL = 20;

//        IntVar vDistillata = model.intVar("distillata (L)", range(targetL, 10));
//        IntVar vSantanna = model.intVar("santanna (L)", range(targetL, 10));
//        IntVar vMilano = model.intVar("milano (L)", range(targetL, 10));

        Map<Water, IntVar> wvs = new HashMap<>();
        waters.stream()
              .map(w -> new HashMap.SimpleImmutableEntry<>
                            (w, model.intVar(w.nome + " (L)", range(targetL, 10))))
              .forEach(e -> wvs.put(e.getKey(), e.getValue()));

        Water target = new Water(11, 0, 1, 8, 0, 26, "santannax");
        //Water santanna = new Water(10, 0, 1, 8, 0, 26, "santanna");

        Map<Salt, IntVar> ss = new HashMap<>();
        salts.stream()
             .map(s -> new Pair<>(s, model.intVar(s.nome + " (g)", 0, 100)))
             .forEach(s -> ss.put(s.getValue0(), s.getValue1()));

        IntVar zero = model.intVar(0);
        IntVar sumCaWs = waterSum(wvs, w1 -> w1.ca).add(sumSalt(ss, s -> s.ca).orElse(zero)).intVar();
        IntVar sumMgWs = waterSum(wvs, w1 -> w1.mg).add(sumSalt(ss, s -> s.mg).orElse(zero)).intVar();
        IntVar sumNaWs = waterSum(wvs, w1 -> w1.na).add(sumSalt(ss, s -> s.na).orElse(zero)).intVar();
        IntVar sumSo4Ws = waterSum(wvs, w1 -> w1.so4).add(sumSalt(ss, s -> s.so4).orElse(zero)).intVar();
        IntVar sumClWs = waterSum(wvs, w1 -> w1.cl).add(sumSalt(ss, s -> s.cl).orElse(zero)).intVar();
        IntVar sumHco3Ws = waterSum(wvs, w1 -> w1.hco3).add(sumSalt(ss, s -> s.hco3).orElse(zero)).intVar();

        IntVar cost = error(sumCaWs, target.ca * targetL)
                 .add(error(sumMgWs, target.mg * targetL))
                 .add(error(sumNaWs, target.na * targetL))
                 .add(error(sumSo4Ws, target.so4 * targetL))
                 .add(error(sumClWs, target.cl * targetL))
                 .add(error(sumHco3Ws, target.hco3 * targetL))
                 .intVar();

        model.sum(wvs.values().toArray(new IntVar[0]), "=", targetL).post();

        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();

        List<IntVar> toWatch = new ArrayList<>(wvs.values());
        toWatch.addAll(ss.values());

        while(solver.solve()) {
//            solver.printStatistics();
            System.out.println("\n\ncost:"+cost.toString());
            toWatch.stream().filter(w -> w.getValue() != 0).forEach(w -> System.out.println(":"+w.toString()));
            System.out.println("target:" + target.toString());
            System.out.printf(
                    "Ca (mg/L): " + weightedSum(wvs, w -> w.ca, ss, s -> s.ca, targetL) +
                            ", Mg (mg/L): " + weightedSum(wvs, w -> w.mg, ss, s -> s.mg, targetL) +
                            ", Na (mg/L): " + weightedSum(wvs, w -> w.na, ss, s -> s.na, targetL) +
                            ", SO4 (mg/L): " + weightedSum(wvs, w -> w.so4, ss, s -> s.so4, targetL) +
                            ", Cl (mg/L): " + weightedSum(wvs, w -> w.cl, ss, s -> s.cl, targetL) +
                            ", HCO3 (mg/L): " + weightedSum(wvs, w -> w.hco3, ss, s -> s.hco3, targetL) +
                            "\n");
        }

    }

    private IntVar error(IntVar actual, int expected) {
        return actual.sub(expected).abs().intVar();
    }

    @Test public void testRange() {
        assertThat(range(100, 10)).containsExactly(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        assertThat(range(100, 1)).hasSize(101);
        assertThat(range(200, 1)).hasSize(101);
    }

    /** ritorna un range con massimo targetL e step ogni perc, ad esempio:
        range(100, 10)  -> [0, 10, 20, 30, 40, 50, 60, 70, 80 , 90, 100]
        range(100, 50)  -> [0, 50, 100]
        range(100, 100) -> [0, 100]
        range(10, 10)   -> [1, 2, 3, 4 5, 6, 7, 8, 9, 10]
     */
    static private int[] range(int targetL, int perc) {
        int step = Math.max(1, (targetL * perc) / 100);
        return Ints.toArray(
                IteratorUtils.toList(
                        new IntegerSequence.Range(0, targetL, step ).iterator()));
    }

    private Optional<IntVar> sumSalt(Map<Salt, IntVar> ss, Function<Salt, Integer> f) {
        return ss.entrySet().stream().map(s -> s.getValue().mul(f.apply(s.getKey())).intVar())
                             .reduce((a, b) -> a.add(b).intVar());
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

    class Water {
        private final int ca;
        private final int mg;
        private final int na;
        private final int so4;
        private final int cl;
        private final int hco3;
        private final String nome;

        Water(int ca, int mg, int na, int so4, int cl, int hco3, String nome) {
            this.ca = ca;
            this.mg = mg;
            this.na = na;
            this.so4 = so4;
            this.cl = cl;
            this.hco3 = hco3;
            this.nome = nome;
        }
        @Override
        public String toString() {
            return "Water{" + "ca=" + ca + ", mg=" + mg + ", na=" + na + ", so4=" + so4 + ", cl=" + cl + ", hco3=" + hco3 + '}';
        }
    }

    class Salt {
        private final int ca;
        private final int mg;
        private final int na;
        private final int so4;
        private final int cl;
        private final int hco3;
        private final String nome;

        Salt(int ca, int mg, int na, int so4, int cl, int hco3, String nome) {
            this.ca = ca;
            this.mg = mg;
            this.na = na;
            this.so4 = so4;
            this.cl = cl;
            this.hco3 = hco3;
            this.nome = nome;
        }

        @Override
        public String toString() {
            return "Salt{" + "ca=" + ca + ", mg=" + mg + ", na=" + na + ", so4=" + so4 + ", cl=" + cl + ", hco3=" + hco3 + '}';
        }
    }


}
