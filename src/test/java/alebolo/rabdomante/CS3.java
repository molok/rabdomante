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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class CS3 {
    WaterProfile santanna = new WaterProfile(10, 0, 1, 8, 0, 26, "santanna");
    WaterProfile milano = new WaterProfile(70, 15, 12, 42, 27, 228, "milano");
    WaterProfile boario = new WaterProfile(131, 40, 5, 240, 4, 303, "boardio");
    WaterProfile levissima = new WaterProfile(21, 2, 2, 17, 0, 57, "levissima");
    WaterProfile eva = new WaterProfile(10, 4, 0, 2, 0, 48, "eva");
    WaterProfile norda = new WaterProfile(11, 3, 2, 6, 1, 52, "norda");
    WaterProfile vera = new WaterProfile(35, 12, 2, 19, 3, 148, "vera");
    WaterProfile vitasnella = new WaterProfile(86, 26, 3, 83, 2, 301, "vitasnella");
    WaterProfile dolomiti = new WaterProfile(8, 9, 1, 22, 1, 95, "dolomiti");
    WaterProfile sanbern = new WaterProfile(9, 1, 1, 2, 1, 30, "sanberardo");
    WaterProfile distilled = new WaterProfile(0, 0, 0, 0, 0, 0, "distillata");

    WaterProfile yellowDry = new WaterProfile(50, 10, 5, 105, 45, 0, "yellow dry");

    SaltProfile gypsum = new SaltProfile(23, 0, 0, 56, 0, 0, "gypsum");
    SaltProfile tableSalt = new SaltProfile(0, 0, 39, 0, 62, 0, "tableSalt");
    SaltProfile calciumChloride = new SaltProfile(36, 0, 0, 0, 64, 0, "calcium chloride");
    SaltProfile magnesiumChloride = new SaltProfile(0, 12, 0, 0, 35, 0, "magnesium chloride");
    SaltProfile espomSalt = new SaltProfile(0, 1, 0, 0, 35, 0, "epsom salt");
    SaltProfile bakingSoda = new SaltProfile(0, 0, 27, 0, 0, 72, "baking soda");
    SaltProfile piclingLime = new SaltProfile(45, 0, 0, 0, 0, 164, "pickling lime");

    @Test public void testUpperBound() {
        assertThat(
                saltUpperBound(new Salt(calciumChloride, 10000),
                        new Water(yellowDry, 1000))
        ).isEqualTo(4);
    }

    @Test public void all_vars_big() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(gypsum, 10000),
                new Salt(tableSalt, 10000),
                new Salt(calciumChloride, 10000),
                new Salt(magnesiumChloride, 10000),
                new Salt(espomSalt, 10000),
                new Salt(bakingSoda, 10000),
                new Salt(piclingLime, 10000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( santanna , Integer.MAX_VALUE),
                new Water( milano , Integer.MAX_VALUE),
                new Water( boario , Integer.MAX_VALUE),
                new Water( levissima , Integer.MAX_VALUE),
                new Water( eva , Integer.MAX_VALUE),
                new Water( norda , Integer.MAX_VALUE),
                new Water( vera , Integer.MAX_VALUE),
                new Water( vitasnella , Integer.MAX_VALUE),
                new Water( dolomiti , Integer.MAX_VALUE),
                new Water( sanbern , Integer.MAX_VALUE)
//                new Water( distilled , Integer.MAX_VALUE)
        );

        Water target = new Water(yellowDry, 1000);
        Optional<Recipex> solution = solve(target, mySalts, myWater);
        assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }

    @Test public void all_vars() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(gypsum, 1000),
                new Salt(tableSalt, 1000),
                new Salt(calciumChloride, 1000),
                new Salt(magnesiumChloride, 1000),
                new Salt(espomSalt, 1000),
                new Salt(bakingSoda, 1000),
                new Salt(piclingLime, 1000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( santanna , Integer.MAX_VALUE),
                new Water( milano , Integer.MAX_VALUE),
                new Water( boario , Integer.MAX_VALUE),
                new Water( levissima , Integer.MAX_VALUE),
                new Water( eva , Integer.MAX_VALUE),
                new Water( norda , Integer.MAX_VALUE),
                new Water( vera , Integer.MAX_VALUE),
                new Water( vitasnella , Integer.MAX_VALUE),
                new Water( dolomiti , Integer.MAX_VALUE),
                new Water( sanbern , Integer.MAX_VALUE),
                new Water( distilled , Integer.MAX_VALUE)
        );

        Water target = new Water(yellowDry, 100);
        Optional<Recipex> solution = solve(target, mySalts, myWater);
        assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }


    @Test public void happy() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(distilled, Integer.MAX_VALUE));
        Water target = new Water(distilled, 10);

        Optional<Recipex> solution = solve(target, mySalts, myWater);
        assertThat(solution).isPresent();
        assertThat(solution.get().waters).containsExactly(new Water(distilled, 10));
    }

    @Test public void not_enough_water() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(distilled, 9));
        Water target = new Water(distilled, 10);

        Optional<Recipex> solution = solve(target, mySalts, myWater);
        assertThat(solution).isNotPresent();
    }

    @Test public void easy_50_50() {
        List<Salt> mySalts = Arrays.asList();

        WaterProfile pa = new WaterProfile(10, 10, 10, 10, 10, 10, "a");
        Water a = new Water(pa, 100);

        WaterProfile pb = new WaterProfile(0, 0, 0, 0, 0, 0, "b");
        Water b = new Water(pb, 100);

        WaterProfile pc = new WaterProfile(100, 100, 100, 100, 100, 100, "c");
        Water c = new Water(pb, 100);

        List<Water> myWater = Arrays.asList( a, b, c );

        Water target = new Water(new WaterProfile(5, 5, 5, 5, 5, 5, "target"), 30);

        Optional<Recipex> solution = solve(target, mySalts, myWater);
        assertThat(solution).isPresent();
        assertThat(solution.get().waters).containsOnly(new Water(pa, 15), new Water(pb, 15));
        assertThat(solution.get().salts).isEmpty();
    }

    @Test public void salt() {
        SaltProfile magicSalt = new SaltProfile(5, 5, 5, 5, 5, 5, "magicSalt");
        SaltProfile badSalt = new SaltProfile(301, 301, 301, 301, 301, 301, "magicSalt");
        List<Salt> mySalts = Arrays.asList(new Salt(magicSalt, 1000 * 10), new Salt(badSalt, 1000 * 10));
        List<Water> myWater = Arrays.asList(new Water(distilled, Integer.MAX_VALUE));

        Water target = new Water(new WaterProfile(15, 15, 15, 15, 15, 15, "target"), 100);

        Optional<Recipex> solution = solve(target, mySalts, myWater);
        assertThat(solution).isPresent();
        assertThat(solution.get().waters).containsOnly(new Water(distilled, 100));
        assertThat(solution.get().salts).containsOnly(new Salt(magicSalt, 300));
    }

    @Test public void antani() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(gypsum, 100),
                new Salt(tableSalt, 1000),
                new Salt(bakingSoda, 1000));

        List<Water> myWater = Arrays.asList(
                new Water(milano, Integer.MAX_VALUE),
                new Water(santanna, 1),
                new Water(sanbern, 1));

        Water target = new Water(yellowDry, 20);

        Optional<Recipex> recipe = solve(target, mySalts, myWater);
        assertThat(recipe.isPresent()).isTrue();
        System.out.println(recipe.toString());
        System.out.println(target.toString());
    }

    private Optional<Recipex> solve(Water target, List<Salt> mySalts, List<Water> myWater) {
        Model model = new Model("waterModel");

        int targetLiters = target.liters;

        Map<WaterProfile, IntVar> waterVars = waterVars(model, myWater, targetLiters);
        Map<SaltProfile, IntVar> saltVars = saltVars(model, mySalts, target);

        IntVar cost = cost(model, targetLiters, target, waterVars, saltVars);

        /* la somma delle acque deve corrispondere al totale dei litri necessari */
        model.sum(waterVars.values().toArray(new IntVar[0]), "=", targetLiters).post();

        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();

        List<IntVar> toWatch = new ArrayList<>(waterVars.values());
        toWatch.addAll(saltVars.values());

        Recipex recipe = null;
        while(solver.solve()) {
            recipe = new Recipex(
                waterVars.entrySet().stream()
                        .filter(wv -> wv.getValue().getValue() != 0)
                        .map(wv -> new Water(wv.getKey(), wv.getValue().getValue()))
                        .collect(Collectors.toList()),
                saltVars.entrySet().stream()
                        .filter(sv -> sv.getValue().getValue() != 0)
                        .map(sv -> new Salt(sv.getKey(), sv.getValue().getValue()))
                        .collect(Collectors.toList()),
                cost.getValue()
            );
        }

        return Optional.ofNullable(recipe);
    }

    @Test public void x() {
//        Model model = new Model("waterModel");
//        int targetLiters = 10;
//        Water target = yellowDry;
//
//        Map<Water, IntVar> waterVars = waterVars(model, waters, targetLiters);
//        Map<SaltProfile, IntVar> saltVars = saltVars(model, salts);
//
//        IntVar cost = cost(model, targetLiters, target, waterVars, saltVars);
//
//        /* la somma delle acque deve corrispondere al totale dei litri necessari */
//        model.sum(waterVars.values().toArray(new IntVar[0]), "=", targetLiters).post();
//
//        model.setObjective(Model.MINIMIZE, cost);
//        Solver solver = model.getSolver();
//
//        List<IntVar> toWatch = new ArrayList<>(waterVars.values());
//        toWatch.addAll(saltVars.values());
//
//        while(solver.solve()) {
////            solver.printStatistics();
//            System.out.println("\n\ncost:"+cost.toString());
//            toWatch.stream().filter(w -> w.getValue() != 0).forEach(w -> System.out.println(":"+w.toString()));
//            System.out.println("target:" + target.toString());
//            System.out.printf(
//                   "Ca (mg/L): "   + weightedSum(waterVars, w -> w.ca, saltVars, s -> s.ca, targetLiters) +
//                ",\nMg (mg/L): "   + weightedSum(waterVars, w -> w.mg, saltVars, s -> s.mg, targetLiters) +
//                ",\nNa (mg/L): "   + weightedSum(waterVars, w -> w.na, saltVars, s -> s.na, targetLiters) +
//                ",\nSO4 (mg/L): "  + weightedSum(waterVars, w -> w.so4, saltVars, s -> s.so4, targetLiters) +
//                ",\nCl (mg/L): "   + weightedSum(waterVars, w -> w.cl, saltVars, s -> s.cl, targetLiters) +
//                ",\nHCO3 (mg/L): " + weightedSum(waterVars, w -> w.hco3, saltVars, s -> s.hco3, targetLiters) +
//                "\n");
//        }
    }

    private IntVar cost(Model model, int targetLiters, WaterProfile target, Map<WaterProfile, IntVar> waterVars, Map<SaltProfile, IntVar> saltVars) {
        IntVar zero = model.intVar(0);
        IntVar sumCaWs = waterSum(waterVars, w1 -> w1.ca).add(sumSalt(saltVars, s -> s.ca).orElse(zero)).intVar();
        IntVar sumMgWs = waterSum(waterVars, w1 -> w1.mg).add(sumSalt(saltVars, s -> s.mg).orElse(zero)).intVar();
        IntVar sumNaWs = waterSum(waterVars, w1 -> w1.na).add(sumSalt(saltVars, s -> s.na).orElse(zero)).intVar();
        IntVar sumSo4Ws = waterSum(waterVars, w1 -> w1.so4).add(sumSalt(saltVars, s -> s.so4).orElse(zero)).intVar();
        IntVar sumClWs = waterSum(waterVars, w1 -> w1.cl).add(sumSalt(saltVars, s -> s.cl).orElse(zero)).intVar();
        IntVar sumHco3Ws = waterSum(waterVars, w1 -> w1.hco3).add(sumSalt(saltVars, s -> s.hco3).orElse(zero)).intVar();

        return error(sumCaWs, target.ca * targetLiters)
                 .add(error(sumMgWs, target.mg * targetLiters))
                 .add(error(sumNaWs, target.na * targetLiters))
                 .add(error(sumSo4Ws, target.so4 * targetLiters))
                 .add(error(sumClWs, target.cl * targetLiters))
                 .add(error(sumHco3Ws, target.hco3 * targetLiters))
                 .intVar();
    }

    private Map<SaltProfile, IntVar> saltVars(Model model, List<Salt> salts, Water target) {
        Map<SaltProfile, IntVar> saltVars = new HashMap<>();
         /* TODO far impostare la disponibilità di sali (ub)*/
        salts.stream()
             .map(s -> new Pair<>(s, model.intVar(s.nome + " (dg)", 0, saltUpperBound(s, target))))
             .forEach(s -> saltVars.put(s.getValue0(), s.getValue1()));
        return saltVars;
    }

    static int saltUpperBound(Salt s, Water target) {
        return Math.min
                ( s.dg,
                  Arrays.asList(
                        ( s.ca == 0 ? 0 : ((target.ca * target.liters) / s.ca) * 2),
                        ( s.mg == 0 ? 0 : ((target.mg * target.liters) / s.mg) * 2),
                        ( s.na == 0 ? 0 : ((target.na * target.liters) / s.na) * 2),
                        ( s.so4 == 0 ? 0 : ((target.so4 * target.liters) / s.so4) * 2),
                        ( s.cl == 0 ? 0 : ((target.cl * target.liters) / s.cl) * 2),
                        ( s.hco3 == 0 ? 0 : ((target.hco3 * target.liters) / s.hco3) * 2))
                        .stream().mapToInt(i -> i).max().getAsInt());
    }

    private Map<WaterProfile, IntVar> waterVars(Model model, List<Water> waters, int targetLiters) {
        Map<WaterProfile, IntVar> waterVars = new HashMap<>();
        waters.stream()
              .map(w -> new HashMap.SimpleImmutableEntry<>
                            (w, model.intVar(w.nome + " (L)", range(Math.min(targetLiters, w.liters), 10))))
              .forEach(e -> waterVars.put(e.getKey(), e.getValue()));
        return waterVars;
    }

    private IntVar error(IntVar actual, int expected) {
        return actual.sub(expected).abs().intVar();
    }

    @Test public void testRange() {
        assertThat(range(100, 10)).containsExactly(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        assertThat(range(100, 1)).hasSize(101);
        assertThat(range(200, 1)).hasSize(101);
        assertThat(range(10, 10)).hasSize(11);
    }

    /** ritorna un range con massimo targetL e step ogni perc, ad esempio:
        range(100, 10)  -> [0, 10, 20, 30, 40, 50, 60, 70, 80 , 90, 100]
        range(100, 50)  -> [0, 50, 100]
        range(100, 100) -> [0, 100]
        range(10, 10)   -> [0, 1, 2, 3, 4 5, 6, 7, 8, 9, 10]
     */
    static private int[] range(int targetL, int perc) {
        int step = Math.max(1, (targetL * perc) / 100);
        return Ints.toArray(
                IteratorUtils.toList(
                        new IntegerSequence.Range(0, targetL, step ).iterator()));
    }

    private Optional<IntVar> sumSalt(Map<SaltProfile, IntVar> ss, Function<SaltProfile, Integer> f) {
        return ss.entrySet().stream().map(s -> s.getValue().mul(f.apply(s.getKey())).intVar())
                             .reduce((a, b) -> a.add(b).intVar());
    }

    private int weightedSum(Map<WaterProfile, IntVar> wvs, Function<WaterProfile, Integer> getter, Map<SaltProfile, IntVar> ss, Function<SaltProfile, Integer> sgetter, int liters) {
        int wSum = wvs.entrySet().stream()
                .mapToInt(wv -> wv.getValue().getValue() * getter.apply(wv.getKey()))
                .sum();
        int sSum = ss.entrySet().stream()
                .mapToInt(sv -> sv.getValue().getValue() * sgetter.apply(sv.getKey()))
                .sum();
        return (wSum + sSum)/ liters;
    }

    private IntVar waterSum(Map<WaterProfile, IntVar> wvs, Function<WaterProfile, Integer> getter) {
        return wvs.entrySet().stream()
                .map(wv -> wv.getValue().mul(getter.apply(wv.getKey())).intVar())
                .reduce((a, b) -> a.add(b).intVar()).get();
    }

    class WaterProfile {
        final int ca;
        final int mg;
        final int na;
        final int so4;
        final int cl;
        final int hco3;
        final String nome;

        WaterProfile(int ca, int mg, int na, int so4, int cl, int hco3, String nome) {
            this.ca = ca;
            this.mg = mg;
            this.na = na;
            this.so4 = so4;
            this.cl = cl;
            this.hco3 = hco3;
            this.nome = nome;
        }

        @Override public String toString() {
            return "Water{" +
                    "ca=" + ca +
                    ", mg=" + mg +
                    ", na=" + na +
                    ", so4=" + so4 +
                    ", cl=" + cl +
                    ", hco3=" + hco3 +
                    ", nome='" + nome + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WaterProfile that = (WaterProfile) o;
            return ca == that.ca &&
                    mg == that.mg &&
                    na == that.na &&
                    so4 == that.so4 &&
                    cl == that.cl &&
                    hco3 == that.hco3 &&
                    Objects.equals(nome, that.nome);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ca, mg, na, so4, cl, hco3, nome);
        }
    }

    class SaltProfile {
        final int ca;
        final int mg;
        final int na;
        final int so4;
        final int cl;
        final int hco3;
        final String nome;

        SaltProfile(int ca, int mg, int na, int so4, int cl, int hco3, String nome) {
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
            return "SaltProfile{" +
                    "ca=" + ca +
                    ", mg=" + mg +
                    ", na=" + na +
                    ", so4=" + so4 +
                    ", cl=" + cl +
                    ", hco3=" + hco3 +
                    ", nome='" + nome + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SaltProfile that = (SaltProfile) o;
            return ca == that.ca &&
                    mg == that.mg &&
                    na == that.na &&
                    so4 == that.so4 &&
                    cl == that.cl &&
                    hco3 == that.hco3 &&
                    Objects.equals(nome, that.nome);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ca, mg, na, so4, cl, hco3, nome);
        }
    }

    class Water extends WaterProfile {
        private final int liters;
        Water(WaterProfile salt, int liters) {
            this(salt.ca, salt.mg, salt.na, salt.so4, salt.cl, salt.hco3, salt.nome, liters);
        }
        Water(int ca, int mg, int na, int so4, int cl, int hco3, String nome, int liters) {
            super(ca, mg, na, so4, cl, hco3, nome);
            this.liters = liters;
        }

        @Override
        public String toString() {
            return "AvailableWater{" +
                    "ca=" + ca +
                    ", mg=" + mg +
                    ", na=" + na +
                    ", so4=" + so4 +
                    ", cl=" + cl +
                    ", hco3=" + hco3 +
                    ", nome='" + nome + '\'' +
                    ", liters=" + liters +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Water water = (Water) o;
            return liters == water.liters;
        }

        @Override
        public int hashCode() {

            return Objects.hash(super.hashCode(), liters);
        }
    }

    class Salt extends SaltProfile {
        private final int dg;
        Salt(SaltProfile salt, int dg) {
            this(salt.ca, salt.mg, salt.na, salt.so4, salt.cl, salt.hco3, salt.nome, dg);
        }
        Salt(int ca, int mg, int na, int so4, int cl, int hco3, String nome, int dg) {
            super(ca, mg, na, so4, cl, hco3, nome);
            this.dg = dg;
        }

        @Override
        public String toString() {
            return "Salt{" +
                    "ca=" + ca +
                    ", mg=" + mg +
                    ", na=" + na +
                    ", so4=" + so4 +
                    ", cl=" + cl +
                    ", hco3=" + hco3 +
                    ", nome='" + nome + '\'' +
                    ", dg=" + dg +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Salt salt = (Salt) o;
            return dg == salt.dg;
        }

        @Override
        public int hashCode() {

            return Objects.hash(super.hashCode(), dg);
        }
    }

    @Test public void testRecipe() {
        Recipex recipe = new Recipex(Arrays.asList(new Water(santanna, 10)), Arrays.asList(), 0);
        System.out.println(recipe);
    }

    private class Recipex {
        private final List<Water> waters;
        private final List<Salt> salts;
        private int distanceFromTarget;

        public Recipex(List<Water> waters, List<Salt> salts, int distanceFromTarget) {
            this.waters = waters;
            this.salts = salts;
            this.distanceFromTarget = distanceFromTarget;
        }

        @Override
        public String toString() {
            return "Recipex{" +
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
}
