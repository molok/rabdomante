package alebolo.rabdomante;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static alebolo.rabdomante.Profile.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.withinPercentage;

public class FinderTest {
    public static final List<MineralAddition> MINERAL_ADDITIONS = Arrays.asList(
//            new MineralAddition(1000, MineralProfile.CALCIUM_CHLORIDE),
//            new MineralAddition(1000, MineralProfile.BAKING_SODA),
            new MineralAddition(1000, MineralProfile.GYPSUM),
            new MineralAddition(1000, MineralProfile.TABLE_SALT)
//            new MineralAddition(1000, MineralProfile.ESPOM_SALT),
//            new MineralAddition(1000, MineralProfile.MAGNESIUM_CHLORIDE),
//            new MineralAddition(1000, MineralProfile.CHALK),
//            new MineralAddition(1000, MineralProfile.PICKLING_LIME)
    );

    public static final double liters = 10;

    public static final List<Water> WATERS = Arrays.asList(
//            new Water(liters, Recipe.create(levissima)),
//            new Water(liters, Recipe.create(boario)),
//            new Water(liters, Recipe.create(eva)),
//            new Water(liters, Recipe.create(santanna)),
//            new Water(liters, Recipe.create(norda)),
            new Water(liters, Recipe.create(vera))
//            new Water(liters, Recipe.create(vitasnella))
//            new Water(liters, Recipe.create(sanbern))
//            new Water(liters, Recipe.create(dolomiti))
//                new Water(liters,
//                        new Recipe(Arrays.asList(new ProfileRatio(vera, 1.0)),
//                                Arrays.asList(
//                                        new MineralRatio(MineralProfile.TABLE_SALT, 70),
//                                        new MineralRatio(MineralProfile.GYPSUM, 70))))
    );
    private final Finder finder = new Finder();
    Water sb = new Water(10., Recipe.create(TestUtils.sanBernarndo));
    Water sb2 = new Water(10., Recipe.create(TestUtils.sanBernarndo));
    Water distilled = new Water(10., Recipe.create(TestUtils.distilled));
    Logger log = LoggerFactory.getLogger(this.getClass());
    public static final Water BLACK_MEDIUM_TARGET = new Water(liters,
            Recipe.create(
                    new Profile(50, 10, 33, 142, 57, 44, "black medium")));

    @Test public void closest() {
        Water res = finder.closest(sb, Arrays.asList(sb2, distilled)).get();
        assertThat(res).isEqualTo(sb);
    }

    @Ignore @Test public void top() {
//        List<MineralAddition> salts = Arrays.asList(new MineralAddition(1, TestUtils.tableSalt));
        List<Water> waters = Arrays.asList(sb2, distilled);
        List<Water> res = finder.top(10, sb, waters, Arrays.asList());
        assertThat(res.get(0).isSameAs(sb)).isTrue();
    }

    @Test public void sameWaterDeltaZero() {
        double res = DistanceCalculator.distanceCoefficient(
                new Water(10,
                        Recipe.create(
                                new Profile(0, 0, 0, 0, 0, 0, "target"))),
                new Water(10, Recipe.create(
                        new Profile(0, 0, 0, 0, 0, 0, "target2"))));

        assertThat(res).isCloseTo(0., withinPercentage(1));
    }

    @Test public void smallDiff() {
        double res = DistanceCalculator.distanceCoefficient(
                new Water(10,
                        Recipe.create(
                                new Profile(0, 0, 0, 0, 0, 0, "target"))),
                new Water(10, Recipe.create(
                        new Profile(30, 0, 0, 0, 0, 0, "target2"))));

        assertThat(res).isCloseTo(30., withinPercentage(1));
    }

    @Test public void delta() {
        System.out.println("delta:"+
            DistanceCalculator.distanceCoefficient(
                new Water(10,
                        Recipe.create(
                                new Profile(50, 10, 33, 142, 57, 44, "black medium"))),
                    new Water(10, Recipe.create(eva))));
    }

    @Test public void easyFind() {
        Water target = new Water(10,
                        Recipe.create(
                                new Profile(0, 0, 39, 0, 0, 60, "target")));
        Water res = finder.closest(
                target,
                Arrays.asList(new Water(10, Recipe.create(TestUtils.distilled))),
                Arrays.asList(new MineralAddition(10, MineralProfile.TABLE_SALT))).get();

        System.out.println("delta:"+ DistanceCalculator.distanceCoefficient(target, res));
        System.out.println("trg:" + target.toString());
        System.out.println("res:" + res.toString());
    }

    @Test public void distinct() {
        Profile foo = new Profile(1, 1, 1, 1, 1, 1, "foo");
        Profile bar = new Profile(1, 1, 1, 1, 1, 1, "foo");
        MineralRatio mineralRatioA = new MineralRatio(MineralProfile.TABLE_SALT, 1.);
        MineralRatio mineralRatioB = new MineralRatio(MineralProfile.TABLE_SALT, 1.);
        List<Water> list = Arrays.asList(
                new Water(10, new Recipe(Arrays.asList(new ProfileRatio(foo, 1.)), Arrays.asList(mineralRatioA))),
                new Water(10, new Recipe(Arrays.asList(new ProfileRatio(bar, 1.)), Arrays.asList(mineralRatioB)))
        );

        assertThat(list.stream().distinct().count()).isEqualTo(1);
    }

