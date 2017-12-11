package alebolo.rabdomante;

public class SaltAddition {
    private final double grams;
    private final SaltProfile salt;

    public SaltAddition(double grams, SaltProfile salt) {
        this.grams = grams;
        this.salt = salt;
    }

    public double sodioMg() { return grams * salt.sodioRatio(); }
    public double calcioMg() { return grams * salt.calcioRatio(); }
    public double solfatoMg() { return grams * salt.solfatoRatio(); }
    public double cloruroMg() { return grams * salt.cloruroRatio(); }
    public double magnesioMg() { return 0; }
    public double bicarbonatiMg() { return 0; }
    public double grams() { return grams; }
    public SaltProfile profile() { return salt; }

    public String toString() {
        return String.format("%.2f g %s", grams, salt.toString());
    }
}
