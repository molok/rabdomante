package alebolo.rabdomante.core;

import org.chocosolver.solver.search.strategy.strategy.AbstractStrategy;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface WaterSolver {
    Optional<WSolution> solve(Water target, List<Salt> availableSalts, List<Water> availableWaters);
    Optional<WSolution> solve(Water target, List<Salt> availableSalts, List<Water> availableWaters, Long secondsTimeLimit);

    Optional<WSolution> solve(
            Water target,
            List<Salt> availableSalts,
            List<Water> availableWaters,
            Long secondsTimeLimit,
            Function<IntVar[], AbstractStrategy<IntVar>> strategyProvider
    );
}
