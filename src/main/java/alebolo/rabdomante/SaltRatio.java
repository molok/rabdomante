package alebolo.rabdomante;

import java.util.Objects;

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
        return String.format("%.2f mg/L %s", mgPerL, salt.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltRatio saltRatio = (SaltRatio) o;
        return Double.compare(saltRatio.mgPerL, mgPerL) == 0 &&
                Objects.equals(salt, saltRatio.salt);
    }

    @Override public int hashCode() { return Objects.hash(mgPerL, salt); }
}
