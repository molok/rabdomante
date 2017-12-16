package alebolo.rabdomante;

import java.util.List;
import java.util.Optional;

public interface IWSolver {
    Optional<Water> solve(Water target, List<Water> waters, List<MineralAddition> salts);
}
