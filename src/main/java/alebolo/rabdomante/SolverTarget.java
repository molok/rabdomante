package alebolo.rabdomante;

public class SolverTarget implements IMgPerL {
    private final Water water;

    public SolverTarget(Water xtarget) {
        this.water = xtarget;
    }

    public int liters() { return (int) water.liters(); }
    public Water water() { return water; }
    public int calcioMgPerL() { return toInt(water.recipe().calcioMgPerL()); }
    public int magnesioMgPerL() { return toInt(water.recipe().magnesioMgPerL()); }
    public int sodioMgPerL() { return toInt(water.recipe().sodioMgPerL()); }
    public int bicarbonatiMgPerL() { return toInt(water.recipe().bicarbonatiMgPerL()); }
    public int cloruroMgPerL() { return toInt(water.recipe().cloruroMgPerL()); }
    public int solfatoMgPerL() { return toInt(water.recipe().solfatoMgPerL()); }

    private int toInt(double v) { return (int) (100 * v); }
}
