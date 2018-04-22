package alebolo.rabdomante.core;

import alebolo.rabdomante.xlsx.WaterProfileParser;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.strategy.AbstractStrategy;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class WaterSolverTest {
    @Test public void all_vars_big() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, 10000),
                new Salt(SaltProfiles.TABLE_SALT, 10000),
                new Salt(SaltProfiles.CALCIUM_CHLORIDE, 10000),
                new Salt(SaltProfiles.MAGNESIUM_CHLORIDE, 10000),
                new Salt(SaltProfiles.EPSOM_SALT, 10000),
                new Salt(SaltProfiles.BAKING_SODA, 10000),
                new Salt(SaltProfiles.PICKLING_LIME, 10000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfiles.SANTANNA_VIN, Integer.MAX_VALUE),
                new Water( WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfiles.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfiles.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfiles.EVA, Integer.MAX_VALUE),
                new Water( WaterProfiles.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VERA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfiles.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANBERARDO, Integer.MAX_VALUE)
//                new Water( DISTILLED , Integer.MAX_VALUE)
        );

        Water target = new Water(WaterProfiles.YELLOW_DRY, 1000);
        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }

    @Test public void brun() {
        WaterProfileParser parser = new WaterProfileParser();
        List<Water> all = parser.parse(this.getClass().getResourceAsStream("/brun.csv"));
        assertThat(all.size()).isEqualTo(66);
    }

        /* mistero: 14s */
//        solver.setSearch(Search.activityBasedSearch(
//                            Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));
        /* mistero 5s */
//        solver.setSearch(Search.domOverWDegSearch(
//                Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));
        /* 40s */
//        solver.setSearch(Search.inputOrderLBSearch(
//                Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));
        /* 22s */
//        solver.setSearch(Search.inputOrderUBSearch(
//                Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));
        /* 14s */
//        solver.setSearch(Search.intVarSearch(
//                Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));
        /* 7s */
//        solver.setSearch(Search.minDomLBSearch(
//                Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));
        /*23s*/
//        solver.setSearch(Search.minDomUBSearch(
//                Iterables.toArray(Iterables.concat(waterVars.values(), saltVars.values()), IntVar.class)));

    @Ignore
    @Test /* 7m44s */
    public void bruteforce_domOverWDegSearch() { bruteforce( "domoverwdget", (intvars) -> Search.domOverWDegSearch(intvars)); }

    @Ignore
    @Test /* 8m41s */
    public void bruteforce_minDomLBSearch() { bruteforce( "mondomlbs", (intvars) -> Search.minDomLBSearch(intvars)); }

    @Ignore
    @Test /* 10m @ 47/67 */
    public void bruteforce_activity() { bruteforce( "activity", (intvars) -> Search.activityBasedSearch(intvars)); }

    @Ignore
    @Test /* 10m @ 66/67 */
    public void bruteforce_inputOrderLBS() { bruteforce( "inputorderlbs", (intvars) -> Search.inputOrderLBSearch(intvars)); }

    @Ignore
    @Test /* 10m @ 39 */
    public void bruteforce_intvarsearch() { bruteforce( "intvarsearch", (intvars) -> Search.intVarSearch(intvars)); }

    @Ignore
    @Test /* 10m @ 28/67 */
    public void bruteforce_minDomUBSearch() { bruteforce("mindomubs",  (intvars) -> Search.minDomUBSearch(intvars)); }

    @Ignore
    @Test /* 10m @ 25 */
    public void bruteforce_inputOrderUBS() { bruteforce( "inputorderubs", (intvars) -> Search.inputOrderUBSearch(intvars)); }

    @Test public void casoStrano() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, 1000),
                new Salt(SaltProfiles.TABLE_SALT, 1000),
                new Salt(SaltProfiles.CALCIUM_CHLORIDE, 1000),
                new Salt(SaltProfiles.MAGNESIUM_CHLORIDE, 1000),
                new Salt(SaltProfiles.EPSOM_SALT, 1000),
                new Salt(SaltProfiles.BAKING_SODA, 1000),
                new Salt(SaltProfiles.PICKLING_LIME, 1000)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfiles.SANTANNA_VIN, Integer.MAX_VALUE),
                new Water( WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfiles.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfiles.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfiles.EVA, Integer.MAX_VALUE),
                new Water( WaterProfiles.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VERA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfiles.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANBERARDO, Integer.MAX_VALUE)
        );

        Water test = new WaterProfileParser().parse(this.getClass().getResourceAsStream("/brun.csv")).get(27);

        Water target = new Water(test, 30);
        System.out.println("name:" + target.nome);
        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater, 10L, (intvars) -> Search.domOverWDegSearch(intvars)).map(s -> s.recipe);
        assertThat(solution).isPresent();
    }

    public void bruteforce(String desc, Function<IntVar[], AbstractStrategy<IntVar>> strategy) {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, 100),
                new Salt(SaltProfiles.TABLE_SALT, 100),
                new Salt(SaltProfiles.CALCIUM_CHLORIDE, 100),
                new Salt(SaltProfiles.MAGNESIUM_CHLORIDE, 100),
                new Salt(SaltProfiles.EPSOM_SALT, 100),
                new Salt(SaltProfiles.BAKING_SODA, 100),
                new Salt(SaltProfiles.PICKLING_LIME, 100)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfiles.SANTANNA_VIN, Integer.MAX_VALUE),
                new Water( WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfiles.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfiles.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfiles.EVA, Integer.MAX_VALUE),
                new Water( WaterProfiles.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VERA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfiles.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANBERARDO, Integer.MAX_VALUE)
        );

        long totStart = System.currentTimeMillis();
        int curr = 0;
        List<Water> tests = new WaterProfileParser().parse(this.getClass().getResourceAsStream("/brun.csv")).subList(25, 30);
        tests.add(new Water(new WaterProfile("Inspiegabilmente Lento", 100, 100, 100, 100, 100, 100), 30));
        for (Water xtarget : tests) {
            long thisStart = System.currentTimeMillis();
            Water target = new Water(xtarget, 30);
//            System.out.println("TARGET:" + target);
            Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater, 60L, strategy).map(s -> s.recipe);
            assertThat(solution).isPresent();
