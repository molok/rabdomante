package alebolo.rabdomante;

import java.util.function.Function;

public class DistanceCalculator {
    public static double distanceCoefficient(Water target, Water candidate) {
        if (target.liters() != candidate.liters()) {
            throw new Defect(String.format(
                    "bisogna confrontare a parità di litri, trovato %f e %f",
                    target.liters(), candidate.liters()));
        }

        return diff(target.recipe(), candidate.recipe(), Recipe::calcioMgPerL) +
               diff(target.recipe(), candidate.recipe(), Recipe::magnesioMgPerL) +
               diff(target.recipe(), candidate.recipe(), Recipe::sodioMgPerL) +
               diff(target.recipe(), candidate.recipe(), Recipe::bicarbonatiMgPerL) +
               diff(target.recipe(), candidate.recipe(), Recipe::solfatoMgPerL) +
               diff(target.recipe(), candidate.recipe(), Recipe::cloruroMgPerL);
    }

    private static double diff(Recipe a, Recipe b, Function<Recipe, Double> getter) {
        return Math.abs(getter.apply(a) - getter.apply(b));
    }
}
