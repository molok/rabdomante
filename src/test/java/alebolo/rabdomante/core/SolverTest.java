package alebolo.rabdomante.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SolverTest {
    private final ChocoSolver solver = new ChocoSolver();

    @Test public void should_return_itself() {
        Water target = new Water(WaterProfiles.EVA, 31);
        Optional<WSolution> res = solver.solve(
                target,
                Arrays.asList(
                        new Salt(SaltProfiles.TABLE_SALT, 100),
                        new Salt(SaltProfiles.EPSOM_SALT, 100),
                        new Salt(SaltProfiles.GYPSUM, 100)),
                Arrays.asList(
                        target,
                        new Water(WaterProfiles.BOARIO, 100),
                        new Water(WaterProfiles.DISTILLED, 100),
                        new Water(WaterProfiles.SANBERARDO, 100)),
                10L);
        assertThat(res.isPresent()).isTrue();
        assertThat(res.get().recipe.waters.size()).isEqualTo(1);
        assertThat(WaterProfile.sameProfile(res.get().recipe.waters.get(0), target)).isTrue();
        assertThat(res.get().recipe.waters.get(0).liters).isEqualTo(target.liters);
        assertThat(res.get().recipe.salts.size()).isEqualTo(0);
    }

    @Test public void should_return_empty_optional_if_not_solvable() {
        Water target = new Water(WaterProfiles.EVA, 10);
        Optional<WSolution> res = solver.solve(target, Arrays.asList(), Arrays.asList(new Water(WaterProfiles.DISTILLED, 9)));
        assertThat(res.isPresent()).isFalse();
    }
}
