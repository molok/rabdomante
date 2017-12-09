package alebolo.rabdomante;

import org.assertj.core.data.Offset;
import org.javatuples.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFinder {
    Logger log = LoggerFactory.getLogger(this.getClass());

    final WaterProfile sanBernarndo = new WaterProfile(9.5, 0.6, 0.6, 30.2, 2.3, 0.7, "sanbernardo");
    final WaterProfile eva = new WaterProfile(10.2 , 4, 0.28, 48, 1.7, 0.18, "eva");
    final WaterProfile target = new WaterProfile(50, 10, 33, 142, 57, 44, "target");
    final WaterProfile distilled = new WaterProfile(0, 0, 0, 0, 0, 0, "distilled");
    final SaltProfile gypsum = new SaltProfile(0, 0, 23.28, 55.8, "gypsum");
    final SaltProfile tableSalt = new SaltProfile(39.34, 60.66, 0, 0, "tableSalt");

    @Test public void salting() {
        SaltProfile prof = new SaltProfile(80, 20, 0, 0, "prof");
        SaltAddition add = new SaltAddition(1, prof);
        assertThat(add.sodioMg()).isEqualTo(800);
        assertThat(add.cloruroMg()).isEqualTo(200);
    }

    @Test public void testostring() {
        Water w = new Water(1, sanBernarndo);
        System.out.println(w.toString());
        w = Modifier.add(w, new SaltAddition(1, gypsum));
        System.out.println(w.toString());
        w = Modifier.add(w, new SaltAddition(1, gypsum));
        System.out.println(w.toString());
    }

    @Test public void compare() {
        assertThat(Finder.diffCoeff(new Water(1, target), new Water(1, target))).isEqualTo(0);
        assertThat(Finder.diffCoeff(new Water(1, sanBernarndo), new Water(1, sanBernarndo))).isEqualTo(0);
        assertThat(Finder.diffCoeff(new Water(1, target), new Water(1, sanBernarndo))).isGreaterThan(0);
    }

    @Test public void add() {
        assertThat(new Water(1, distilled)
                        .add(new Water(1, distilled)))
                .isEqualTo(new Water(2, distilled));

        assertThat(new Water(1, sanBernarndo).add(new Water(0, distilled)))
                .isEqualTo(new Water(1, sanBernarndo));
    }

    @Test public void findSame() {
        assertThat(Finder.closest(new Water(1, target),
                                  Arrays.asList(
                                            new Water(1, sanBernarndo),
                                            new Water(1, eva),
                                            new Water(1, target) )))
                .isEqualTo(new Water(1, target));
    }

    @Test public void findClosest() {
        Water target = new Water.Builder(1).calcio(100).solfato(200).build();
        Water closest = new Water.Builder(1).calcio(99).solfato(199).build();
        Water nocigar = new Water.Builder(1).calcio(90).solfato(190).build();
        Water verydifferent = new Water.Builder(1).calcio(199).solfato(299).build();

        assertThat(Finder.closest(target, Arrays.asList(closest, nocigar, verydifferent)))
                   .isEqualTo(closest);
    }

    @Test public void additions() {
        assertThat(
                Modifier.add(new Water(1, distilled),
                        new SaltAddition(0.5, gypsum), new SaltAddition(0.5, gypsum))
        ).isEqualTo(Modifier.add(new Water(1, distilled),
                new SaltAddition(1, gypsum)));
    }

    @Test public void findClosestReal() {
       Water levissima = new Water(10, 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
       Water boario = new Water(10, 131, 40, 5, 303, 240, 4, "boario");
       Water eva = new Water(10, 10.2, 4, 0.28, 48, 1.7, 0.17, "eva");
       Water santanna = new Water(10, 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
       Water norda = new Water(10, 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
       Water vera = new Water(10, 35, 12.6, 2, 148, 19.2, 2.6, "vera");
       Water vitasnella = new Water(10, 86, 26, 3, 301, 83, 2, "vitasnella");
       Water sanbern = new Water(10, sanBernarndo);
       Water dolomiti = new Water(10, 23.8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");

        Water blackMediumTarget = new Water(10, 50, 10, 33, 142, 57, 44, "black medium");
        long startTime = System.currentTimeMillis();
        // 5978
        List<Water> xxx = Finder.top2(100, blackMediumTarget
                , Arrays.asList(levissima,
                        boario,
                        eva, santanna, norda,
                        vera,
                        vitasnella, sanbern, dolomiti),
                Arrays.asList(new SaltAddition(3, gypsum), new SaltAddition(3, tableSalt)));

        System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms");

        log.warn("res:\n" + xxx.stream()
                        .map(w -> "delta: " + String.format("%.2f", Finder.diffCoeff(blackMediumTarget, w))
                                + "  = " + w.toString())
                .collect(Collectors.joining("\n")));
    }

    @Test public void stampa() {
        System.out.println(String.format("%.2f mg", 10.12345678));
    }

    @Test public void findClosestWithSalts() {
        log.warn("res");
        Water target = Modifier.add(new Water(1, distilled)
                , new SaltAddition(1.5, gypsum)
                , new SaltAddition(1, tableSalt) );

        Water res = Finder.closest(
                target,
                Arrays.asList(new Water(1, distilled)),
                Arrays.asList(new SaltAddition(100, gypsum), new SaltAddition(100, tableSalt)));

        log.warn("res:"+res);
        assertThat(res).isEqualTo(target);
    }

    @Test public void waterContent() {
        WaterProfile profile = new WaterProfile(10, 0, 0, 0, 0, 0, "profile");
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
                new Water(1, 232.8, 0, 393.4, 0, 558, 606.6, "test"));
    }

    @Test public void profile() {
        assertThat(new Water(1, sanBernarndo).profile()).isEqualTo(sanBernarndo);
        assertThat(new Water(2.5, sanBernarndo).profile()).isEqualTo(sanBernarndo);
    }
}
