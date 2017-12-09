package alebolo.rabdomante;

import com.google.common.math.DoubleMath;

import java.util.Objects;

public class Water {
    private final double liters;
    private final double calcio;
    private final double magnesio;
    private final double sodio;
    private final double bicarbonati;
    private final double solfato;
    private final double cloruro;
    private final String name;

    public Water(double liters, WaterProfile profile) {
        this(liters,profile.calcioMgPerL() * liters
        , profile.magnesioMgPerL() * liters
        , profile.sodioMgPerL() * liters
        , profile.bicarbonatiMgPerL() * liters
        , profile.solfatoMgPerL() * liters
        , profile.cloruroMgPerL() * liters, profile.name());
    }

    public boolean isSameAs(Water water) {
        return DoubleMath.fuzzyCompare(water.liters, liters, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.calcio, calcio, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.magnesio, magnesio, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.sodio, sodio, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.bicarbonati, bicarbonati, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.solfato, solfato, 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.cloruro, cloruro, 0.001) == 0;
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
        public Builder cloruro(String name) { this.name = name; return this; }

        public Water build() {
            return new Water(liters, calcio, magnesio, sodio, bicarbonati, solfato, cloruro, name);
        }

        public Builder name(String name) { this.name = name; return this; }
    }

    public Water(double liters, double calcio, double magnesio, double sodio, double bicarbonati, double solfato, double cloruro, String name) {
        this.liters = liters;
        this.calcio = calcio;
        this.magnesio = magnesio;
        this.sodio = sodio;
        this.bicarbonati = bicarbonati;
        this.solfato = solfato;
        this.cloruro = cloruro;
        this.name = name;
    }

    public WaterProfile profile() {
        return new WaterProfile(calcioMg() / liters,
                magnesioMg() / liters,
                sodioMg() / liters,
                bicarbonatiMg() / liters,
                solfatoMg() / liters,
                cloruroMg() / liters, name);
    }

    public double calcioMg() { return calcio; }
    public double magnesioMg() { return magnesio; }
    public double sodioMg() { return sodio; }
    public double bicarbonatiMg() { return bicarbonati; }
    public double solfatoMg() { return solfato; }
    public double cloruroMg() { return cloruro; }
    public double liters() { return liters; }

    public String name() { return String.format("%.1f %s", liters, profile().name()); }

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

    public Water add(Water water) {
        return add(water, this.name + ", " + water.name());
    }

    public Water duplicate() {
        return add(new Water.Builder(0).build(), this.name);
    }

    public Water add(Water water, String newName) {
        return new Water(this.liters + water.liters
                , this.calcio + water.calcio
                , this.magnesio + water.magnesio
                , this.sodio + water.sodio
                , this.bicarbonati + water.bicarbonati
                , this.solfato + water.solfato
                , this.cloruro + water.cloruro, newName);
    }
}
