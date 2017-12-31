package alebolo.rabdomante;

public class MineralProfileSolver implements IMineralRatio {
    private final MineralProfile profile;
    private final int k;

    public MineralProfileSolver(MineralProfile profile, int i) { this.profile = profile; this.k = i;}
    public int calcioRatio() { return toInt(profile.calcioRatio()); }
    public int magnesioRatio() { return toInt(profile.magnesiumRatio()); }
    public int sodioRatio() { return toInt(profile.sodioRatio()); }
    public int bicarbonatiRatio() { return toInt(profile.bicarbonateRatio()); }
    public int solfatoRatio() { return toInt(profile.solfatoRatio()); }
    public int cloruroRatio() { return toInt(profile.cloruroRatio()); }
    private int toInt(double v) { return (int) (v * k); }
    public String name() { return profile.name(); }
}