//            System.out.println(solution.get().toString());
//            System.out.println(target.toString());
            curr++;

            double thisTime = (System.currentTimeMillis() - thisStart) / 1000.;
            double totTime = (System.currentTimeMillis() - totStart) / 1000.;

            System.out.println("\n***********************************************");
            System.out.println(desc + ": " + curr + "/67" + ", last:" + thisTime + ", tot:" + totTime + " [" + target.nome + "]");
            System.out.println("***********************************************\n");

            if (thisTime > 60) {
                throw new RuntimeException("took too long fail @"+curr);
            }

            if (totTime > 60 * 10) {
                throw new RuntimeException("fail @"+curr);
            }
        }
        System.out.println("Execution time: " + String.format("%.03f", (System.currentTimeMillis() - totStart) / 1000.) + "s");
    }

    @Test public void mistero() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, 100),
                new Salt(SaltProfiles.TABLE_SALT, 100),
                new Salt(SaltProfiles.CALCIUM_CHLORIDE, 100),
                new Salt(SaltProfiles.MAGNESIUM_CHLORIDE, 100),
                new Salt(SaltProfiles.EPSOM_SALT, 100),
                new Salt(SaltProfiles.BAKING_SODA, 100),
                new Salt(SaltProfiles.PICKLING_LIME, 100)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfiles.SANTANNA_VIN, Integer.MAX_VALUE),
                new Water( WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfiles.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfiles.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfiles.EVA, Integer.MAX_VALUE),
                new Water( WaterProfiles.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VERA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfiles.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANBERARDO, Integer.MAX_VALUE)
        );

        Water target = new Water(new WaterProfile("Inspiegabilmente Lento", 100, 100, 100, 100, 100, 100), 10);
