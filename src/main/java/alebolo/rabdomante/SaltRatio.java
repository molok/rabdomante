package alebolo.rabdomante;

class SaltRatio {
    private final double gramsPerL;
    private final SaltProfile salt;

    public SaltRatio(double gramsPerL, SaltProfile salt) {
        this.gramsPerL = gramsPerL;
        this.salt = salt;
    }

    public double sodioMgPerL() { return gramsPerL * salt.sodioPerc() * 10 ; }
    public double calcioMgPerL() { return gramsPerL * salt.calcioPerc() * 10 ; }
    public double solfatoMgPerL() { return gramsPerL * salt.solfatoPerc() * 10 ; }
    public double cloruroMgPerL() { return gramsPerL * salt.cloruroPerc() * 10 ; }
    public double magnesioMgPerL() { return 0; }
    public double bicarbonatiMgPerL() { return 0; }
    public double gramsPerL() { return gramsPerL; }
    public SaltProfile profile() { return salt; }

    public String toString() {
        return String.format("%.2f g/L %s", gramsPerL, salt.toString());
    }
}
