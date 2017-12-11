package alebolo.rabdomante;

import com.google.common.math.DoubleMath;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Water2Test {
    @Test public void no_salts_one_profile() {
        List<SaltRatio> noSalts = Arrays.asList();
        ProfileRatio profileRatio = new ProfileRatio(TestUtils.evaProfile, 1.);
        List<ProfileRatio> profRatio = Arrays.asList(profileRatio);

        Water2 w = new Water2( 10, new Recipe(profRatio, noSalts));

        System.out.println(w.toString());
    }

    @Test public void easy_merge() {
        ProfileRatio profileRatio = new ProfileRatio(TestUtils.evaProfile, 1.);
        Water2 w1 = new Water2( 10, new Recipe(Arrays.asList(profileRatio), Arrays.asList()));
        Water2 w2 = new Water2( 5, new Recipe(Arrays.asList(profileRatio), Arrays.asList()));

        Water2 merge = WaterMerger.merge(w1, w2);
        assertThat(merge.recipe().profilesRatio()).containsExactly(profileRatio);
        assertThat(merge.liters()).isEqualTo(w1.liters() + w2.liters());
    }

    @Test public void merge_correct_ratio() {
        Water2 eva = new Water2( 10, new Recipe(Arrays.asList(new ProfileRatio(TestUtils.evaProfile, 1.)), Arrays.asList()));
        Water2 sanbernardo = new Water2(
                10,
                new Recipe(
                        Arrays.asList(
                                new ProfileRatio(TestUtils.sanBernarndo, 0.5),
                                new ProfileRatio(TestUtils.evaProfile, 0.5)),
                        Arrays.asList()));

        Water2 merge = WaterMerger.merge(eva, sanbernardo);

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

    @Test public void merge_with_salts() {
        Water2 eva_e_sale = new Water2( 10,
                new Recipe(Arrays.asList(new ProfileRatio(TestUtils.evaProfile, 1.)),
                           Arrays.asList(new SaltRatio(TestUtils.tableSalt, 0.1))));

        Water2 eva_e_molto_sale = new Water2( 10,
                new Recipe(Arrays.asList(new ProfileRatio(TestUtils.evaProfile, 1.)),
                        Arrays.asList(new SaltRatio(TestUtils.tableSalt, 0.9))));

        Water2 merged = WaterMerger.merge(eva_e_sale, eva_e_molto_sale);

        assertThat(merged.recipe().saltsRatio().get(0).gramsPerL()).isEqualTo(0.9 * (10/20.) + 0.1 * (10/20.));
    }

}