    @Test public void findClosestMine() {
        long startTime = System.currentTimeMillis();
        Water xxx = finder.closest2(BLACK_MEDIUM_TARGET, WATERS, MINERAL_ADDITIONS);

        System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms");

//        log.warn("\n"+ BLACK_MEDIUM_TARGET.toString());

        log.warn("res:"+xxx.description());
        log.warn("distance:"+DistanceCalculator.distanceCoefficient(BLACK_MEDIUM_TARGET, xxx));
        log.warn("\ntarget:"+ BLACK_MEDIUM_TARGET.description());
        log.warn("\ncandidate:"+ xxx.description());
    }

    @Test public void findChocoDist() {
//        Water target = new Water(liters,
//                Recipe.create(
//                        new Profile(0, 0, 0, 0, 0, 0, "distillato più sale")));
        Water target = new Water(liters,
                Recipe.create(
                        new Profile(0, 0, 3.9, 0, 0, 6.0, "distillato più sale")));

        List<Water> waters = Arrays.asList( new Water(liters, Recipe.create(Profile.distilled)));
        List<MineralAddition> minerals = Arrays.asList( new MineralAddition(1000, MineralProfile.TABLE_SALT));
        Water xxx = finder.closest(target, waters, minerals).get();

        log.warn("res:"+xxx.description());
        log.warn("distance:"+DistanceCalculator.distanceCoefficient(target, xxx));
        log.warn("\ntarget:"+ target.description());
        log.warn("\ncandidate:"+ xxx.description());
    }

    @Test public void findMineDist() {
        Water target = new Water(liters,
                Recipe.create(
                        new Profile(0, 0, 3.9, 0, 0, 6.0, "distillato più sale")));

        List<Water> waters = Arrays.asList( new Water(liters, Recipe.create(Profile.distilled)));
        List<MineralAddition> minerals = Arrays.asList( new MineralAddition(1000, MineralProfile.TABLE_SALT));
        Water xxx = finder.closest2(target, waters, minerals);

        log.warn("res:"+xxx.description());
        log.warn("distance:"+DistanceCalculator.distanceCoefficient(target, xxx));
        log.warn("\ntarget:"+ target.description());
        log.warn("\ncandidate:"+ xxx.description());
    }


    @Test public void findClosestChoco() {
        long startTime = System.currentTimeMillis();
        Optional<Water> xxx = finder.closest(BLACK_MEDIUM_TARGET, WATERS, MINERAL_ADDITIONS);

        System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms");

        log.warn("res:"+xxx.get().description());
        log.warn("distance:"+DistanceCalculator.distanceCoefficient(BLACK_MEDIUM_TARGET, xxx.get()));
        log.warn("\ntarget:"+ BLACK_MEDIUM_TARGET.description());
        log.warn("\ncandidate:"+ xxx.get().description());

//        WATERS.stream().forEach(w -> {
//            log.warn("w: " + w.toString());
//            log.warn("delta w: " + DistanceCalculator.distanceCoefficient(blackMediumTarget, w)); });


//        log.warn("res:\n" + xxx.stream()
//                .map(w -> "xdelta " +
//                        String.format("%.2f", DistanceCalculator.distanceCoefficient(blackMediumTarget, w))
//                        + " = " + w.description())
//                .collect(Collectors.joining("\n")));
    }

    @Test public void profToString() {
        Profile p = TestUtils.target;
        System.out.println(p.toString());

        System.out.println(Recipe.create(p).toString());
    }

    @Test public void sensato() {
        Water target = new Water(10., Recipe.create(new Profile(50, 10, 33, 142, 57, 44, "black medium")));
        Water wvera = new Water(10, Recipe.create(vera));
        Water ok = new Water(10.,
                new Recipe(Arrays.asList(new ProfileRatio(vera, 1.)),
                        Arrays.asList(new MineralRatio(MineralProfile.GYPSUM, 0.7))));
        assertThat(finder.sensato(0.7, wvera, MineralProfile.TABLE_SALT, 100, target.recipe())).isTrue();

        Water closematch = new Water(10.,
                new Recipe(Arrays.asList(new ProfileRatio(vera, 1.)),
                        Arrays.asList(new MineralRatio(MineralProfile.GYPSUM, 70),
                                      new MineralRatio(MineralProfile.TABLE_SALT, 70))));

        System.out.println(closematch.sodioMg()+" vs "+target.sodioMg());

        System.out.println(DistanceCalculator.distanceCoefficient(target, closematch));
        System.out.println(DistanceCalculator.distanceCoefficient(target, ok));
        System.out.println(DistanceCalculator.distanceCoefficient(target, wvera));
        System.out.println(DistanceCalculator.distanceCoefficient(target, target));
    }

    @Test public void permutations() {
        List<Integer> x = Arrays.asList(1,2,3, 4, 5, 6, 7, 8);

        System.out.println(finder.permutate(x, 0).size());
    }


}