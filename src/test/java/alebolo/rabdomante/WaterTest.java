package alebolo.rabdomante;

import com.google.common.math.DoubleMath;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

public class WaterTest {
    @Test public void no_salts_one_profile() {
        List<MineralRatio> noSalts = Arrays.asList();
        ProfileRatio profileRatio = new ProfileRatio(TestUtils.evaProfile, 1.);
        List<ProfileRatio> profRatio = Arrays.asList(profileRatio);

        Water w = new Water( 10, new Recipe(profRatio, noSalts));

        System.out.println(w.toString());
    }

    @Test public void mah() {
        WaterMixer mixer = new WaterMixer();

        Profile levissima = new Profile( 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
        Profile boario = new Profile( 131, 40, 5, 303, 240, 4, "boario");
        Water wlevissima = new Water(5, Recipe.create(levissima));
        Water wboario = new Water(5, Recipe.create(boario));
        Water res = mixer.merge(wlevissima, wboario);
        System.out.println(res.description());
    }

    @Test public void easy_merge() {
        ProfileRatio profileRatio = new ProfileRatio(TestUtils.evaProfile, 1.);
        Water w1 = new Water( 10, new Recipe(Arrays.asList(profileRatio), Arrays.asList()));
        Water w2 = new Water( 5, new Recipe(Arrays.asList(profileRatio), Arrays.asList()));

        Water merge = WaterMixer.merge(w1, w2);
        assertThat(merge.recipe().profilesRatio()).containsExactly(profileRatio);
        assertThat(merge.liters()).isEqualTo(w1.liters() + w2.liters());
    }

    @Test public void merge_correct_ratio() {
        Water eva = new Water( 10, new Recipe(Arrays.asList(new ProfileRatio(TestUtils.evaProfile, 1.)), Arrays.asList()));
        Water sanbernardo = new Water(
                10,
                new Recipe(
                        Arrays.asList(
                                new ProfileRatio(TestUtils.sanBernarndo, 0.5),
                                new ProfileRatio(TestUtils.evaProfile, 0.5)),
                        Arrays.asList()));

        Water merge = WaterMixer.merge(eva, sanbernardo);

        assertThat(merge.recipe().profilesRatio().size()).isEqualTo(2);

        ProfileRatio mergeEva = merge.recipe().profilesRatio().stream()
                                    .filter(p -> p.profile().name().equals(TestUtils.evaProfile.name()))
                                    .findFirst()
                                    .get();

        ProfileRatio mergeSanb = merge.recipe().profilesRatio().stream()
                .filter(p -> p.profile().name().equals(TestUtils.sanBernarndo.name()))
                .findFirst()
                .get();

        assertThat(DoubleMath.fuzzyCompare(mergeEva.ratio(), 15/20., 0.1)).isEqualTo(0);
        assertThat(DoubleMath.fuzzyCompare(mergeSanb.ratio(), 5/20., 0.1)).isEqualTo(0);
    }

    @Test public void multi_layer_merge() {
        int saltMgPerL = 100;
        Water a = new Water(10,
                                Recipe.create(TestUtils.evaProfile,
                                              Arrays.asList(new MineralRatio(MineralProfile.TABLE_SALT, saltMgPerL))));

        assertThat(a.sodioMg()).isCloseTo(MineralProfile.TABLE_SALT.sodioRatio() * saltMgPerL * a.liters(), withinPercentage(1));

        Water b = new Water(10, Recipe.create(TestUtils.evaProfile));

        Water merge_ab = WaterMixer.merge(a, b);

        assertThat(merge_ab.sodioMg()).isCloseTo(MineralProfile.TABLE_SALT.sodioRatio() * saltMgPerL * a.liters(), withinPercentage(2));

        Water c = new Water(10,
                              Recipe.create(TestUtils.evaProfile,
                                      Arrays.asList(new MineralRatio(MineralProfile.TABLE_SALT, saltMgPerL))));
        Water merge_abc = WaterMixer.merge(merge_ab, c);

        assertThat(merge_abc.liters()).isEqualTo(30.);
        assertThat(merge_abc.recipe()
                            .profilesRatio()
                            .stream().map(r -> r.profile())
                            .collect(Collectors.toList())).containsExactly(TestUtils.evaProfile);

        assertThat(merge_abc.sodioMg()).isCloseTo(MineralProfile.TABLE_SALT.sodioRatio() * saltMgPerL * (c.liters() + a.liters()), withinPercentage(3));
    }
}
