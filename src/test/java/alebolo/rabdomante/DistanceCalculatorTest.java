package alebolo.rabdomante;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DistanceCalculatorTest {
    @Test public void same_water_diff_zero(){
        Water distilled = new Water(10, Recipe.create(TestUtils.distilled));
        Assertions.assertThat(DistanceCalculator.distanceCoefficient(distilled, distilled)).isEqualTo(0.);
    }

    @Test public void different_water_diff_gt_zero(){
        Water distilled = new Water(10, Recipe.create(TestUtils.distilled));
        Water sanbernardo = new Water(10, Recipe.create(TestUtils.sanBernarndo));
        Assertions.assertThat(DistanceCalculator.distanceCoefficient(distilled, sanbernardo)).isGreaterThan(0.);
    }
}