package alebolo.rabdomante;

public class Modifier {
    public static Water add(Water water, SaltAddition... saltAdditions) {
        Water res = new Water(water.liters(), water.profile());
        for (SaltAddition saltAddition : saltAdditions) {
            res = new Water(res.liters(),
                    res.calcioMg() + saltAddition.calcioMg(),
                    res.magnesioMg() + saltAddition.magnesioMg(),
                    res.sodioMg() + saltAddition.sodioMg(),
                    res.bicarbonatiMg() + saltAddition.bicarbonatiMg(),
                    res.solfatoMg() + saltAddition.solfatoMg(),
                    res.cloruroMg() + saltAddition.cloruroMg(), res.name() + ", " +saltAddition);
        }

        return res;
    }
}
