package alebolo.rabdomante;

import org.assertj.core.data.Offset;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFinder {
    final WaterProfile sanBernarndo = new WaterProfile(9.5, 0.6, 0.6, 30.2, 2.3, 0.7);
    final WaterProfile target = new WaterProfile(50, 10, 33, 142, 57, 44);
    final WaterProfile distilled = new WaterProfile(0, 0, 0, 0, 0, 0);
    final SaltProfile gypsum = new SaltProfile(0, 0, 23.28, 55.8);
    final SaltProfile tableSalt = new SaltProfile(39.34, 60.66, 0, 0);

    @Test public void salting() {
        SaltProfile prof = new SaltProfile(80, 20, 0, 0);
        SaltAddition add = new SaltAddition(1, prof);
        assertThat(add.sodioMg()).isEqualTo(800);
        assertThat(add.cloruroMg()).isEqualTo(200);
    }

    @Test public void compare() {
        assertThat(Finder.diffCoeff(target, target)).isEqualTo(0);
        assertThat(Finder.diffCoeff(sanBernarndo, sanBernarndo)).isEqualTo(0);
        assertThat(Finder.diffCoeff(target, sanBernarndo)).isGreaterThan(0);
    }

    @Test public void waterContent() {
        WaterProfile profile = new WaterProfile(10, 0, 0, 0, 0, 0);
        assertThat(new Water(1, profile).calcioMg()).isEqualTo(10);
    }

    @Test public void saltAddition() {
        assertThat(new SaltAddition(2, tableSalt).sodioMg()).isEqualTo(786.8, Offset.offset(0.001));
    }

    @Test public void waterCorrection() {
        Water waterCorrected = Modifier.add(new Water(1, distilled),
                                 new SaltAddition(1.0, gypsum));

        assertThat(waterCorrected.calcioMg()).isEqualTo(232.8);
        assertThat(waterCorrected.solfatoMg()).isEqualTo(558);

        Water doubleCorr = Modifier.add(waterCorrected, new SaltAddition(1.0, tableSalt));
        assertThat(doubleCorr).isEqualTo(
                new Water(1, 232.8, 0, 393.4, 0, 558, 606.6 ));
    }

    @Test public void profile() {
        assertThat(new Water(1, sanBernarndo).profile()).isEqualTo(sanBernarndo);
        assertThat(new Water(2.5, sanBernarndo).profile()).isEqualTo(sanBernarndo);
    }
}
