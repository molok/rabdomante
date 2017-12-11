package alebolo.rabdomante;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class WaterMixTest {
    @Test public void mixContainsOriginalWaters() {
        Water sanBenedetto = new Water.Builder(10).name("san benedetto").build();
        Water norda = new Water.Builder(5).name("norda").build();
        IWater mix = WaterMix.create(Arrays.asList(sanBenedetto, norda));
        assertThat(mix.sources().stream().map(sr -> sr.profile())).containsExactly(norda.profile(), sanBenedetto.profile());
    }

    @Test public void flattensDuplicates() {
        Water sanBenedetto1 = new Water.Builder(10).name("san benedetto").build();
        Water sanBenedetto2 = new Water.Builder(10).name("san benedetto").build();
        IWater mix = WaterMix.create(Arrays.asList(sanBenedetto1, sanBenedetto2));
        assertThat(mix.sources().stream().map(sr -> sr.profile())).containsExactly(sanBenedetto1.profile());
    }

}