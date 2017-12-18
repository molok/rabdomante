package alebolo.rabdomante;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChocoSolver implements IWSolver {
    WaterMixer mixer = new WaterMixer();

    @Override public Optional<Water> solve(Water xtarget, List<Water> waters, List<MineralAddition> salts) {
        Model model = new Model("waterModel");

        SolverTarget target = new SolverTarget(xtarget);

        List<SolverProfile> profiles = waters.stream()
                                    .map(w -> w.recipe().profilesRatio().get(0).profile())
                                    .map(p -> new SolverProfile(p))
                                    .collect(Collectors.toList());

        IntVar[] varWaters = watersToIntVars(waters, model, target.liters());
        model.sum(varWaters, "=", target.liters()).post();
        IntVar cost = cost(profiles, varWaters, model, target);

        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();
        Water res = null;
        while(solver.solve()) {
            System.out.println("cost = " + cost.getValue() + ", solution = " + Arrays.toString(varWaters));

            res = null;
            for (int i = 0; i < varWaters.length; i++) {
                IntVar varCurr = varWaters[i];
                if (varCurr.getValue() > 0) {
                    Water curr = new Water(varCurr.getValue(), waters.get(i).recipe());
                    res = res == null ? curr : mixer.merge(res, curr);
                }
            }

            if (res != null) {
                System.out.println("mydistance:" +DistanceCalculator.distanceCoefficient(target.water(), res) + ", w:" +res.description());
            }


        }

        return Optional.ofNullable(res);
    }

    private enum MineralContent {
        calcio, magnesio, sodio, bicarbonati, solfato, cloruro;
    }

    private IntVar cost(List<SolverProfile> profiles, IntVar[] varWaters, Model model, SolverTarget target) {
        Map<MineralContent, Function<IMgPerL, Integer>> map = new HashMap<>();
        map.put(MineralContent.calcio, x -> x.calcioMgPerL());
        map.put(MineralContent.magnesio, x -> x.magnesioMgPerL());
        map.put(MineralContent.sodio, x -> x.sodioMgPerL());
        map.put(MineralContent.bicarbonati, x -> x.bicarbonatiMgPerL());
        map.put(MineralContent.solfato, x -> x.solfatoMgPerL());
        map.put(MineralContent.cloruro, x -> x.cloruroMgPerL());

        Map<MineralContent, IntVar> mineralContentSum = new HashMap<>();

        for (int i = 0; i < profiles.size(); i++) {
            IntVar varWater = varWaters[i];
            SolverProfile t = profiles.get(i);

            for (Map.Entry<MineralContent, Function<IMgPerL, Integer>> mc_getter : map.entrySet()) {
                mineralContentSum.merge(
                           mc_getter.getKey(),
                           costAddendo(model, varWater, t, mc_getter.getKey(), mc_getter.getValue()),
                           (IntVar aggCost, IntVar addendo) -> aggCost.add(addendo).intVar());
            }
        }

        IntVar cost = map.entrySet().stream()
                .map(mc_f -> mineralContentSum.get(mc_f.getKey())
                                              .div(target.liters())
                                              .dist(mc_f.getValue().apply(target)).intVar())
                .reduce(model.intVar("WaterDistance", 0), (a, b) -> a.add(b).intVar());

        return cost;
    }

    private IntVar costAddendo(Model model, IntVar varWater, SolverProfile profile, MineralContent mineralContent, Function<IMgPerL, Integer> getter) {
        return model.intVar("cost_" + mineralContent.name(), getter.apply(profile)).mul(varWater).intVar();
    }

    private IntVar[] watersToIntVars(List<Water> waters, Model model, int liters) {
        /* FIXME diamo per assunto che non ci siano water con più profile */
        IntVar[] varWaters = new IntVar[waters.size()];
        for (int i = 0; i < waters.size(); i++) {
            varWaters[i] = model.intVar
                            ( waters.get(i)
                                    .recipe().profilesRatio().get(0)
                                    .profile().name()
                            , 0
                            , liters);
        }
        return varWaters;
    }
}
