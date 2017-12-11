package alebolo.rabdomante;

import com.google.common.collect.*;
import org.assertj.core.data.Offset;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFinder {
    Logger log = LoggerFactory.getLogger(this.getClass());

    final Profile sanBernarndo = new Profile(9.5, 0.6, 0.6, 30.2, 2.3, 0.7, "sanbernardo");
    final Profile eva = new Profile(10.2 , 4, 0.28, 48, 1.7, 0.18, "evaProfile");
    final Profile target = new Profile(50, 10, 33, 142, 57, 44, "target");
    final Profile distilled = new Profile(0, 0, 0, 0, 0, 0, "distilled");
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
                        .add(new Water(1, distilled)).isSameAs(
                new Water(2, distilled))).isTrue();

        assertThat(new Water(1, sanBernarndo).add(new Water(0, distilled))
                .isSameAs(new Water(1, sanBernarndo))).isTrue();
    }

    @Test public void findSame() {
        assertThat(Finder.closest(new Water(1, target),
                                  Arrays.asList(
                                            new Water(1, sanBernarndo),
                                            new Water(1, eva),
                                            new Water(1, target) ))
                .isSameAs(new Water(1, target))).isTrue();
    }

    @Test public void findClosest() {
        Water target = new Water.Builder(1).name("target").calcio(100).solfato(200).build();
        Water closest = new Water.Builder(1).name("ok").calcio(99).solfato(199).build();
        Water nocigar = new Water.Builder(1).name("nocigar").calcio(90).solfato(190).build();
        Water verydifferent = new Water.Builder(1).name("diff").calcio(199).solfato(299).build();

        assertThat(Finder.closest(target, Arrays.asList(closest, nocigar, verydifferent)).isSameAs(closest)).isTrue();
    }

    @Test public void additions() {
        assertThat(
                Modifier.add(new Water(1, distilled),
                        new SaltAddition(0.5, gypsum), new SaltAddition(0.5, gypsum)).isSameAs(
        (Modifier.add(new Water(1, distilled),
                new SaltAddition(1, gypsum))))).isTrue();
    }

    @Ignore
    @Test public void allCombs() {
        Water levissima = new Water(16, 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
//        Water boario = new Water(16, 131, 40, 5, 303, 240, 4, "boario");
        Water eva = new Water(16, 10.2, 4, 0.28, 48, 1.7, 0.17, "evaProfile");
//        Water santanna = new Water(16, 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
//        Water norda = new Water(16, 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
        Water vera = new Water(16, 35, 12.6, 2, 148, 19.2, 2.6, "vera");
//        Water vitasnella = new Water(16, 86, 26, 3, 301, 83, 2, "vitasnella");
//        Water sanbern = new Water(16, sanBernarndo);
        Water dolomiti = new Water(16, 23.8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");

        Water blackMediumTarget = new Water(16, 50, 10, 33, 142, 57, 44, "black medium");

        Finder.allCombinations(blackMediumTarget.liters(), Arrays.asList(levissima,
//                        boario,
                        eva,
//                        santanna, norda,
                        vera,
//                        vitasnella, sanbern,
                        dolomiti
                ));
    }

    @Test public void findClosestReal() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Water levissima = new Water(10, 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
       Water boario = new Water(10, 131, 40, 5, 303, 240, 4, "boario");
       Water eva = new Water(10, 10.2, 4, 0.28, 48, 1.7, 0.17, "evaProfile");
       Water santanna = new Water(10, 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
       Water norda = new Water(10, 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
       Water vera = new Water(10, 35, 12.6, 2, 148, 19.2, 2.6, "vera");
       Water vitasnella = new Water(10, 86, 26, 3, 301, 83, 2, "vitasnella");
       Water sanbern = new Water(10, sanBernarndo);
       Water dolomiti = new Water(10, 23.8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");

        Water blackMediumTarget = new Water(10, 50, 10, 33, 142, 57, 44, "black medium");
        long startTime = System.currentTimeMillis();
        List<Water> xxx = Finder.top(100, blackMediumTarget
                , Arrays.asList(levissima,
                        boario,
                        eva,
                        santanna, norda,
                        vera,
                        vitasnella, sanbern,
                        dolomiti
                ),
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
                Arrays.asList(new SaltAddition(10, gypsum), new SaltAddition(10, tableSalt)));

        log.warn("res:"+res);
        assertThat(res.isSameAs(target)).isTrue();
    }

    @Test public void waterContent() {
        Profile profile = new Profile(10, 0, 0, 0, 0, 0, "profile");
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
        assertThat(doubleCorr.isSameAs(
                new Water(1, 232.8, 0, 393.4, 0, 558, 606.6, "test")))
                .isTrue();
    }

    @Test public void profile() {
        assertThat(new Water(1, sanBernarndo).profile()).isEqualTo(sanBernarndo);
        assertThat(new Water(2.5, sanBernarndo).profile()).isEqualTo(sanBernarndo);
    }

    @Test public void combinazioni() {
        Water a = new Water.Builder(2).name("a").build();
        Water b = new Water.Builder(2).name("b").build();
        Water c = new Water.Builder(2).name("c").build();

        Set<Multiset<Water>> res = Finder.allCombinations(2, Arrays.asList(a, b, c));

        for (Collection<Water> lw : res)  { if (lw.size() > 0) {System.out.println("RES:"+ printList(lw));} }
    }
    private String printList(Collection<Water> lw) {
        return lw.stream().map(e -> e.name()).collect(Collectors.joining(", "));
    }

//    @Test public void combinations() {
//        Water a = new Water.Builder(10).name("a").build();
//        Water b = new Water.Builder(10).name("b").build();
//        Water target = new Water.Builder(10).name("b").build();
//        List<Water> mix = Finder.combineWaters(target.liters(), a, b);
//
//        mix.stream().forEach(m -> System.out.println("mix:"+m.toString()));
//    }

//    @Test public void testequals() {
//        Water a0 = new Water.Builder(0).name("a").build();
//        Water a1 = new Water.Builder(1).name("a").build();
//        Water a2 = new Water.Builder(1).name("a").build();
//        WaterMix ma = new WaterMix(Arrays.asList(a0, a1));
//        WaterMix mb = new WaterMix(Arrays.asList(a2));
//        assertThat(ma).isEqualByComparingTo(mb);
//        System.out.println("ma:"+ma.toString());
//    }

    @Test public void mixing() {
        Water a0 = new Water.Builder(0).name("a").build();
        Water a1 = new Water.Builder(1).name("a").build();
    }

//    @Test public void combinationOfTwo() {
//        Water target = new Water.Builder(20).name("target").magnesio(100).calcio(100).sodio(100).build();
//        List<Water> ws = Arrays.asList(
//                new Water.Builder(20).name("a").build(),
//                new Water.Builder(20).name("b").build(),
//                new Water.Builder(20).name("c").build()
//        );
//        List<Water> combinedWaters = Finder.combineWaters(target.liters(), ws, ws);
//
//        System.out.println("combined:"+combinedWaters.size());
//        combinedWaters.stream().forEach(w -> System.out.println(w.toString()));
//    }

    @Test public void tostr1() {
        Water a = new Water.Builder(5).name("a").build();
        System.out.println(a.toString());
    }

//    @Test public void tostr2() {
//        Water a = new Water.Builder(5).name("a").build();
//        Water b = new Water.Builder(5).name("b").build();
//        Water anb = a.add(b);
//        System.out.println(anb.toString());
//    }

//    @Test public void xmixing() {
//        Water water = new Water.Builder(5).name("a").build()
//                    .add(     new Water.Builder(5).name("b").build()
//                         .add(new Water.Builder(1).name("a").build().add(new Water.Builder(5).name("c").build())));
//        Water sb = new Water.Builder(1).name("san benedetto").build();
//        Water add1 = sb.add(sb);
//        System.out.println(add1.composition());
//        Water add = sb.add(add1);
//        System.out.println(add.composition());
//    }

//    @Test public void aggComp() {
//        Water a = new Water.Builder(5).name("a").build();
//        Water b = new Water.Builder(5).name("b").build();
//        Water c = new Water.Builder(5).name("c").build();
//        Water d = new Water.Builder(5).name("c").build();
//        System.out.println(a.add(b).add(c).add(d).mixRatio(1., new HashMap<>()));
//    }

    @Test public void tostrfff() {
        Water a = new Water.Builder(2).name("a").build();
        System.out.println(a.toString());
        System.out.println(a.add(a).toString());
    }
}
