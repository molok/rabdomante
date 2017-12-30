package alebolo.rabdomante;

import org.apache.commons.collections4.CollectionUtils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.expression.discrete.arithmetic.ArExpression;
import org.chocosolver.solver.variables.IntVar;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChocoSolver implements IWSolver {
    WaterMixer mixer = new WaterMixer();
    int scaleUpDouble = 1000;

    List<Pair<String,IntVar>> toWatch = new ArrayList<>();

    public Optional<Pair<Integer, Water>> solve2(Water xtarget, List<Water> waters, List<MineralAddition> salts) {
        toWatch = new ArrayList<>(); // reset
        Model model = new Model("waterModel");

        SolverTarget target = new SolverTarget(xtarget, scaleUpDouble);

        waters.stream().forEach(w -> { if (w.recipe().saltsRatio().size() > 0) throw new RuntimeException("supportiamo solo basewaters (single profile no salts)"); });

        IntVar[] varWaters = watersToIntVars(waters, model, target.liters());
        model.sum(varWaters, "=", target.liters()).post();
        IntVar[] varMineralsMgPerL = mineralsToIntVars(salts, model);
        IntVar cost = cost(
                waters.stream()
                        .map(w -> w.recipe().profilesRatio().get(0).profile())
                        .map(p -> new SolverProfile(p, scaleUpDouble))
                        .collect(Collectors.toList()),
                varWaters,
                model,
                target,
                varMineralsMgPerL,
                salts.stream().map(ma -> new MineralProfileSolver(ma.profile(), scaleUpDouble)).collect(Collectors.toList())
        );

        /* se non imposti minimize non ritorni il minimo, capra!!!! */
        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();
        Water res = null;
        Integer xcost = null;
        while(solver.solve()) {
            xcost = cost.getValue();
            System.out.println("\n\ncost = " + cost.getValue() + ", solution = " + Arrays.toString(varWaters) + ", " + Arrays.toString(varMineralsMgPerL));
            res = water(waters, salts, varWaters, varMineralsMgPerL);
            System.out.println("watched:" + toWatch.stream().map(watched -> "\n"+watched.getValue0() + ":" + watched.getValue1().toString()).collect(Collectors.joining(", ")));
            if (res != null) {
                System.out.println("mydistance:" + DistanceCalculator.distanceCoefficient(target.water(), res) + ", w:" + res.description());
            }
        }
        return Optional.ofNullable( res == null ? null : new Pair<>(xcost, res));
    }


    @Override public Optional<Water> solve(Water xtarget, List<Water> waters, List<MineralAddition> salts) {
        return solve2(xtarget, waters, salts).map(x -> x.getValue1());
    }

    private Water water(List<Water> waters, List<MineralAddition> salts, IntVar[] varWaters, IntVar[] varMineralsMgPerL) {
        Water res;
        res = null;
        for (int i = 0; i < varWaters.length; i++) {
            IntVar varCurr = varWaters[i];
            if (varCurr.getValue() > 0) {
                Water curr = new Water(varCurr.getValue(), waters.get(i).recipe());
                res = res == null ? curr : mixer.merge(res, curr);
            }
        }

        List<MineralRatio> minAdd = new ArrayList<>();
        for (int i = 0; i < varMineralsMgPerL.length; i++) {
            IntVar varCurr = varMineralsMgPerL[i];
            if (varCurr.getValue() > 0) {
                minAdd.add(new MineralRatio(salts.get(i).profile(), varCurr.getValue()));
            }
        }

        res = new Water(
                res.liters(),
                new Recipe(
                        res.recipe().profilesRatio(),
                        new ArrayList<>(
                                CollectionUtils.union(
                                        res.recipe().saltsRatio(), minAdd))));
        return res;
    }

    private IntVar[] mineralsToIntVars(List<MineralAddition> minerals, Model model) {
        return minerals.stream()
                .map(m -> model.intVar(m.profile().name() + " mg/L", 0, 100 ))
                .toArray(IntVar[]::new);
    }

    private enum MineralContent {
        calcio, magnesio, sodio, bicarbonati, solfato, cloruro;
    }

    public IntVar cost(List<SolverProfile> profiles,
                       IntVar[] varWaters,
                       Model model,
                       SolverTarget target,
                       IntVar[] varMineralsMgPerL,
                       List<MineralProfileSolver> salts) {
        System.out.println("target:"+target.toString());
        System.out.println("costSalts:"+Arrays.toString(salts.toArray(new MineralProfileSolver[]{})));
        Map<MineralContent, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>>> mineralGetters = new HashMap<>();
        mineralGetters.put(MineralContent.calcio, new Pair<>(x -> x.calcioMgPerL(), m -> m.calcioRatio()));
        mineralGetters.put(MineralContent.magnesio, new Pair<>(x -> x.magnesioMgPerL(), m -> m.magnesioRatio()));
        mineralGetters.put(MineralContent.sodio, new Pair<>(x -> x.sodioMgPerL(), m -> m.sodioRatio()));
        mineralGetters.put(MineralContent.bicarbonati, new Pair<>(x -> x.bicarbonatiMgPerL(), m -> m.bicarbonatiRatio()));
        mineralGetters.put(MineralContent.solfato, new Pair<>(x -> x.solfatoMgPerL(), m -> m.solfatoRatio()));
        mineralGetters.put(MineralContent.cloruro, new Pair<>(x -> x.cloruroMgPerL(), m -> m.cloruroRatio()));

        Map<MineralContent, IntVar> mineralContentSum = new HashMap<>();

        sumWaters(profiles, varWaters, model, mineralGetters, mineralContentSum);
        toWatch.add(new Pair<>("sodiosumWater", mineralContentSum.get(MineralContent.sodio)));
        sumSalts(model, varMineralsMgPerL, salts, mineralGetters, mineralContentSum);
        IntVar waterDistance = getWaterDistance(model, target, mineralGetters, mineralContentSum);
        toWatch.add(new Pair<>("sodiosumTot", mineralContentSum.get(MineralContent.sodio)));
        toWatch.add(new Pair<>("KOST", waterDistance));
        return waterDistance;
    }

    private IntVar getWaterDistance
            (Model model,
             SolverTarget target,
             Map<MineralContent, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>>> mineralGetters,
             Map<MineralContent, IntVar> mineralContentSum) {
        Stream<IntVar> wd = mineralContentSum.entrySet().stream()
                .map(mc_f -> {
                    IntVar totSodio = mineralContentSum.get(mc_f.getKey());
                    toWatch.add(new Pair<>("totSodio", totSodio));
                    ArExpression mgPerLCandidate = totSodio.div(target.liters());
                    Integer mgPerLTarget = mineralGetters.get(mc_f.getKey()).getValue0().apply(target);
                    toWatch.add(new Pair<>("targetMgPerL_" + mc_f.getKey().name(), model.intVar(mgPerLTarget)));
                    toWatch.add(new Pair<>("candidateMgPerL_" + mc_f.getKey().name(), mgPerLCandidate.intVar()));
                    IntVar distance = mgPerLCandidate.dist(mgPerLTarget).intVar();
                    toWatch.add(new Pair<>("waterdistance_" + mc_f.getKey().name(), distance));
                    return distance.mul(scaleUpDouble).intVar();
                });
        return wd.reduce(model.intVar("WaterDistance", 0), (a, b) -> a.add(b).intVar())
                    .div(scaleUpDouble).intVar();
    }

    private void sumWaters(
            List<SolverProfile> profiles,
            IntVar[] varWaters,
            Model model,
            Map<MineralContent, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>>> mineralGetters,
            Map<MineralContent, IntVar> mineralContentSum)
    {
        for (int i = 0; i < profiles.size(); i++) {
            System.out.println("profile:"+profiles.get(i));
            IntVar varWater = varWaters[i];
            SolverProfile currProfile = profiles.get(i);

            mineralGetters.forEach((mineral, getters) -> {
                IntVar value = model.intVar("cost_" + mineral.name(), getters.getValue0().apply(currProfile)).mul(varWater).div(scaleUpDouble).intVar();
                toWatch.add(new Pair<>("valueSumWater", value));

                mineralContentSum.merge(
                        mineral,
                        value,
                        (IntVar aggCost, IntVar addendo) -> aggCost.add(addendo).intVar());
            });
        }
    }

    private void sumSalts(Model model,
                          IntVar[] varMineralsMgPerL,
                          List<MineralProfileSolver> salts,
                          Map<MineralContent, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>>> mineralGetters,
                          Map<MineralContent, IntVar> mineralContentSum) {

        for (int i = 0; i < salts.size(); i++) {
            IntVar varMineral = varMineralsMgPerL[i];
            MineralProfileSolver salt = salts.get(0);

            for (Map.Entry<MineralContent, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>>>
                 mineralContentGetter : mineralGetters.entrySet()) {
                MineralContent mineral = mineralContentGetter.getKey();
                Function<IMineralRatio, Integer> getter = mineralContentGetter.getValue().getValue1();

                int precision = 100;
                IntVar varMineralMul100 = model.intScaleView(varMineral, precision);

                IntVar mgPerL = model.intVar("mCost_" + mineral.name(), getter.apply(salt)).mul(varMineralMul100).div(precision).intVar();

                toWatch.add(new Pair<>("mCost_" + mineral.name(), mgPerL));

                mineralContentSum.merge(
                        mineral,
                        mgPerL,
                        (IntVar aggCost, IntVar addendo) -> (aggCost.add(addendo)).intVar());
            }
//            mineralGetters.forEach((MineralContent mineral, Pair<Function<IMgPerL, Integer>, Function<IMineralRatio, Integer>> mineralContentGetters) ->
//                    mineralContentSum.merge(
//                            mineral,
//                            model.intVar("mCost_" + mineral.name(), mineralContentGetters.getValue1().apply(salt)).mul(varMineral).intVar(),
//                            (IntVar aggCost, IntVar addendo) -> aggCost.add(addendo).intVar()));

        }
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
