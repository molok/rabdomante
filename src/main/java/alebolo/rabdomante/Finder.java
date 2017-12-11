package alebolo.rabdomante;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import org.javatuples.Pair;

import javax.swing.plaf.multi.MultiScrollBarUI;
import java.util.*;
import java.util.stream.Collectors;

public class Finder {
    static double diffCoeff(Water target, Water candidate) {
        return Math.abs(target.calcioMg() - candidate.calcioMg()) +
               Math.abs(target.magnesioMg() - candidate.magnesioMg()) +
               Math.abs(target.sodioMg() - candidate.sodioMg()) +
               Math.abs(target.bicarbonatiMg() - candidate.bicarbonatiMg()) +
               Math.abs(target.solfatoMg() - candidate.solfatoMg()) +
               Math.abs(target.cloruroMg() - candidate.cloruroMg());
    }

    private static List<Water> splitIntoOneLiterElements(Water w) {
        ArrayList<Water> res = new ArrayList<>();
        for (int i = 0; i < w.liters(); i++) {
            res.add(new Water(1, w.profile()));
        }
        return res;
    }

    public static Set<Multiset<Water>> allCombinations(double target, List<Water> waters) {
        List<Water> elements = waters.stream()
                .flatMap(w -> splitIntoOneLiterElements(w).stream())
                .collect(Collectors.toList());

        Set<Multiset<Water>> x = new HashSet<>();
        x.add(HashMultiset.create());

        return combinations(HashMultiset.create(), x, target, elements);
    }


    private static Set<Multiset<Water>> combinations(
            Multiset<Water> curr,
            Set<Multiset<Water>> solutions,
            double target,
            List<Water> elements) {

        if (target == 0) {
            solutions.add(curr);
            return new HashSet<>(HashMultiset.create(solutions));
        } else if (target < 0 || elements.size() == 0) {
            /* non ho trovato niente */
            Set<Multiset<Water>> lists = new HashSet<>();
            lists.add(HashMultiset.create());
            return lists;
        } else {
            Set<Multiset<Water>> res = new HashSet<>();

            res.addAll(combinations(curr, solutions, target, elements.subList(1, elements.size())));

            Multiset<Water> x = HashMultiset.create(curr);
            x.add(elements.get(0));
            res.addAll(combinations(x, solutions, target - 1, elements.subList(1, elements.size())));

            return res;
        }
    }

//    public static List<Water> combineWaters(double targetLiters, List<IWater> ws1, List<IWater> ws2) {
//        /* per adesso supportiamo solo il mixing tra due acque */
//        return Lists.cartesianProduct(ws1, ws2).stream()
//                .distinct()
//                .flatMap(xs -> combineWaters(targetLiters, xs.get(0), xs.get(1)).stream())
//                .distinct()
//                .collect(Collectors.toList());
//    }
//    public static List<Water> combineWaters(double targetLiters, Water a, Water b) {
//        List<Water> mix = new ArrayList<>();
//        for (int ia = 0; ia < a.liters() && ia < targetLiters; ia++) {
//            int needed = (int) targetLiters - ia;
//            if (   b.liters() >= needed
//                    && b.composition.keySet().size() <= needed /* voglio mix di almeno 1 litro */) {
//                Water neededA = new Water(ia, a.profile());
//                Water neededb = new Water(needed, b.profile());
//                mix.add(neededA.add(neededb));
//            }
//        }
//        return mix;
//    }

    public static List<Water> top(int n, Water target, List<Water> waters, List<SaltAddition> salts) {
//        List<Water> combWaters = combineWaters(target.liters(), waters, waters);
        List<Water> combWaters = waters;

        return combWaters.parallelStream()
                .flatMap(w -> saltsCombinations(w, salts).stream())
                .map(c -> new Pair<>(c, diffCoeff(target, c)))
                .sorted(Comparator.comparingDouble(Pair::getValue1))
                .map(pair -> pair.getValue0())
                .limit(n)
                .collect(Collectors.toList());
    }

    public static Water closest(Water target, List<Water> waters, List<SaltAddition> salts) {
        return top(1, target, waters, salts).get(0);
    }

    public static Water closest(Water target, List<Water> waters) {
        return closest(target, waters, new ArrayList<>());
    }

    private static List<Water> saltsCombinations(Water w, List<SaltAddition> salts) {
        List<Water> res = new ArrayList<>();
        res.add(w);
        for (SaltAddition s : salts) {
            res.addAll(saltAddition(res, s));
        }
        return res;
    }

    private static List<Water> saltAddition(List<Water> res, SaltAddition s) {
        List<Water> added = new ArrayList<>();
        for (Water wx : res) {
            double grams = s.grams();
            while (grams >= 0) {
                added.add(Modifier.add(wx, new SaltAddition(grams, s.profile())));
                grams -= 0.01; /* step di dedimo di grammo per ora */
            }
        }
        return added;
    }
}
