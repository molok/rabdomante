package alebolo.rabdomante;

import com.google.common.collect.Lists;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Finder {
    public static List<Water> combineWaters(double targetLiters, List<Water> ws1, List<Water> ws2) {
        /* per adesso supportiamo solo il mixing tra due acque */
        return Lists.cartesianProduct(ws1, ws2).stream()
                .distinct()
                .flatMap(xs -> combineWaters(targetLiters, xs.get(0), xs.get(1)).stream())
                .distinct()
                .collect(Collectors.toList());
    }
    public static List<Water> combineWaters(double targetLiters, Water a, Water b) {
        List<Water> mix = new ArrayList<>();
        int increment = Math.max(1, (int) targetLiters / 10); /* andiamo a scaglioni di 10% per gestire i volumi */
        for (int ia = increment; ia < a.liters() && ia < targetLiters; ia += increment) {
            int needed = (int) targetLiters - ia;
            if (b.liters() >= needed) {
                mix.add(WaterMixer.merge(
                        new Water(ia, a.recipe()),
                        new Water(needed, b.recipe())));
            }
        }
        return mix;
    }

    public static List<Water> top(int n, Water target, List<Water> availableWaters, List<MineralAddition> availableSalts) {
        List<Water> combWaters = combineWaters(target.liters(), availableWaters, availableWaters);
        System.out.println("waters:"+combWaters.size());

        List<Water> salted = combWaters.parallelStream().flatMap(w -> saltsCombinations(w, availableSalts, target).stream()).collect(Collectors.toList());
        System.out.println("saltcombination:"+ salted.size());

        return salted.stream()
                .distinct()
                .map(c -> new Pair<>(c, DistanceCalculator.distanceCoefficient(target, c)))
                .sorted(Comparator.comparingDouble(Pair::getValue1))
                .map(pair -> pair.getValue0())
                .distinct()
                .limit(n)
                .collect(Collectors.toList());
    }

    public static Water closest(Water target, List<Water> waters, List<MineralAddition> salts) {
        return top(1, target, waters, salts).get(0);
    }

    public static Water closest(Water target, List<Water> waters) {
        return closest(target, waters, new ArrayList<>());
    }

    public static <T> List<List<T>> permutate(List<T> num, int index){
        List<List<T>> result = new ArrayList<>();
        if(index == num.size() - 1){
            List<T> list = new ArrayList<>();
            list.add(num.get(index));
            result.add(list);
            return result;
        }else{
            List<List<T>> partial = permutate(num, index + 1);
            for(List<T> list: partial){
                for(int i = 0; i <= list.size(); i++){
                    List<T> tmp = new ArrayList<>(list);
                    tmp.add(i, num.get(index));
                    result.add(tmp);
                }
            }
            return result;
        }
    }

    public static List<Water> saltsCombinations(Water w, List<MineralAddition> xsalts, Water target) {
        /* usiamo algoritmo greedy sul singolo mineral ma proviamo tutte le permutazioni dell'ordine */
        List<Water> res = new ArrayList<>();
        res.add(w);
        for(List<MineralAddition> salts : permutate(xsalts, 0)) {
            for (MineralAddition s : salts) {
                res.addAll(saltAddition(res, s, target));
            }
        }
        return res;
    }

    private static List<Water> saltAddition(List<Water> ws, MineralAddition mineralAddition, Water target) {
        double step = 0.01 * target.liters();
        Water currentBest = null;
        for (Water w : ws) {
            for (double mineraAdditionGrams = mineralAddition.grams();
                 mineraAdditionGrams >= 0.;
                 mineraAdditionGrams -= step)
            {
                if (sensato(mineraAdditionGrams, w, mineralAddition.profile(), 100, target.recipe())) {
                    Water e = new Water(
                            w.liters(),
                            new Recipe(w.recipe().profilesRatio(),
                                    salts(mineralAddition, w, mineraAdditionGrams)));

                    /* usiamo logica greedy che sembra sufficientemente efficace per ragioni prestazionali */
                    if (better(target, currentBest, e)) { currentBest = e; }
                }
            }
        }
        return currentBest == null ? new ArrayList<>() : Arrays.asList(currentBest);
    }

    private static boolean better(Water target, Water currentBest, Water candidate) {
        return DistanceCalculator.distanceCoefficient(target, candidate)
            < (currentBest == null
                ? Double.MAX_VALUE
                : DistanceCalculator.distanceCoefficient(target, currentBest));
    }

    private static List<MineralRatio> salts(MineralAddition s, Water wx, double totGrams) {
        List<MineralRatio> salts = new ArrayList<>();
        salts.addAll(wx.recipe().saltsRatio());
        salts.add(additionToRatio(totGrams, s.profile(), wx));
        return salts;
    }

    public static boolean sensato(double grams, Water candidate, MineralProfile saltProf, int thresholdMgPerL, Recipe targetRecipe) {
        final double G_TO_MG_PER_L = 1000 * grams / candidate.liters();

        double deltaCloruro = abs(targetRecipe.cloruroMgPerL() - (candidate.recipe().cloruroMgPerL() + (saltProf.cloruroRatio()* G_TO_MG_PER_L)));
        double deltaSodio = abs(targetRecipe.sodioMgPerL() - (candidate.recipe().sodioMgPerL() + ((saltProf.sodioRatio() * G_TO_MG_PER_L))));
        double deltaCalcio = abs(targetRecipe.calcioMgPerL() - (candidate.recipe().calcioMgPerL() + ((saltProf.calcioRatio() * G_TO_MG_PER_L))));
        double deltaSolfato = abs(targetRecipe.solfatoMgPerL() - (candidate.recipe().solfatoMgPerL() + ((saltProf.solfatoRatio() * G_TO_MG_PER_L))));
        double deltaMagnesium = abs(targetRecipe.magnesioMgPerL() - (candidate.recipe().magnesioMgPerL() + ((saltProf.magnesiumRatio() * G_TO_MG_PER_L))));
        double deltaBicarbonate = abs(targetRecipe.bicarbonatiMgPerL() - (candidate.recipe().bicarbonatiMgPerL() + ((saltProf.bicarbonateRatio() * G_TO_MG_PER_L))));

        return ( !alteraProfilo(saltProf.cloruroRatio()) || deltaCloruro < thresholdMgPerL)
            && ( !alteraProfilo(saltProf.sodioRatio()) || deltaSodio < thresholdMgPerL)
            && ( !alteraProfilo(saltProf.calcioRatio()) || deltaCalcio < thresholdMgPerL)
            && ( !alteraProfilo(saltProf.solfatoRatio()) || deltaSolfato < thresholdMgPerL)
            && ( !alteraProfilo(saltProf.magnesiumRatio()) || deltaMagnesium < thresholdMgPerL)
            && ( !alteraProfilo(saltProf.bicarbonateRatio()) || deltaBicarbonate < thresholdMgPerL);
    }

    /* se il sale non altera il profilo non lo prendo in considerazione */
    private static boolean alteraProfilo(double v) {
        return v != 0.;
    }

    private static MineralRatio additionToRatio(double grams, MineralProfile mineralProfile, Water wx) {
        return new MineralRatio(mineralProfile, 1000 * (grams / wx.liters()));
    }
}
