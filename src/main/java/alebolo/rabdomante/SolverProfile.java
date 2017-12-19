package alebolo.rabdomante;

public class SolverProfile implements IMgPerL {
    private final Profile profile;
    private final int k;

    public SolverProfile(Profile p, int i) {
        this.profile = p;
        this.k = i;
    }

    public int calcioMgPerL() { return getAnInt(profile.calcioMgPerL()); }
    public int magnesioMgPerL() { return getAnInt(profile.magnesioMgPerL()); }
    public int sodioMgPerL() { return getAnInt(profile.sodioMgPerL()); }
    public int bicarbonatiMgPerL() { return getAnInt(profile.bicarbonatiMgPerL()); }
    public int solfatoMgPerL() { return getAnInt(profile.solfatoMgPerL()); }
    public int cloruroMgPerL() { return getAnInt(profile.cloruroMgPerL()); }

    private int getAnInt(double v) {
        return (int) (v * k);
    }
}
