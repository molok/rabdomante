package alebolo.rabdomante;

import java.util.Objects;

class MineralRatio {
    private final double mgPerL;
    private final MineralProfile salt;
    private final int hashCode;

    public MineralRatio(MineralProfile salt, double mgPerL) {
        this.mgPerL = mgPerL;
        this.salt = salt;
        this.hashCode = Objects.hash(mgPerL, salt);
    }

    public double sodioMgPerL() { return mgPerL * salt.sodioRatio(); }
    public double calcioMgPerL() { return mgPerL * salt.calcioRatio(); }
    public double solfatoMgPerL() { return mgPerL * salt.solfatoRatio(); }
    public double cloruroMgPerL() { return mgPerL * salt.cloruroRatio(); }
    public double magnesioMgPerL() { return mgPerL * salt.magnesiumRatio(); }
    public double bicarbonatiMgPerL() { return mgPerL * salt.bicarbonateRatio(); }
    public double mgPerL() { return mgPerL; }
    public MineralProfile profile() { return salt; }

    public String toString() {
        return String.format("%.2f mg/L %s", mgPerL, salt.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MineralRatio mineralRatio = (MineralRatio) o;
        return Double.compare(mineralRatio.mgPerL, mgPerL) == 0 &&
                Objects.equals(salt, mineralRatio.salt);
    }

    @Override public int hashCode() { return hashCode; }
}
