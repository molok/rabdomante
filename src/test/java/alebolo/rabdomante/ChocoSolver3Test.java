package alebolo.rabdomante;

import org.javatuples.Pair;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static alebolo.rabdomante.Profile.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ChocoSolver3Test {
    ChocoSolver solver = new ChocoSolver();

    public static double liters = 10;

    public static final Water BLACK_MEDIUM_TARGET = new Water(liters,
            Recipe.create(
                    new Profile(50, 10, 33, 142, 57, 44, "black medium")));

    public static final List<Water> WATERS = Arrays.asList(
            new Water(liters, Recipe.create(levissima)),
            new Water(liters, Recipe.create(boario))
//            new Water(liters, Recipe.create(eva)),
//            new Water(liters, Recipe.create(santanna)),
//            new Water(liters, Recipe.create(norda)),
//            new Water(liters, Recipe.create(vera)),
//            new Water(liters, Recipe.create(vitasnella)),
//            new Water(liters, Recipe.create(sanbern))
//            new Water(liters, Recipe.create(dolomiti))
    );

    public static final List<MineralAddition> MINERAL_ADDITIONS = Arrays.asList(
            new MineralAddition(1, MineralProfile.CALCIUM_CHLORIDE),
            new MineralAddition(1, MineralProfile.BAKING_SODA),
            new MineralAddition(1, MineralProfile.GYPSUM),
            new MineralAddition(1, MineralProfile.TABLE_SALT));

    @Test public void costAndStuff() {
        Water target = BLACK_MEDIUM_TARGET;
        List<Water> w = WATERS;
        Pair<Integer, Water> res = solver.solve2(target, w, MINERAL_ADDITIONS).get();
        /* candidate:6,00L dolomiti, 14,00L levissima 1,40g calcium chloride (70,00 mg/L), 2,00g baking soda (100,00 mg/L), 1,40g gypsum (70,00 mg/L)
        { calcioMgPerL:58,67 mg, magnesioMgPerL:3,80 mg, sodioMgPerL:29,09 mg, bicarbonatiMgPerL:140,98 mg, solfatoMgPerL:57,56 mg, cloruroMgPerL:45,05 mg} */
        System.out.println("res:"+res.getValue1());
        assertThat(res.getValue0()).isLessThanOrEqualTo(50);
    }

    @Test public void costWithSalts() {
        Water target = new Water(20, Recipe.create(new Profile(0, 0, 10, 0, 0, 0, "test")));
        Water cand = new Water(20, Recipe.create(Profile.distilled));
        Pair<Integer, Water> res = solver.solve2(target, Arrays.asList(cand), Arrays.asList(new MineralAddition(111., MineralProfile.FAKE_TABLE_SALT))).get();
        assertThat(res.getValue1().recipe().saltsRatio().get(0)).isEqualTo(new MineralRatio(MineralProfile.FAKE_TABLE_SALT, 10.));
        assertThat(res.getValue0()).isEqualTo(0);
        assertThat(res.getValue1().isSameAs(target)).isTrue();
    }

    @Test(expected = RuntimeException.class) public void nonBaseWater() {
        Water target = new Water(20, Recipe.create(Profile.distilled));
        Water cand = new Water
                (20,
                Recipe.create(
                        new Profile(0, 0, 0, 0, 0, 0, "distil"),
                        Arrays.asList(new MineralRatio(MineralProfile.FAKE_TABLE_SALT, 1.))));
        Pair<Integer, Water> __ = solver.solve2(target, Arrays.asList(cand), Arrays.asList()).get();
    }

    @Test public void costNoSalts() {
        Water target = new Water(2, Recipe.create(Profile.distilled));
        Water cand = new Water(2, Recipe.create(new Profile(0, 0, 10, 0, 0, 0, "test")));
        Pair<Integer, Water> res = solver.solve2(target, Arrays.asList(cand), Arrays.asList()).get();
//        System.out.println("result:"+cand);
        assertThat(res.getValue1().isSameAs(cand)).isTrue();
        assertThat(res.getValue0()).isEqualTo(10);
    }

    @Test public void costNoSalts2() {
        Water target = new Water(30, Recipe.create(Profile.distilled));
        Water cand = new Water(30, Recipe.create(new Profile(0, 0, 20, 0, 0, 0, "test")));
        Pair<Integer, Water> res = solver.solve2(target, Arrays.asList(cand), Arrays.asList()).get();
        assertThat(res.getValue1().isSameAs(cand)).isTrue();
        assertThat(res.getValue0()).isEqualTo(20);
    }

    @Test public void costNoSaltsSodioAndCalcio() {
        Water target = new Water(30, Recipe.create(Profile.distilled));
        Water cand = new Water(30, Recipe.create(new Profile(30, 0, 10, 0, 0, 0, "test")));
        Pair<Integer, Water> res = solver.solve2(target, Arrays.asList(cand), Arrays.asList()).get();
        assertThat(res.getValue1().isSameAs(cand)).isTrue();
        assertThat(res.getValue0()).isEqualTo(40);
    }

    @Test public void cost0() {
        Water target = new Water(20, Recipe.create(Profile.distilled));
        Water distilled = new Water(20, Recipe.create(Profile.distilled));
        Pair<Integer, Water> res = solver.solve2(target, Arrays.asList(distilled), Arrays.asList()).get();
        assertThat(res.getValue1().isSameAs(distilled)).isTrue();
        assertThat(res.getValue0()).isEqualTo(0);
    }

//    @Test public void cost0() {
//        Water target = new Water(20, Recipe.create(Profile.distilled));
//        Water distilled = new Water(20, Recipe.create(Profile.distilled));
//        Water res = solver.solve(target, Arrays.asList(distilled), Arrays.asList()).get();
//        assertThat(res.isSameAs(distilled)).isTrue();
//    }

}