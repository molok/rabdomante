package alebolo.rabdomante;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DistanceCalculatorTest {
    @Test public void same_water_diff_zero(){
        Water2 distilled = new Water2(10, Recipe.create(TestUtils.distilled));
        Assertions.assertThat(DistanceCalculator.distanceCoefficient(distilled, distilled)).isEqualTo(0.);
    }

    @Test public void different_water_diff_gt_zero(){
        Water2 distilled = new Water2(10, Recipe.create(TestUtils.distilled));
        Water2 sanbernardo = new Water2(10, Recipe.create(TestUtils.sanBernarndo));
        Assertions.assertThat(DistanceCalculator.distanceCoefficient(distilled, sanbernardo)).isGreaterThan(0.);
    }
}