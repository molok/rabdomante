package alebolo.rabdomante;

public class MineralProfileSolver {
    private final MineralProfile profile;

    public MineralProfileSolver(MineralProfile profile) { this.profile = profile; }
    public double calcioRatio() { return toInt(profile.calcioRatio()); }
    public double magnesioRatio() { return toInt(profile.magnesiumRatio()); }
    public double sodioRatio() { return toInt(profile.sodioRatio()); }
    public double bicarbonatiRatio() { return toInt(profile.bicarbonateRatio()); }
    public double solfatoRatio() { return toInt(profile.solfatoRatio()); }
    public double cloruroRatio() { return toInt(profile.cloruroRatio()); }
    private double toInt(double v) { return v; }
}
