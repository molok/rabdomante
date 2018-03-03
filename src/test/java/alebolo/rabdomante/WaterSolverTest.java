package alebolo.rabdomante;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class WaterSolverTest {
    @Test public void all_vars_big() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfile.GYPSUM, 10000),
                new Salt(SaltProfile.TABLE_SALT, 10000),
                new Salt(SaltProfile.CALCIUM_CHLORIDE, 10000),
                new Salt(SaltProfile.MAGNESIUM_CHLORIDE, 10000),
                new Salt(SaltProfile.EPSOM_SALT, 10000),
                new Salt(SaltProfile.BAKING_SODA, 10000),
                new Salt(SaltProfile.PICKLING_LIME, 10000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfile.SANTANNA, Integer.MAX_VALUE),
                new Water( WaterProfile.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfile.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfile.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfile.EVA, Integer.MAX_VALUE),
                new Water( WaterProfile.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfile.VERA, Integer.MAX_VALUE),
                new Water( WaterProfile.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfile.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfile.SANBERARDO, Integer.MAX_VALUE)
//                new Water( DISTILLED , Integer.MAX_VALUE)
        );

        Water target = new Water(WaterProfile.YELLOW_DRY, 1000);
        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }

    @Test public void all_vars() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfile.GYPSUM, 1000),
                new Salt(SaltProfile.TABLE_SALT, 1000),
                new Salt(SaltProfile.CALCIUM_CHLORIDE, 1000),
                new Salt(SaltProfile.MAGNESIUM_CHLORIDE, 1000),
                new Salt(SaltProfile.EPSOM_SALT, 1000),
                new Salt(SaltProfile.BAKING_SODA, 1000),
                new Salt(SaltProfile.PICKLING_LIME, 1000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfile.SANTANNA, Integer.MAX_VALUE),
                new Water( WaterProfile.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfile.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfile.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfile.EVA, Integer.MAX_VALUE),
                new Water( WaterProfile.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfile.VERA, Integer.MAX_VALUE),
                new Water( WaterProfile.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfile.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfile.SANBERARDO, Integer.MAX_VALUE),
                new Water( WaterProfile.DISTILLED, Integer.MAX_VALUE)
        );

        Water target = new Water(WaterProfile.YELLOW_DRY, 100);
        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }


    @Test public void happy() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(WaterProfile.DISTILLED, Integer.MAX_VALUE));
        Water target = new Water(WaterProfile.DISTILLED, 10);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();
        Assertions.assertThat(solution.get().waters).containsExactly(new Water(WaterProfile.DISTILLED, 10));
    }

    @Test public void not_enough_water() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(WaterProfile.DISTILLED, 9));
        Water target = new Water(WaterProfile.DISTILLED, 10);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isNotPresent();
    }

    @Test public void easy_50_50() {
        List<Salt> mySalts = Arrays.asList();

        WaterProfile pa = new WaterProfile(10, 10, 10, 10, 10, 10, "a");
        Water a = new Water(pa, 100);

        WaterProfile pb = new WaterProfile(0, 0, 0, 0, 0, 0, "b");
        Water b = new Water(pb, 100);

        WaterProfile pc = new WaterProfile(100, 100, 100, 100, 100, 100, "c");
        Water c = new Water(pb, 100);

        List<Water> myWater = Arrays.asList( a, b, c );

        Water target = new Water(new WaterProfile(5, 5, 5, 5, 5, 5, "target"), 30);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();
        Assertions.assertThat(solution.get().waters).containsOnly(new Water(pa, 15), new Water(pb, 15));
        Assertions.assertThat(solution.get().salts).isEmpty();
    }

    @Test public void salt() {
        SaltProfile magicSalt = new SaltProfile(5, 5, 5, 5, 5, 5, "magicSalt");
        SaltProfile badSalt = new SaltProfile(301, 301, 301, 301, 301, 301, "magicSalt");
        List<Salt> mySalts = Arrays.asList(new Salt(magicSalt, 1000 * 10), new Salt(badSalt, 1000 * 10));
        List<Water> myWater = Arrays.asList(new Water(WaterProfile.DISTILLED, Integer.MAX_VALUE));

        Water target = new Water(new WaterProfile(15, 15, 15, 15, 15, 15, "target"), 100);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();
        Assertions.assertThat(solution.get().waters).containsOnly(new Water(WaterProfile.DISTILLED, 100));
        Assertions.assertThat(solution.get().salts).containsOnly(new Salt(magicSalt, 300));
    }

    @Test public void antani() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfile.GYPSUM, 100),
                new Salt(SaltProfile.TABLE_SALT, 1000),
                new Salt(SaltProfile.BAKING_SODA, 1000));

        List<Water> myWater = Arrays.asList(
                new Water(WaterProfile.MILANO, Integer.MAX_VALUE),
                new Water(WaterProfile.SANTANNA, 1),
                new Water(WaterProfile.SANBERARDO, 1));

        Water target = new Water(WaterProfile.YELLOW_DRY, 20);

        Optional<Recipe> recipe = new ChocoSolver().solve(target, mySalts, myWater);
        Assertions.assertThat(recipe.isPresent()).isTrue();
        System.out.println(recipe.toString());
        System.out.println(target.toString());
    }

    @Test public void testRecipe() {
        Recipe recipe = new Recipe(Arrays.asList(new Water(WaterProfile.SANTANNA, 10)), Arrays.asList(), 0);
        System.out.println(recipe);
    }
}
