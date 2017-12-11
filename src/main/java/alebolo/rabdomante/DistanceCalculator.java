package alebolo.rabdomante;

import java.util.function.Function;

public class DistanceCalculator {
    public static double distanceCoefficient(Water2 target, Water2 candidate) {
        if (target.liters() != candidate.liters()) {
            throw new Defect(String.format(
                    "bisogna confrontare a parità di litri, trovato %f e %f",
                    target.liters(), candidate.liters()));
        }

        return diff(target, candidate, Water2::calcioMg) +
               diff(target, candidate, Water2::magnesioMg) +
               diff(target, candidate, Water2::sodioMg) +
               diff(target, candidate, Water2::bicarbonatiMg) +
               diff(target, candidate, Water2::solfatoMg) +
               diff(target, candidate, Water2::cloruroMg);
    }

    private static double diff(Water2 a, Water2 b, Function<Water2, Double> getter) {
        return Math.abs(getter.apply(a) - getter.apply(b));
    }
}
