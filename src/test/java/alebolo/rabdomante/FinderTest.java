package alebolo.rabdomante;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.withinPercentage;

public class FinderTest {
    Water sb = new Water(10., Recipe.create(TestUtils.sanBernarndo));
    Water sb2 = new Water(10., Recipe.create(TestUtils.sanBernarndo));
    Water distilled = new Water(10., Recipe.create(TestUtils.distilled));
    Logger log = LoggerFactory.getLogger(this.getClass());

    Profile levissima = new Profile( 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
    Profile boario = new Profile( 131, 40, 5, 303, 240, 4, "boario");
    Profile eva = new Profile( 10.2, 4, 0.28, 48, 1.7, 0.17, "evaProfile");
    Profile santanna = new Profile( 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
    Profile norda = new Profile( 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
    Profile vera = new Profile( 35, 12.6, 2, 148, 19.2, 2.6, "vera");
    Profile vitasnella = new Profile( 86, 26, 3, 301, 83, 2, "vitasnella");
    Profile dolomiti = new Profile(8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");
    Profile sanbern = new Profile(9.5, 0.6, 0.6, 30.2, 2.3, 0.7, "sanbernardo");

    @Test public void closest() {
        Water res = Finder.closest(sb, Arrays.asList(sb2, distilled));
        assertThat(res).isEqualTo(sb);
    }

    @Test public void top() {
//        List<MineralAddition> salts = Arrays.asList(new MineralAddition(1, TestUtils.tableSalt));
        List<Water> waters = Arrays.asList(sb2, distilled);
        List<Water> res = Finder.top(10, sb, waters, Arrays.asList());
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
        /* calcioMgPerL:0.47, magnesioMgPerL:0.11, sodioMgPerL:0.31, bicarbonatiMgPerL:1.55, solfatoMgPerL:0.43, cloruroMgPerL:0.44 */
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
        Water res = Finder.closest(
                target,
                Arrays.asList(new Water(10, Recipe.create(TestUtils.distilled))),
                Arrays.asList(new MineralAddition(10, MineralProfile.TABLE_SALT)));

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


    @Test public void findClosestReal() {
        int liters = 100;
        Water blackMediumTarget =
                new Water(liters,
                        Recipe.create(
                                new Profile(50, 10, 33, 142, 57, 44, "black medium")));

        long startTime = System.currentTimeMillis();

        List<Water> waters = Arrays.asList(
                new Water(liters, Recipe.create(levissima)),
                new Water(liters, Recipe.create(boario)),
                new Water(liters, Recipe.create(eva)),
                new Water(liters, Recipe.create(santanna)),
                new Water(liters, Recipe.create(norda)),
                new Water(liters, Recipe.create(vera)),
                new Water(liters, Recipe.create(vitasnella)),
                new Water(liters, Recipe.create(sanbern)),
                new Water(liters, Recipe.create(dolomiti)),
                new Water(liters,
                        new Recipe(Arrays.asList(new ProfileRatio(vera, 1.0)),
                                Arrays.asList(
                                        new MineralRatio(MineralProfile.TABLE_SALT, 70),
                                        new MineralRatio(MineralProfile.GYPSUM, 70))))
        );
        List<Water> xxx = Finder.top(1000, blackMediumTarget
                , waters,
                Arrays.asList(
                        new MineralAddition(1000, MineralProfile.GYPSUM),
                        new MineralAddition(1000, MineralProfile.TABLE_SALT),
                        new MineralAddition(1000, MineralProfile.BAKING_SODA),
                        new MineralAddition(1000, MineralProfile.CALCIUM_CHLORIDE)
                ));

        System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms");

        log.warn("\n"+blackMediumTarget.toString());

        waters.stream().forEach(w -> {
            log.warn("w: " + w.toString());
            log.warn("delta w: " + DistanceCalculator.distanceCoefficient(blackMediumTarget, w)); });


        log.warn("res:\n" + xxx.stream()
                .map(w -> "xdelta " +
                        String.format("%.2f", DistanceCalculator.distanceCoefficient(blackMediumTarget, w))
                        + " = " + w.description())
                .collect(Collectors.joining("\n")));
    }

    @Test public void profToString() {
        Profile p = TestUtils.target;
        System.out.println(p.toString());

        System.out.println(Recipe.create(p).toString());
    }

    @Test public void sensato() {
        Water target = new Water(10., Recipe.create(new Profile(50, 10, 33, 142, 57, 44, "black medium")));
        Water vera = new Water(10, Recipe.create(this.vera));
        Water ok = new Water(10.,
                new Recipe(Arrays.asList(new ProfileRatio(this.vera, 1.)),
                        Arrays.asList(new MineralRatio(MineralProfile.GYPSUM, 0.7))));
        assertThat(Finder.sensato(0.7, vera, MineralProfile.TABLE_SALT, 100, target.recipe())).isTrue();

        Water closematch = new Water(10.,
                new Recipe(Arrays.asList(new ProfileRatio(this.vera, 1.)),
                        Arrays.asList(new MineralRatio(MineralProfile.GYPSUM, 70),
                                      new MineralRatio(MineralProfile.TABLE_SALT, 70))));

        System.out.println(closematch.sodioMg()+" vs "+target.sodioMg());

        System.out.println(DistanceCalculator.distanceCoefficient(target, closematch));
        System.out.println(DistanceCalculator.distanceCoefficient(target, ok));
        System.out.println(DistanceCalculator.distanceCoefficient(target, vera));
        System.out.println(DistanceCalculator.distanceCoefficient(target, target));
    }
}