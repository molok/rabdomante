package alebolo.rabdomante;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChocoSolver implements IWSolver {
    WaterMixer mixer = new WaterMixer();

    @Override public Optional<Water> solve(Water xtarget, List<Water> waters, List<MineralAddition> salts) {
        Model model = new Model("waterModel");

        SolverTarget target = new SolverTarget(xtarget);

        IntVar[] varWaters = watersToIntVars(waters, model, target.liters());
        model.sum(varWaters, "=", target.liters()).post();
        IntVar[] varMineralsMgPerL = mineralsToIntVars(salts, model);
        IntVar cost = cost(
                            waters.stream()
                                 .map(w -> w.recipe().profilesRatio().get(0).profile())
                                 .map(p -> new SolverProfile(p))
                                 .collect(Collectors.toList()),
                            varWaters,
                            model,
                            target,
                            varMineralsMgPerL,
                            salts.stream().map(ma -> new MineralProfileSolver(ma.profile())).collect(Collectors.toList())
                           );

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

    private IntVar[] mineralsToIntVars(List<MineralAddition> minerals, Model model) {
        return minerals.stream()
                .map(m -> model.intVar(m.profile().name() + " mg/L", 0, (int) (3) ))
                .toArray(IntVar[]::new);
    }

    private enum MineralContent {
        calcio, magnesio, sodio, bicarbonati, solfato, cloruro;
    }

    private IntVar cost(List<SolverProfile> profiles,
                        IntVar[] varWaters,
                        Model model,
                        SolverTarget target,
                        IntVar[] varMineralsMgPerL,
                        List<MineralProfileSolver> salts) {
        Map<MineralContent, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>>> mineralGetters = new HashMap<>();
        mineralGetters.put(MineralContent.calcio, new Pair<>(x -> x.calcioMgPerL(), m -> m.calcioRatio()));
        mineralGetters.put(MineralContent.magnesio, new Pair<>(x -> x.magnesioMgPerL(), m -> m.magnesioRatio()));
        mineralGetters.put(MineralContent.sodio, new Pair<>(x -> x.sodioMgPerL(), m -> m.sodioRatio()));
        mineralGetters.put(MineralContent.bicarbonati, new Pair<>(x -> x.bicarbonatiMgPerL(), m -> m.bicarbonatiRatio()));
        mineralGetters.put(MineralContent.solfato, new Pair<>(x -> x.solfatoMgPerL(), m -> m.solfatoRatio()));
        mineralGetters.put(MineralContent.cloruro, new Pair<>(x -> x.cloruroMgPerL(), m -> m.cloruroRatio()));

        Map<MineralContent, IntVar> mineralContentSum = new HashMap<>();

        for (int i = 0; i < profiles.size(); i++) {
            IntVar varWater = varWaters[i];
            SolverProfile currProfile = profiles.get(i);

            mineralGetters.forEach((mineral, getters) -> mineralContentSum.merge(
                    mineral,
                    costAddend(model, varWater, currProfile, mineral, getters.getValue0()),
                    (IntVar aggCost, IntVar addendo) -> aggCost.add(addendo).intVar()));
        }

        for (int i = 0; i < salts.size(); i++) {
//            mineralContentSum.merge(
//                    mc_getter.getKey(),
//                    costAddend(model, varWater, t, mc_getter.getKey(), mc_getter.getValue()),
//                    (IntVar aggCost, IntVar addendo) -> aggCost.add(addendo).intVar());
        }

        /*
        for (int i = 0; i < minerals.size(); i++) {
           MineralProfileSolver p = new MineralProfileSolver(minerals.get(i).profile());
            mSumCalcio      = mSumCalcio      .add(varMineralsMgPerL[i]).mul((int) (p.calcioRatio()      )).realVar();
            mSumMagnesio    = mSumMagnesio    .add(varMineralsMgPerL[i]).mul((int) (p.magnesioRatio()    )).realVar();
            mSumSodio       = mSumSodio       .add(varMineralsMgPerL[i]).mul((int) (p.sodioRatio()       )).realVar();
            mSumBicarbonati = mSumBicarbonati .add(varMineralsMgPerL[i]).mul((int) (p.bicarbonatiRatio() )).realVar();
            mSumSolfato     = mSumSolfato     .add(varMineralsMgPerL[i]).mul((int) (p.solfatoRatio()     )).realVar();
            mSumCloruro     = mSumCloruro     .add(varMineralsMgPerL[i]).mul((int) (p.cloruroRatio()     )).realVar();
        }
         */

        IntVar cost = mineralGetters.entrySet().stream()
                .map(mc_f -> mineralContentSum.get(mc_f.getKey())
                                              .div(target.liters())
                                              .dist(mc_f.getValue().getValue0().apply(target)).intVar())
                .reduce(model.intVar("WaterDistance", 0), (a, b) -> a.add(b).intVar());

        return cost;
    }

    private IntVar costAddend(Model model, IntVar varWater, SolverProfile profile, MineralContent mineralContent, Function<IMgPerL, Integer> getter) {
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
