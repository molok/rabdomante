package alebolo.rabdomante;

public class Modifier {
    public static Water add(Water water, SaltAddition saltAddition) {
        return new Water(water.liters(),
                water.calcioMg() + saltAddition.calcioMg(),
                water.magnesioMg() + saltAddition.magnesioMg(),
                water.sodioMg() + saltAddition.sodioMg(),
                water.bicarbonatiMg() + saltAddition.bicarbonatiMg(),
                water.solfatoMg() + saltAddition.solfatoMg(),
                water.cloruroMg() + saltAddition.cloruroMg());
    }
}
