package alebolo.rabdomante;

import com.google.common.collect.Lists;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Finder2 {
    public static List<Water2> combineWaters(double targetLiters, List<Water2> ws1, List<Water2> ws2) {
        /* per adesso supportiamo solo il mixing tra due acque */
        return Lists.cartesianProduct(ws1, ws2).stream()
                .distinct()
                .flatMap(xs -> combineWaters(targetLiters, xs.get(0), xs.get(1)).stream())
                .distinct()
                .collect(Collectors.toList());
    }
    public static List<Water2> combineWaters(double targetLiters, Water2 a, Water2 b) {
        List<Water2> mix = new ArrayList<>();
        for (int ia = 0; ia < a.liters() && ia < targetLiters; ia++) {
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

        return combWaters.parallelStream()
                .flatMap(w -> saltsCombinations(w, salts).stream())
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

    public static List<Water2> saltsCombinations(Water2 w, List<SaltAddition> salts) {
        /* TODO valutare target per inserire sali da provare */
        List<Water2> res = new ArrayList<>();
        res.add(w);
        for (SaltAddition s : salts) {
            res.addAll(saltAddition(res, s));
        }
        return res;
    }

    public static List<Water2> saltAddition(List<Water2> res, SaltAddition s) {
        List<Water2> added = new ArrayList<>();
        for (Water2 wx : res) {
            double grams = s.grams();
            while (grams >= 0.) {
                List<SaltRatio> salts = new ArrayList<>();
                salts.addAll(wx.recipe().saltsRatio());
                salts.add(additionToRatio(grams, s.profile(), wx));
                added.add( new Water2(wx.liters(), new Recipe(wx.recipe().profilesRatio(), salts)));
                grams -= 0.1; /* step di dedimo di grammo per ora */
            }
        }
        return added;
    }

    private static SaltRatio additionToRatio(double grams, SaltProfile saltProfile, Water2 wx) {
        return new SaltRatio(saltProfile, 1000 * (grams / wx.liters()));
    }
}
