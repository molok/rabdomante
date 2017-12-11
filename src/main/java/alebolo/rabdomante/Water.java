package alebolo.rabdomante;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Water implements IWater {
    private final double liters;
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;
    private final String name;

    /*

    Osservazione: molto probabilmente conviene strutturare l'acqua come un insieme di profili, ognuno con il suo ratio, più
    i sali che possono comporre l'acqua

     */

    public Water(double liters, WaterProfile profile) {
        this(liters,profile.calcioMgPerL() * liters
        , profile.magnesioMgPerL() * liters
        , profile.sodioMgPerL() * liters
        , profile.bicarbonatiMgPerL() * liters
        , profile.solfatoMgPerL() * liters
        , profile.cloruroMgPerL() * liters, profile.name());
    }

    public Water(double liters, double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro, String name) {
        this.liters = liters;
        this.calcio = calcio;
        this.magnesio = magnesio;
        this.sodio = sodio;
        this.bicarbonati = bicarbonati;
        this.solfato = solfato;
        this.cloruro = cloruro;
        this.name = Objects.requireNonNull(name);
    }

    public double calcioMg() { return calcio; }
    public double magnesioMg() { return magnesio; }
    public double sodioMg() { return sodio; }
    public double bicarbonatiMg() { return bicarbonati; }
    public double solfatoMg() { return solfato; }
    public double cloruroMg() { return cloruro; }
    public double liters() { return liters; }

    @Override public List<SourceRatio> sources() { return Arrays.asList(new SourceRatio(profile(), 1.)); }
    @Override public List<Water> composition() { return new ArrayList<>(); }

    public String name() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water water = (Water) o;
        return Double.compare(water.liters, liters) == 0 &&
                Double.compare(water.calcio, calcio) == 0 &&
                Double.compare(water.magnesio, magnesio) == 0 &&
                Double.compare(water.sodio, sodio) == 0 &&
                Double.compare(water.bicarbonati, bicarbonati) == 0 &&
                Double.compare(water.solfato, solfato) == 0 &&
                Double.compare(water.cloruro, cloruro) == 0 &&
                Objects.equals(name, water.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(liters, calcio, magnesio, sodio, bicarbonati, solfato, cloruro, name);
    }

    @Override
    public String toString() {
        return "Water (" + name +") {" +
                "liters=" + String.format("%.2f", liters) +
                ", calcio=" + String.format("%.2f", calcio) +
                ", magnesio=" + String.format("%.2f", magnesio) +
                ", sodio=" + String.format("%.2f", sodio) +
                ", bicarbonati=" + String.format("%.2f", bicarbonati) +
                ", solfato=" + String.format("%.2f", solfato) +
                ", cloruro=" + String.format("%.2f", cloruro) +
                '}';
    }

    public IWater add(IWater water) {
        return WaterMix.create(Arrays.asList(this, water));
    }

    public static class Builder {
        private double liters = 0;
        private double calcio = 0;
        private double magnesio = 0;
        private double sodio = 0;
        private double bicarbonati = 0;
        private double solfato = 0;
        private double cloruro = 0;
        private String name;

        Builder(double liters) {
            this.liters = liters;
        }

        public Builder calcio(double calcio) { this.calcio = calcio; return this; }
        public Builder magnesio(double magnesio) { this.magnesio = magnesio; return this; }
        public Builder sodio(double sodio) { this.sodio = sodio; return this; }
        public Builder bicarbonati(double bicarbonati) { this.bicarbonati = bicarbonati; return this; }
        public Builder solfato(double solfato) { this.solfato = solfato; return this; }
        public Builder cloruro(double cloruro) { this.cloruro = cloruro; return this; }

        public Water build() {
            return new Water(liters, calcio, magnesio, sodio, bicarbonati, solfato, cloruro, name);
        }

        public Builder name(String name) { this.name = name; return this; }
    }

}
