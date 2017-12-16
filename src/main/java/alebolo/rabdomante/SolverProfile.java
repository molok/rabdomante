package alebolo.rabdomante;

public class SolverProfile {
    private final Profile profile;

    public SolverProfile(Profile p) {
        this.profile = p;
    }

    public int calcioMgPerL() { return (int) (profile.calcioMgPerL() * 100); }
    public int magnesioMgPerL() { return (int) (profile.magnesioMgPerL() * 100); }
    public int sodioMgPerL() { return (int) (profile.sodioMgPerL() * 100); }
    public int bicarbonatiMgPerL() { return (int) (profile.bicarbonatiMgPerL() * 100); }
    public int solfatoMgPerL() { return (int) (profile.solfatoMgPerL() * 100); }
    public int cloruroMgPerL() { return (int) (profile.cloruroMgPerL() * 100); }
}
