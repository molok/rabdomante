package alebolo.rabdomante.core;

import java.util.List;
import java.util.Optional;

public interface WaterSolver {
    Optional<Recipe> solve(Water target, List<Salt> availableSalts, List<Water> availableWaters);
    Optional<Recipe> solve(Water target, List<Salt> availableSalts, List<Water> availableWaters, Long secondsTimeout);
}
