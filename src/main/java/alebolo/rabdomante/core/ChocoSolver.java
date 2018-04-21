package alebolo.rabdomante.core;

import com.google.common.primitives.Ints;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.math3.util.IntegerSequence;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import java.util.function.Function;

public class ChocoSolver implements WaterSolver {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override public Optional<Recipe> solve(Water target, List<Salt> availableSalts, List<Water> availableWaters) {
        logger.info("target {}", target);
        logger.info("waters {}", availableWaters);
        logger.info("salts {}", availableSalts);

        Model model = new Model("waterModel");

        int targetLiters = target.liters;

        Map<WaterProfile, IntVar> waterVars = waterVars(model, availableWaters, targetLiters);
        Map<SaltProfile, IntVar> saltVars = saltVars(model, availableSalts, target);

        IntVar cost = cost(model, targetLiters, target, waterVars, saltVars);

        /* la somma delle acque deve corrispondere al totale dei litri necessari */
        model.sum(waterVars.values().toArray(new IntVar[waterVars.size()]), "=", targetLiters).post();

        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();
//        solver.limitTime(30 * 1000);

        List<IntVar> toWatch = new ArrayList<>(waterVars.values());
        toWatch.addAll(saltVars.values());

        Recipe recipe = null;
        while(solver.solve()) {
            recipe = new Recipe(
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

        logger.info("recipe", recipe);

        return Optional.ofNullable(recipe);
    }

    private IntVar cost(Model model, int targetLiters, WaterProfile target, Map<WaterProfile, IntVar> waterVars, Map<SaltProfile, IntVar> saltVars) {
        IntVar zero = model.intVar(0);
        IntVar sumCaWs = waterSum(waterVars, w1 -> w1.ca).add(sumSalt(saltVars, s -> s.ca).orElse(zero)).intVar();
        IntVar sumMgWs = waterSum(waterVars, w1 -> w1.mg).add(sumSalt(saltVars, s -> s.mg).orElse(zero)).intVar();
        IntVar sumNaWs = waterSum(waterVars, w1 -> w1.na).add(sumSalt(saltVars, s -> s.na).orElse(zero)).intVar();
        IntVar sumSo4Ws = waterSum(waterVars, w1 -> w1.so4).add(sumSalt(saltVars, s -> s.so4).orElse(zero)).intVar();
        IntVar sumClWs = waterSum(waterVars, w1 -> w1.cl).add(sumSalt(saltVars, s -> s.cl).orElse(zero)).intVar();
        IntVar sumHco3Ws = waterSum(waterVars, w1 -> w1.hco3).add(sumSalt(saltVars, s -> s.hco3).orElse(zero)).intVar();

        return error(sumCaWs.div(targetLiters).intVar(), target.ca)
                .add(error(sumMgWs.div(targetLiters).intVar(), target.mg))
                .add(error(sumNaWs.div(targetLiters).intVar(), target.na))
                .add(error(sumSo4Ws.div(targetLiters).intVar(), target.so4))
                .add(error(sumClWs.div(targetLiters).intVar(), target.cl))
                .add(error(sumHco3Ws.div(targetLiters).intVar(), target.hco3))
                .intVar();
    }

    private Map<SaltProfile, IntVar> saltVars(Model model, List<Salt> salts, Water target) {
        Map<SaltProfile, IntVar> saltVars = new HashMap<>();
        salts.stream()
                .map(s -> new Pair<>(s, model.intVar(s.nome + " (dg)", range(saltUpperBound(s, target), 1))))
                .forEach(s -> saltVars.put(s.getValue0(), s.getValue1()));
        return saltVars;
    }

    static int saltUpperBound(Salt s, Water target) {
        /* l'idea è calcolare l'apporto massimo che è sensato dare ad un'acqua
           quindi è inutile provare con 1kg di sale se già con 20g si è sforato
           il valore massimo di sodio */
        return Math.min
                ( s.dg, Arrays.asList(
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

    /** ritorna un range con massimo targetL e step ogni perc, ad esempio:
     range(100, 10)  -> [0, 10, 20, 30, 40, 50, 60, 70, 80 , 90, 100]
     range(100, 50)  -> [0, 50, 100]
     range(100, 100) -> [0, 100]
     range(10, 10)   -> [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
     */
    static public int[] range(int max, int perc) {
        int step = Math.max(1, (max * perc) / 100);
        return Ints.toArray(
                IteratorUtils.toList(
                        new IntegerSequence.Range(0, max, step ).iterator()));
    }

    private Optional<IntVar> sumSalt(Map<SaltProfile, IntVar> ss, Function<SaltProfile, Integer> f) {
        return ss.entrySet().stream().map(s -> s.getValue().mul(f.apply(s.getKey())).intVar())
                .reduce((a, b) -> a.add(b).intVar());
    }

    private IntVar waterSum(Map<WaterProfile, IntVar> wvs, Function<WaterProfile, Integer> getter) {
        return wvs.entrySet().stream()
                .map(wv -> wv.getValue().mul(getter.apply(wv.getKey())).intVar())
                .reduce((a, b) -> a.add(b).intVar()).get();
    }

}
