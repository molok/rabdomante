package alebolo.rabdomante;

public class SolverTarget {
    private final Water water;

    public SolverTarget(Water xtarget) {
        this.water = xtarget;
    }

    public double liters() { return (double) water.liters(); }
    public Water water() { return water; }
    public double calcioMgPerL() { return toInt(water.recipe().calcioMgPerL()); }
    public double magnesioMgPerL() { return toInt(water.recipe().magnesioMgPerL()); }
    public double sodioMgPerL() { return toInt(water.recipe().sodioMgPerL()); }
    public double bicarbonatiMgPerL() { return toInt(water.recipe().bicarbonatiMgPerL()); }
    public double cloruroMgPerL() { return toInt(water.recipe().cloruroMgPerL()); }
    public double solfatoMgPerL() { return toInt(water.recipe().solfatoMgPerL()); }

    private double toInt(double v) { return (double) v; }
}
