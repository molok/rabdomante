package alebolo.rabdomante;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class CS3 {
    WaterProfile santanna = new WaterProfile(10, 0, 1, 8, 0, 26, "santanna");
    WaterProfile milano = new WaterProfile(70, 15, 12, 42, 27, 228, "milano");
    WaterProfile boario = new WaterProfile(131, 40, 5, 240, 4, 303, "boardio");
    WaterProfile levissima = new WaterProfile(21, 2, 2, 17, 0, 57, "levissima");
    WaterProfile eva = new WaterProfile(10, 4, 0, 2, 0, 48, "eva");
    WaterProfile norda = new WaterProfile(11, 3, 2, 6, 1, 52, "norda");
    WaterProfile vera = new WaterProfile(35, 12, 2, 19, 3, 148, "vera");
    WaterProfile vitasnella = new WaterProfile(86, 26, 3, 83, 2, 301, "vitasnella");
    WaterProfile dolomiti = new WaterProfile(8, 9, 1, 22, 1, 95, "dolomiti");
    WaterProfile sanbern = new WaterProfile(9, 1, 1, 2, 1, 30, "sanberardo");
    WaterProfile distilled = new WaterProfile(0, 0, 0, 0, 0, 0, "distillata");

    WaterProfile yellowDry = new WaterProfile(50, 10, 5, 105, 45, 0, "yellow dry");

    SaltProfile gypsum = new SaltProfile(23, 0, 0, 56, 0, 0, "gypsum");
    SaltProfile tableSalt = new SaltProfile(0, 0, 39, 0, 62, 0, "tableSalt");
    SaltProfile calciumChloride = new SaltProfile(36, 0, 0, 0, 64, 0, "calcium chloride");
    SaltProfile magnesiumChloride = new SaltProfile(0, 12, 0, 0, 35, 0, "magnesium chloride");
    SaltProfile espomSalt = new SaltProfile(0, 1, 0, 0, 35, 0, "epsom salt");
    SaltProfile bakingSoda = new SaltProfile(0, 0, 27, 0, 0, 72, "baking soda");
    SaltProfile piclingLime = new SaltProfile(45, 0, 0, 0, 0, 164, "pickling lime");

    @Test public void all_vars_big() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(gypsum, 10000),
                new Salt(tableSalt, 10000),
                new Salt(calciumChloride, 10000),
                new Salt(magnesiumChloride, 10000),
                new Salt(espomSalt, 10000),
                new Salt(bakingSoda, 10000),
                new Salt(piclingLime, 10000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( santanna , Integer.MAX_VALUE),
                new Water( milano , Integer.MAX_VALUE),
                new Water( boario , Integer.MAX_VALUE),
                new Water( levissima , Integer.MAX_VALUE),
                new Water( eva , Integer.MAX_VALUE),
                new Water( norda , Integer.MAX_VALUE),
                new Water( vera , Integer.MAX_VALUE),
                new Water( vitasnella , Integer.MAX_VALUE),
                new Water( dolomiti , Integer.MAX_VALUE),
                new Water( sanbern , Integer.MAX_VALUE)
//                new Water( distilled , Integer.MAX_VALUE)
        );

        Water target = new Water(yellowDry, 1000);
        Optional<Recipe> solution = solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }

    @Test public void all_vars() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(gypsum, 1000),
                new Salt(tableSalt, 1000),
                new Salt(calciumChloride, 1000),
                new Salt(magnesiumChloride, 1000),
                new Salt(espomSalt, 1000),
                new Salt(bakingSoda, 1000),
                new Salt(piclingLime, 1000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( santanna , Integer.MAX_VALUE),
                new Water( milano , Integer.MAX_VALUE),
                new Water( boario , Integer.MAX_VALUE),
                new Water( levissima , Integer.MAX_VALUE),
                new Water( eva , Integer.MAX_VALUE),
                new Water( norda , Integer.MAX_VALUE),
                new Water( vera , Integer.MAX_VALUE),
                new Water( vitasnella , Integer.MAX_VALUE),
                new Water( dolomiti , Integer.MAX_VALUE),
                new Water( sanbern , Integer.MAX_VALUE),
                new Water( distilled , Integer.MAX_VALUE)
        );

        Water target = new Water(yellowDry, 100);
        Optional<Recipe> solution = solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }


    @Test public void happy() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(distilled, Integer.MAX_VALUE));
        Water target = new Water(distilled, 10);

        Optional<Recipe> solution = solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();
        Assertions.assertThat(solution.get().waters).containsExactly(new Water(distilled, 10));
    }

    @Test public void not_enough_water() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(distilled, 9));
        Water target = new Water(distilled, 10);

        Optional<Recipe> solution = solve(target, mySalts, myWater);
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

        Optional<Recipe> solution = solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();
        Assertions.assertThat(solution.get().waters).containsOnly(new Water(pa, 15), new Water(pb, 15));
        Assertions.assertThat(solution.get().salts).isEmpty();
    }

    @Test public void salt() {
        SaltProfile magicSalt = new SaltProfile(5, 5, 5, 5, 5, 5, "magicSalt");
        SaltProfile badSalt = new SaltProfile(301, 301, 301, 301, 301, 301, "magicSalt");
        List<Salt> mySalts = Arrays.asList(new Salt(magicSalt, 1000 * 10), new Salt(badSalt, 1000 * 10));
        List<Water> myWater = Arrays.asList(new Water(distilled, Integer.MAX_VALUE));

        Water target = new Water(new WaterProfile(15, 15, 15, 15, 15, 15, "target"), 100);

        Optional<Recipe> solution = solve(target, mySalts, myWater);
        Assertions.assertThat(solution).isPresent();
        Assertions.assertThat(solution.get().waters).containsOnly(new Water(distilled, 100));
        Assertions.assertThat(solution.get().salts).containsOnly(new Salt(magicSalt, 300));
    }

    @Test public void antani() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(gypsum, 100),
                new Salt(tableSalt, 1000),
                new Salt(bakingSoda, 1000));

        List<Water> myWater = Arrays.asList(
                new Water(milano, Integer.MAX_VALUE),
                new Water(santanna, 1),
                new Water(sanbern, 1));

        Water target = new Water(yellowDry, 20);

        Optional<Recipe> recipe = solve(target, mySalts, myWater);
        Assertions.assertThat(recipe.isPresent()).isTrue();
        System.out.println(recipe.toString());
        System.out.println(target.toString());
    }

    private Optional<Recipe> solve(Water target, List<Salt> mySalts, List<Water> myWater) {
        return new WaterSolver().solve(target, mySalts, myWater);
    }

    @Test public void testRecipe() {
        Recipe recipe = new Recipe(Arrays.asList(new Water(santanna, 10)), Arrays.asList(), 0);
        System.out.println(recipe);
    }
}
