package alebolo.rabdomante;

public class SolverProfile {
    private final Profile profile;

    public SolverProfile(Profile p) {
        this.profile = p;
    }

    public double calcioMgPerL() { return toInt(profile.calcioMgPerL()); }
    public double magnesioMgPerL() { return toInt(profile.magnesioMgPerL()); }
    public double sodioMgPerL() { return toInt(profile.sodioMgPerL()); }
    public double bicarbonatiMgPerL() { return toInt(profile.bicarbonatiMgPerL()); }
    public double solfatoMgPerL() { return toInt(profile.solfatoMgPerL()); }
    public double cloruroMgPerL() { return toInt(profile.cloruroMgPerL()); }

    private double toInt(double v) { return (double) v; }
}