//        Water target = new Water(new WaterProfile("Burton", 270, 41, 113, 720, 85, 270), 10);

        System.out.println("TARGET:" + target);
        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater, 40L).map(s -> s.recipe);
        assertThat(solution).isPresent();
        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }

    @Test public void all_vars() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, Integer.MAX_VALUE),
                new Salt(SaltProfiles.TABLE_SALT, Integer.MAX_VALUE),
                new Salt(SaltProfiles.CALCIUM_CHLORIDE, Integer.MAX_VALUE),
                new Salt(SaltProfiles.MAGNESIUM_CHLORIDE, Integer.MAX_VALUE),
                new Salt(SaltProfiles.EPSOM_SALT, Integer.MAX_VALUE),
                new Salt(SaltProfiles.BAKING_SODA, Integer.MAX_VALUE),
                new Salt(SaltProfiles.PICKLING_LIME, Integer.MAX_VALUE)
        );

        List<Water> myWater = Arrays.asList(
                new Water( WaterProfiles.SANTANNA_VIN, Integer.MAX_VALUE),
                new Water( WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfiles.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfiles.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfiles.EVA, Integer.MAX_VALUE),
                new Water( WaterProfiles.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VERA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfiles.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANBERARDO, Integer.MAX_VALUE),
                new Water( WaterProfiles.DISTILLED, Integer.MAX_VALUE)
        );

        Water target = new Water(WaterProfiles.YELLOW_DRY, 1000);
        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(solution).isPresent();

        System.out.println(solution.get().toString());
        System.out.println(target.toString());
    }


    @Test public void happy() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(WaterProfiles.DISTILLED, Integer.MAX_VALUE));
        Water target = new Water(WaterProfiles.DISTILLED, 10);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(solution).isPresent();
        assertThat(solution.get().waters).containsExactly(new Water(WaterProfiles.DISTILLED, 10));
    }

    @Test public void not_enough_water() {
        List<Salt> mySalts = Arrays.asList();
        List<Water> myWater = Arrays.asList(new Water(WaterProfiles.DISTILLED, 9));
        Water target = new Water(WaterProfiles.DISTILLED, 10);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(solution).isNotPresent();
    }

    @Test public void easy_50_50() {
        List<Salt> mySalts = Arrays.asList();

        WaterProfile pa = new WaterProfile("a", 10, 10, 10, 10, 10, 10);
        Water a = new Water(pa, 100);

        WaterProfile pb = new WaterProfile("b", 0, 0, 0, 0, 0, 0);
        Water b = new Water(pb, 100);

        WaterProfile pc = new WaterProfile("c", 100, 100, 100, 100, 100, 100);
        Water c = new Water(pb, 100);

        List<Water> myWater = Arrays.asList( a, b, c );

        Water target = new Water(new WaterProfile("target", 5, 5, 5, 5, 5, 5), 30);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(solution).isPresent();
        assertThat(solution.get().waters).containsOnly(new Water(pa, 15), new Water(pb, 15));
        assertThat(solution.get().salts).isEmpty();
    }

    @Test public void salt() {
        SaltProfile magicSalt = new SaltProfile("magicSalt", 5, 5, 5, 5, 5, 5);
        SaltProfile badSalt = new SaltProfile("magicSalt", 301, 301, 301, 301, 301, 301);
        List<Salt> mySalts = Arrays.asList(new Salt(magicSalt, 1000 * 10), new Salt(badSalt, 1000 * 10));
        List<Water> myWater = Arrays.asList(new Water(WaterProfiles.DISTILLED, Integer.MAX_VALUE));

        Water target = new Water(new WaterProfile("target", 15, 15, 15, 15, 15, 15), 100);

        Optional<Recipe> solution = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(solution).isPresent();
        assertThat(solution.get().waters).containsOnly(new Water(WaterProfiles.DISTILLED, 100));
        assertThat(solution.get().salts).containsOnly(new Salt(magicSalt, 300));
    }

    @Test public void antani() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, 100),
                new Salt(SaltProfiles.TABLE_SALT, 1000),
                new Salt(SaltProfiles.BAKING_SODA, 1000));

        List<Water> myWater = Arrays.asList(
                new Water(WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water(WaterProfiles.SANTANNA_VIN, 1),
                new Water(WaterProfiles.SANBERARDO, 1));

        Water target = new Water(WaterProfiles.YELLOW_DRY, 20);

        Optional<Recipe> recipe = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        assertThat(recipe.isPresent()).isTrue();
        System.out.println(recipe.toString());
        System.out.println(target.toString());
    }

    @Test public void yellow() {
        List<Salt> mySalts = Arrays.asList(
                new Salt(SaltProfiles.GYPSUM, 100),
                new Salt(SaltProfiles.TABLE_SALT, 1000));

        List<Water> myWater = Arrays.asList(
//                new Water( WaterProfiles.SANTANNA_VIN, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANTANNA_REB, Integer.MAX_VALUE),
                new Water( WaterProfiles.MILANO, Integer.MAX_VALUE),
                new Water( WaterProfiles.BOARIO, Integer.MAX_VALUE),
                new Water( WaterProfiles.LEVISSIMA, Integer.MAX_VALUE),
                new Water( WaterProfiles.EVA, Integer.MAX_VALUE),
                new Water( WaterProfiles.NORDA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VERA, Integer.MAX_VALUE),
                new Water( WaterProfiles.VITASNELLA, Integer.MAX_VALUE),
                new Water( WaterProfiles.DOLOMITI, Integer.MAX_VALUE),
                new Water( WaterProfiles.SANBERARDO, Integer.MAX_VALUE));

        Water target = new Water(WaterProfiles.YELLOW_DRY, 17);

        Optional<Recipe> recipe = new ChocoSolver().solve(target, mySalts, myWater).map(s -> s.recipe);
        System.out.println(recipe.get());
    }

    @Test public void testRecipe() {
        Recipe recipe = new Recipe(Arrays.asList(new Water(WaterProfiles.SANTANNA_VIN, 10)), Arrays.asList(), 0);
        System.out.println(recipe);
    }

    @Test public void range() {
        assertThat(ChocoSolver.range(10000, 10)).hasSize(11);
    }
}
