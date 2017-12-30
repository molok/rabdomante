package alebolo.rabdomante;

public class SolverTarget implements IMgPerL {
    private final Water water;
    private final int k;

    public SolverTarget(Water xtarget, int i) {
        this.water = xtarget;
        this.k = i;
    }

    public int liters() { return (int) water.liters(); }
    public Water water() { return water; }
    public int calcioMgPerL() { return toInt(water.recipe().calcioMgPerL()); }
    public int magnesioMgPerL() { return toInt(water.recipe().magnesioMgPerL()); }
    public int sodioMgPerL() {
        System.out.println("sodioperMgLDouble:" + water.recipe().sodioMgPerL());
        return toInt(water.recipe().sodioMgPerL());
    }
    public int bicarbonatiMgPerL() { return toInt(water.recipe().bicarbonatiMgPerL()); }
    public int cloruroMgPerL() { return toInt(water.recipe().cloruroMgPerL()); }
    public int solfatoMgPerL() { return toInt(water.recipe().solfatoMgPerL()); }

    private int toInt(double v) { return (int) (v * k); }

    @Override
    public String toString() {
        return "SolverTarget{" +
                "water=" + water +
                ", k=" + k +
                '}';
    }
}
