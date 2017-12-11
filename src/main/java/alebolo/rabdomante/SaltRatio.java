package alebolo.rabdomante;

class SaltRatio {
    private final double mgPerL;
    private final SaltProfile salt;

    public SaltRatio(SaltProfile salt, double mgPerL) {
        this.mgPerL = mgPerL;
        this.salt = salt;
    }

    public double sodioMgPerL() { return mgPerL * salt.sodioRatio(); }
    public double calcioMgPerL() { return mgPerL * salt.calcioRatio(); }
    public double solfatoMgPerL() { return mgPerL * salt.solfatoRatio(); }
    public double cloruroMgPerL() { return mgPerL * salt.cloruroRatio(); }
    public double magnesioMgPerL() { return 0; }
    public double bicarbonatiMgPerL() { return 0; }
    public double mgPerL() { return mgPerL; }
    public SaltProfile profile() { return salt; }

    public String toString() {
        return String.format("%.2f g/L %s", mgPerL, salt.toString());
    }
}
