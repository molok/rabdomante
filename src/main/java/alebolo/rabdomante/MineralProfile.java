package alebolo.rabdomante;

import java.util.Objects;

public class MineralProfile {
    private final double sodio;
    private final double cloruro;
    private final double calcio;
    private final double solfato;
    private final double magnesium;
    private final double bicarbonate;
    private final String name;

    public final static MineralProfile GYPSUM = new MineralProfile.Builder().name("gypsum").calcioRatio(0.2328).solfatoRatio(0.558).build();
    /*
    1g => 0.39g
    1000mg => 390mg
    100mg => 39gm
    * */
    public final static MineralProfile TABLE_SALT = new MineralProfile.Builder().name("tableSalt").sodioRatio(0.3934).cloruroRatio(0.6066).build();
    public final static MineralProfile CALCIUM_CHLORIDE = new MineralProfile.Builder().name("calcium chloride").calcioRatio(0.3611).cloruroRatio(0.6389).build();
    public final static MineralProfile ESPOM_SALT = new Builder().name("epsom salt").magnesiumRatio(0.0986).solfatoRatio(0.3487).build();
    public final static MineralProfile MAGNESIUM_CHLORIDE = new MineralProfile.Builder().name("magnesium chloride").magnesiumRatio(0.1195).cloruroRatio(0.3487).build();
    public final static MineralProfile BAKING_SODA = new Builder().name("baking soda").sodioRatio(0.2737).bicarbonateRatio(0.7263).build();
    public final static MineralProfile CHALK = new Builder().name("chalk").calcioRatio(0.4005).bicarbonateRatio(1.2198).build();
    public final static MineralProfile PICKLING_LIME = new Builder().name("pickling lime").calcioRatio(0.541).bicarbonateRatio(1.6455).build();

    public final static MineralProfile FAKE_TABLE_SALT = new MineralProfile.Builder().name("fakeTableSalt").sodioRatio(1).build();

    private MineralProfile(double sodioRatio, double cloruroRatio, double calcioRatio, double solfatoRatio, String name, double magnesiumRatio, double bicarbonateRatio) {
        this.sodio = sodioRatio;
        this.cloruro = cloruroRatio;
        this.calcio = calcioRatio;
        this.solfato = solfatoRatio;
        this.bicarbonate = bicarbonateRatio;
        this.magnesium = magnesiumRatio;
        this.name = name;
    }

    public double sodioRatio() { return sodio; }
    public double calcioRatio() { return calcio; }
    public double cloruroRatio() { return cloruro;}
    public double solfatoRatio() { return solfato;}
    public double magnesiumRatio() { return magnesium; }
    public double bicarbonateRatio() { return bicarbonate; }

    public String name() { return name; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MineralProfile that = (MineralProfile) o;
        return Double.compare(that.sodio, sodio) == 0 &&
                Double.compare(that.cloruro, cloruro) == 0 &&
                Double.compare(that.calcio, calcio) == 0 &&
                Double.compare(that.solfato, solfato) == 0 &&
                Double.compare(that.magnesium, magnesium) == 0 &&
                Double.compare(that.bicarbonate, bicarbonate) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override public int hashCode() { return Objects.hash(sodio, cloruro, calcio, solfato, magnesium, bicarbonate, name); }

    @Override
    public String toString() {
        return "MineralProfile{" +
                "sodio=" + sodio +
                ", cloruro=" + cloruro +
                ", calcio=" + calcio +
                ", solfato=" + solfato +
                ", magnesium=" + magnesium +
                ", bicarbonate=" + bicarbonate +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private double sodioRatio;
        private double cloruroRatio;
        private double calcioRatio;
        private double solfatoRatio;
        private String name;
        private double magnesiumRatio;
        private double bicarbonate;

        public Builder sodioRatio(double sodioRatio) { this.sodioRatio = sodioRatio; return this; }
        public Builder cloruroRatio(double cloruroRatio) { this.cloruroRatio = cloruroRatio; return this; }
        public Builder calcioRatio(double calcioRatio) { this.calcioRatio = calcioRatio; return this; }
        public Builder solfatoRatio(double solfatoRatio) { this.solfatoRatio = solfatoRatio; return this; }
        public Builder magnesiumRatio(double m) { this.magnesiumRatio = m; return this; }
        public Builder bicarbonateRatio(double v) { this.bicarbonate = v; return this; }

        public Builder name(String name) { this.name = name; return this; }
        public MineralProfile build() { return new MineralProfile(sodioRatio, cloruroRatio, calcioRatio, solfatoRatio, name, magnesiumRatio, bicarbonate); }
    }
}
