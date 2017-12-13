package alebolo.rabdomante;

import com.google.common.collect.Lists;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Finder2 {
    public static List<Water2> combineWaters(double targetLiters, List<Water2> ws1, List<Water2> ws2) {
        /* per adesso supportiamo solo il mixing tra due acque */
        return Lists.cartesianProduct(ws1, ws2).parallelStream()
                .distinct()
                .flatMap(xs -> combineWaters(targetLiters, xs.get(0), xs.get(1)).stream())
                .distinct()
                .collect(Collectors.toList());
    }
    public static List<Water2> combineWaters(double targetLiters, Water2 a, Water2 b) {
        List<Water2> mix = new ArrayList<>();
        int increment = (int) targetLiters / 10; /* andiamo a scaglioni di 10% per gestire i volumi */
        for (int ia = 0; ia < a.liters() && ia < targetLiters; ia+=increment) {
            int needed = (int) targetLiters - ia;
            if (b.liters() >= needed) {
                mix.add(WaterMerger.merge(
                        new Water2(ia, a.recipe()),
                        new Water2(needed, b.recipe())));
            }
        }
        return mix;
    }

    public static List<Water2> top(int n, Water2 target, List<Water2> waters, List<SaltAddition> salts) {
        List<Water2> combWaters = combineWaters(target.liters(), waters, waters);
        System.out.println("waters:"+combWaters.size());
        List<Water2> water2Stream = combWaters.stream().flatMap(w -> saltsCombinations(w, salts, target).stream()).collect(Collectors.toList());
        System.out.println("saltcombination:"+ water2Stream.size());

        return water2Stream.stream()
                .distinct()
                .map(c -> new Pair<>(c, DistanceCalculator.distanceCoefficient(target, c)))
                .sorted(Comparator.comparingDouble(Pair::getValue1))
                .map(pair -> pair.getValue0())
                .distinct()
                .limit(n)
                .collect(Collectors.toList());
    }

    public static Water2 closest(Water2 target, List<Water2> waters, List<SaltAddition> salts) {
        return top(1, target, waters, salts).get(0);
    }

    public static Water2 closest(Water2 target, List<Water2> waters) {
        return closest(target, waters, new ArrayList<>());
    }

    public static List<Water2> saltsCombinations(Water2 w, List<SaltAddition> salts, Water2 target) {
        List<Water2> res = new ArrayList<>();
        res.add(w);
        for (SaltAddition s : salts) {
            res.addAll(saltAddition(res, s, target));
        }
        return res;
    }

    public static List<Water2> saltAddition(List<Water2> ws, SaltAddition saltAddition, Water2 target) {
        List<Water2> saltedWater = new ArrayList<>();
        for (Water2 w : ws) {
            for (double saltAdditionGrams = saltAddition.grams(); saltAdditionGrams >= 0 ; saltAdditionGrams -= 0.1) {
//                System.out.println("provo con g" + saltAdditionGrams);
                if (sensato(w, saltAdditionGrams, saltAddition.profile(), target, 100)) {
                    saltedWater.add(
                            new Water2( w.liters(),
                                        new Recipe(w.recipe().profilesRatio(),
                                                   salts(saltAddition, w, saltAdditionGrams))));
                }
            }
        }
//        System.out.println("saltedwater: "+saltedWater.size());
        return saltedWater;
    }

    private static List<SaltRatio> salts(SaltAddition s, Water2 wx, double totGrams) {
        List<SaltRatio> salts = new ArrayList<>();
        salts.addAll(wx.recipe().saltsRatio());
        salts.add(additionToRatio(totGrams, s.profile(), wx));
        return salts;
    }

    public static boolean sensato(Water2 candidate, double grams, SaltProfile saltProf, Water2 target, int maxDiffThreshold) {
        double k = 1000 * grams / candidate.liters();
        double deltaCloruro = abs(target.recipe().cloruroMgPerL() - (candidate.recipe().cloruroMgPerL() + (saltProf.cloruroRatio() * k)));
        double deltaSodio = abs(target.recipe().sodioMgPerL() - (candidate.recipe().sodioMgPerL() + ((saltProf.sodioRatio() * k))));
        double deltaCalcio = abs(target.recipe().calcioMgPerL() - (candidate.recipe().calcioMgPerL() + ((saltProf.calcioRatio() * k))));
        double deltaSolfato = abs(target.recipe().solfatoMgPerL() - (candidate.recipe().solfatoMgPerL() + ((saltProf.solfatoRatio() * k))));

        return ( saltProf.cloruroRatio() == 0. || deltaCloruro < maxDiffThreshold)
            && ( saltProf.sodioRatio() == 0. || deltaSodio < maxDiffThreshold)
            && ( saltProf.calcioRatio() == 0. || deltaCalcio < maxDiffThreshold)
            && ( saltProf.solfatoRatio() == 0. || deltaSolfato < maxDiffThreshold);
    }

    private static SaltRatio additionToRatio(double grams, SaltProfile saltProfile, Water2 wx) {
        return new SaltRatio(saltProfile, 1000 * (grams / wx.liters()));
    }
}
