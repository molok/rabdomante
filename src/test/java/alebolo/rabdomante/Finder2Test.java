package alebolo.rabdomante;

import org.assertj.core.internal.DeepDifference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.withinPercentage;

public class Finder2Test {
    Water2 sb = new Water2(10., Recipe.create(TestUtils.sanBernarndo));
    Water2 sb2 = new Water2(10., Recipe.create(TestUtils.sanBernarndo));
    Water2 distilled = new Water2(10., Recipe.create(TestUtils.distilled));
    Logger log = LoggerFactory.getLogger(this.getClass());

    Profile levissima = new Profile( 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
    Profile boario = new Profile( 131, 40, 5, 303, 240, 4, "boario");
    Profile eva = new Profile( 10.2, 4, 0.28, 48, 1.7, 0.17, "evaProfile");
    Profile santanna = new Profile( 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
    Profile norda = new Profile( 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
    Profile vera = new Profile( 35, 12.6, 2, 148, 19.2, 2.6, "vera");
    Profile vitasnella = new Profile( 86, 26, 3, 301, 83, 2, "vitasnella");
    Profile dolomiti = new Profile(8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");
    Profile sanbern = TestUtils.sanBernarndo;

    @Test public void closest() {
        Water2 res = Finder2.closest(sb, Arrays.asList(sb2, distilled));
        assertThat(res).isEqualTo(sb);
    }

    @Test public void top() {
//        List<SaltAddition> salts = Arrays.asList(new SaltAddition(1, TestUtils.tableSalt));
        List<Water2> waters = Arrays.asList(sb2, distilled);
        List<Water2> res = Finder2.top(10, sb, waters, Arrays.asList());
        assertThat(res.get(0).isSameAs(sb)).isTrue();
    }

    @Test public void sameWaterDeltaZero() {
        double res = DistanceCalculator.distanceCoefficient(
                new Water2(10,
                        Recipe.create(
                                new Profile(0, 0, 0, 0, 0, 0, "target"))),
                new Water2(10, Recipe.create(
                        new Profile(0, 0, 0, 0, 0, 0, "target2"))));

        assertThat(res).isCloseTo(0., withinPercentage(1));
    }

    @Test public void smallDiff() {
        double res = DistanceCalculator.distanceCoefficient(
                new Water2(10,
                        Recipe.create(
                                new Profile(0, 0, 0, 0, 0, 0, "target"))),
                new Water2(10, Recipe.create(
                        new Profile(30, 0, 0, 0, 0, 0, "target2"))));

        assertThat(res).isCloseTo(30., withinPercentage(1));
    }

    @Test public void delta() {
        System.out.println("delta:"+
            DistanceCalculator.distanceCoefficient(
                new Water2(10,
                        Recipe.create(
                                new Profile(50, 10, 33, 142, 57, 44, "black medium"))),
                    new Water2(10, Recipe.create(eva))));
    }

    @Test public void easyFind() {
        Water2 target = new Water2(10,
                        Recipe.create(
                                new Profile(0, 0, 39, 0, 0, 60, "target")));
        Water2 res = Finder2.closest(
                target,
                Arrays.asList(new Water2(10, Recipe.create(TestUtils.distilled))),
                Arrays.asList(new SaltAddition(10, TestUtils.tableSalt)));

        System.out.println("delta:"+ DistanceCalculator.distanceCoefficient(target, res));
        System.out.println("trg:" + target.toString());
        System.out.println("res:" + res.toString());
    }

    @Test public void saltComb() {
        List<Water2> res = Finder2.saltAddition(
                Arrays.asList(new Water2(10, Recipe.create(TestUtils.distilled))),
                new SaltAddition(10, TestUtils.tableSalt));

        res.stream().forEach(x -> System.out.println(x.toString()));
    }

    @Test public void distinct() {
        Profile foo = new Profile(1, 1, 1, 1, 1, 1, "foo");
        Profile bar = new Profile(1, 1, 1, 1, 1, 1, "foo");
        SaltRatio saltRatioA = new SaltRatio(TestUtils.tableSalt, 1.);
        SaltRatio saltRatioB = new SaltRatio(TestUtils.tableSalt, 1.);
        List<Water2> list = Arrays.asList(
                new Water2(10, new Recipe(Arrays.asList(new ProfileRatio(foo, 1.)), Arrays.asList(saltRatioA))),
                new Water2(10, new Recipe(Arrays.asList(new ProfileRatio(bar, 1.)), Arrays.asList(saltRatioB)))
        );

        assertThat(list.stream().distinct().count()).isEqualTo(1);
    }


    @Test public void findClosestReal() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Water2 blackMediumTarget =
                new Water2(10,
                        Recipe.create(
                                new Profile(50, 10, 33, 142, 57, 44, "black medium")));

        long startTime = System.currentTimeMillis();

        List<Water2> xxx = Finder2.top(10, blackMediumTarget
                , Arrays.asList(
                        new Water2(10, Recipe.create(levissima)),
                        new Water2(10, Recipe.create(boario)),
                        new Water2(10, Recipe.create(eva)),
                        new Water2(10, Recipe.create(santanna)),
                        new Water2(10, Recipe.create(norda)),
                        new Water2(10, Recipe.create(vera)),
                        new Water2(10, Recipe.create(vitasnella)),
                        new Water2(10, Recipe.create(sanbern)),
                        new Water2(10, Recipe.create(dolomiti))
                ),
                Arrays.asList(
                        new SaltAddition(1, TestUtils.gypsum),
                        new SaltAddition(1, TestUtils.tableSalt)
                ));

        System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms");

        log.warn("\n"+blackMediumTarget.toString());
        log.warn("res:\n" + xxx.stream()
                .map(w -> "delta: " + String.format("%.2f", DistanceCalculator.distanceCoefficient(blackMediumTarget, w))
                        + "  = " + w.toString())
                .collect(Collectors.joining("\n")));
    }
}