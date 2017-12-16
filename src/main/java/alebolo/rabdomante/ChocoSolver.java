package alebolo.rabdomante;

import com.google.common.primitives.Ints;
import org.apache.commons.collections4.CollectionUtils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;

import java.util.*;
import java.util.stream.Collectors;

public class ChocoSolver implements IWSolver {
    WaterMixer mixer = new WaterMixer();

    @Override public Optional<Water> solve(Water xtarget, List<Water> waters, List<MineralAddition> minerals) {
        Model model = new Model("waterModel");

        SolverTarget target = new SolverTarget(xtarget);

        /* FIXME decidere se gestire solo BaseWater oppure tutti i profili delle acque */
        List<SolverProfile> profiles = waters.stream()
                                    .map(w -> w.recipe().profilesRatio().get(0).profile())
                                    .map(p -> new SolverProfile(p))
                                    .collect(Collectors.toList());

        IntVar[] varWaters = watersToIntVars(waters, model, target.liters());
        model.sum(varWaters, "=", target.liters()).post();

        IntVar[] varMineralsMgPerL = mineralsToIntVars(minerals, model);
        IntVar cost = cost(profiles, varWaters, model, target, minerals, varMineralsMgPerL);

//        model.setObjective(Model.MINIMIZE, cost);
        Solver solver = model.getSolver();
        Water res = null;
        while(solver.solve()) {
            solver.printStatistics();
            System.out.println("cost = " + cost.getValue() + ", solution = " + Arrays.toString(varWaters) + ", " + Arrays.toString(varMineralsMgPerL));

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
                    minAdd.add(new MineralRatio(minerals.get(i).profile(), varCurr.getValue()));
                }
            }

            res = new Water(
                    res.liters(),
                    new Recipe(
                            res.recipe().profilesRatio(),
                            new ArrayList<>(
                                    CollectionUtils.union(
                                            res.recipe().saltsRatio(), minAdd))));

            if (res != null) {
                System.out.println("mydistance:" +DistanceCalculator.distanceCoefficient(target.water(), res) + ", w:" +res.description());
            }
        }

        return Optional.ofNullable(res);
    }

    private IntVar cost(List<SolverProfile> profiles, IntVar[] varWaters, Model model, SolverTarget target, List<MineralAddition> minerals, RealVar[] varMineralsMgPerL) {
        RealVar wSumCalcio = model.realVar("costCalcioW", 0);
        RealVar wSumMagnesio = model.realVar("costMagnesioW", 0);
        RealVar wSumSodio = model.realVar("costSodioW", 0);
        RealVar wSumBicarbonati = model.realVar("costBicarbonatiW", 0);
        RealVar wSumSolfato = model.realVar("costSolfatoW", 0);
        RealVar wSumCloruro = model.realVar("costCloruroW", 0);

        /* creiamo funzione costo */
        for (int i = 0; i < profiles.size(); i++) {
            SolverProfile p = profiles.get(i);
            RealVar currLiters = model.realVar((double) varWaters[i].getValue());

            wSumCalcio = wSumCalcio.add(model.realVar(p.calcioMgPerL()).mul(currLiters)).realVar(0.001);
            wSumMagnesio = wSumMagnesio.add(model.realVar(p.magnesioMgPerL()).mul(currLiters)).realVar(0.001);
            wSumSodio = wSumSodio.add(model.realVar(p.sodioMgPerL()).mul(currLiters)).realVar(0.001);
            wSumBicarbonati = wSumBicarbonati.add(model.realVar(p.bicarbonatiMgPerL()).mul(currLiters)).realVar(0.001);
            wSumSolfato = wSumSolfato.add(model.realVar(p.solfatoMgPerL()).mul(currLiters)).realVar();
            wSumCloruro = wSumCloruro.add(model.realVar(p.cloruroMgPerL()).mul(currLiters)).realVar();
        }

        RealVar mSumCalcio = model.realVar("costCalcioWM", 0);
        RealVar mSumMagnesio = model.realVar("costMagnesioM", 0);
        RealVar mSumSodio = model.realVar("costSodioM", 0);
        RealVar mSumBicarbonati = model.realVar("costBicarbonatiM", 0);
        RealVar mSumSolfato = model.realVar("costSolfatoM", 0);
        RealVar mSumCloruro = model.realVar("costCloruroM", 0);
        for (int i = 0; i < minerals.size(); i++) {
           MineralProfileSolver p = new MineralProfileSolver(minerals.get(i).profile());
            mSumCalcio      = mSumCalcio      .add(varMineralsMgPerL[i]).mul((int) (p.calcioRatio()      )).realVar();
            mSumMagnesio    = mSumMagnesio    .add(varMineralsMgPerL[i]).mul((int) (p.magnesioRatio()    )).realVar();
            mSumSodio       = mSumSodio       .add(varMineralsMgPerL[i]).mul((int) (p.sodioRatio()       )).realVar();
            mSumBicarbonati = mSumBicarbonati .add(varMineralsMgPerL[i]).mul((int) (p.bicarbonatiRatio() )).realVar();
            mSumSolfato     = mSumSolfato     .add(varMineralsMgPerL[i]).mul((int) (p.solfatoRatio()     )).realVar();
            mSumCloruro     = mSumCloruro     .add(varMineralsMgPerL[i]).mul((int) (p.cloruroRatio()     )).realVar();
        }

        RealVar cost = model.realVar("WaterDistance", 0)
                    .add(mSumCalcio.add(wSumCalcio.div(target.liters())).realVar().dist(target.calcioMgPerL()))
                    .add(mSumMagnesio.add(wSumMagnesio.div(target.liters())).realVar().dist(target.magnesioMgPerL()))
                    .add(mSumSodio.add(wSumSodio.div(target.liters())).realVar().dist(target.sodioMgPerL()))
                    .add(mSumBicarbonati.add(wSumBicarbonati.div(target.liters())).realVar().dist(target.bicarbonatiMgPerL()))
                    .add(mSumSolfato.add(wSumSolfato.div(target.liters())).realVar().dist(target.solfatoMgPerL()))
                    .add(mSumCloruro.add(wSumCloruro.div(target.liters())).realVar().dist(target.cloruroMgPerL()))
                    .realVar();

        return cost;
    }

    private IntVar[] mineralsToIntVars(List<MineralAddition> minerals, Model model) {
        return minerals.stream()
                .map(m -> model.intVar(m.profile().name() + " mg/L", 0, (int) (3) ))
                .toArray(IntVar[]::new);
    }

    private IntVar[] watersToIntVars(List<Water> waters, Model model, int batchLiters) {
        /* FIXME diamo per assunto che non ci siano water con più profile */
        IntVar[] varWaters = new IntVar[waters.size()];
        for (int i = 0; i < waters.size(); i++) {
            varWaters[i] = model.intVar
                            ( waters.get(i)
                                    .recipe().profilesRatio().get(0)
                                    .profile().name()
                            , domain(batchLiters, (int) waters.get(i).liters()));
        }
        return varWaters;
    }

    private int[] domain(int batchLiters, int liters) {
        final double MIN_WATER_RATIO = 0.1;
        final int MIN_WATER_ADDITION_L = 1;

        int step = Math.max((int) (batchLiters * MIN_WATER_RATIO), MIN_WATER_ADDITION_L);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= liters; i++) {
            if ((i % step) == 0 || i == batchLiters || i == liters) {
                res.add(i);
            }
        }
        return Ints.toArray(res);
    }
}
