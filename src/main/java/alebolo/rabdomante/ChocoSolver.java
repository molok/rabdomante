package alebolo.rabdomante;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
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
        IntVar sumCalcio = model.intVar("costCalcio", 0);
        IntVar sumMagnesio = model.intVar("costMagnesio", 0);
        IntVar sumSodio = model.intVar("costSodio", 0);
        IntVar sumBicarbonati = model.intVar("costBicarbonati", 0);
        IntVar sumSolfato = model.intVar("costSolfato", 0);
        IntVar sumCloruro = model.intVar("costCloruro", 0);

        /* creiamo funzione costo */
        for (int i = 0; i < profiles.size(); i++) {
            SolverProfile p = profiles.get(i);
            sumCalcio = sumCalcio.add(model.intVar(p.calcioMgPerL()).mul(varWaters[i])).intVar();
            sumMagnesio = sumMagnesio.add(model.intVar(p.magnesioMgPerL()).mul(varWaters[i])).intVar();
            sumSodio = sumSodio.add(model.intVar(p.sodioMgPerL()).mul(varWaters[i])).intVar();
            sumBicarbonati = sumBicarbonati.add(model.intVar(p.bicarbonatiMgPerL()).mul(varWaters[i])).intVar();
            sumSolfato = sumSolfato.add(model.intVar(p.solfatoMgPerL()).mul(varWaters[i])).intVar();
            sumCloruro = sumCloruro.add(model.intVar(p.cloruroMgPerL()).mul(varWaters[i])).intVar();
        }

        IntVar cost = model.intVar("WaterDistance", 0)
                    .add(sumCalcio.dist(target.calcioMgPerL() * target.liters()))
                    .add(sumMagnesio.dist(target.magnesioMgPerL() * target.liters()))
                    .add(sumSodio.dist(target.sodioMgPerL() * target.liters()))
                    .add(sumBicarbonati.dist(target.bicarbonatiMgPerL() * target.liters()))
                    .add(sumSolfato.dist(target.solfatoMgPerL() * target.liters()))
                    .add(sumCloruro.dist(target.cloruroMgPerL() * target.liters()))
                    .intVar();

        return cost;
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
