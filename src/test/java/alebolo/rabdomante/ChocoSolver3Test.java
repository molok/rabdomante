package alebolo.rabdomante;

import org.javatuples.Pair;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ChocoSolver3Test {
    ChocoSolver solver = new ChocoSolver();

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