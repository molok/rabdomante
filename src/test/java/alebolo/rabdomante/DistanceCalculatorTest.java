package alebolo.rabdomante;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceCalculatorTest {
    @Test public void diff_is_abs() {
        Water a = new Water(1, Recipe.create(Profile.distilled, Arrays.asList(new MineralRatio(MineralProfile.FAKE_TABLE_SALT, 10))));
        Water b = new Water(1, Recipe.create(Profile.distilled, Arrays.asList(new MineralRatio(MineralProfile.FAKE_TABLE_SALT, 5))));
        double distance1 = DistanceCalculator.distanceCoefficient(a, b);

        Water c = new Water(100, Recipe.create(Profile.distilled, Arrays.asList(new MineralRatio(MineralProfile.FAKE_TABLE_SALT, 10))));
        Water d = new Water(100, Recipe.create(Profile.distilled, Arrays.asList(new MineralRatio(MineralProfile.FAKE_TABLE_SALT, 5))));
        double distance2 = DistanceCalculator.distanceCoefficient(c, d);

        assertThat(distance2).isEqualTo(distance1);
    }


    @Test public void same_water_diff_zero(){
        Water distilled = new Water(10, Recipe.create(TestUtils.distilled));
        assertThat(DistanceCalculator.distanceCoefficient(distilled, distilled)).isEqualTo(0.);
    }

    @Test public void different_water_diff_gt_zero(){
        Water distilled = new Water(10, Recipe.create(TestUtils.distilled));
        Water sanbernardo = new Water(10, Recipe.create(TestUtils.sanBernarndo));
        assertThat(DistanceCalculator.distanceCoefficient(distilled, sanbernardo)).isGreaterThan(0.);
    }
}