package alebolo.rabdomante;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.expression.discrete.arithmetic.ArExpression;
import org.chocosolver.solver.variables.IntVar;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    private IntVar cost(List<SolverProfile> profiles, IntVar[] varWaters, Model model, SolverTarget target) {
        IntVar sumCalcioMg = model.intVar("costCalcio", 0);
        IntVar sumMagnesioMg = model.intVar("costMagnesio", 0);
        IntVar sumSodioMg = model.intVar("costSodio", 0);
        IntVar sumBicarbonatiMg = model.intVar("costBicarbonati", 0);
        IntVar sumSolfatoMg = model.intVar("costSolfato", 0);
        IntVar sumCloruroMg = model.intVar("costCloruro", 0);

        /* creiamo funzione costo */
        for (int i = 0; i < profiles.size(); i++) {
            SolverProfile p = profiles.get(i);
            sumCalcioMg = sumCalcioMg.add(model.intVar(p.calcioMgPerL()).mul(varWaters[i])).intVar();
            sumMagnesioMg = sumMagnesioMg.add(model.intVar(p.magnesioMgPerL()).mul(varWaters[i])).intVar();
            sumSodioMg = sumSodioMg.add(model.intVar(p.sodioMgPerL()).mul(varWaters[i])).intVar();
            sumBicarbonatiMg = sumBicarbonatiMg.add(model.intVar(p.bicarbonatiMgPerL()).mul(varWaters[i])).intVar();
            sumSolfatoMg = sumSolfatoMg.add(model.intVar(p.solfatoMgPerL()).mul(varWaters[i])).intVar();
            sumCloruroMg = sumCloruroMg.add(model.intVar(p.cloruroMgPerL()).mul(varWaters[i])).intVar();
        }

        int liters = target.liters();
        IntVar cost = model.intVar("WaterDistance", 0)
                    .add(distance(liters, sumCalcioMg, target.calcioMgPerL()))
                    .add(distance(liters, sumMagnesioMg, target.magnesioMgPerL()))
                    .add(distance(liters, sumSodioMg, target.sodioMgPerL()))
                    .add(distance(liters, sumBicarbonatiMg, target.bicarbonatiMgPerL()))
                    .add(distance(liters, sumSolfatoMg, target.solfatoMgPerL()))
                    .add(distance(liters, sumCloruroMg, target.cloruroMgPerL()))
                    .intVar();

        return cost;
    }

    private ArExpression distance(int targetLiters, IntVar sumMg, int targetMgPerL) {
        return sumMg.div(targetLiters).dist(targetMgPerL);
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
