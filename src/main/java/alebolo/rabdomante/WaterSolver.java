package alebolo.rabdomante;

import java.util.List;
import java.util.Optional;

public interface WaterSolver {
    Optional<Recipe> solve(Water target, List<Salt> availableSalts, List<Water> availableWaters);
}
