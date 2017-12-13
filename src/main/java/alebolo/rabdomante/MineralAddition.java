package alebolo.rabdomante;

public class MineralAddition {
    private final double grams;
    private final MineralProfile salt;

    public MineralAddition(double grams, MineralProfile salt) {
        this.grams = grams;
        this.salt = salt;
    }

    public double grams() { return grams; }
    public MineralProfile profile() { return salt; }

    public String toString() {
        return String.format("%.2f g %s", grams, salt.toString());
    }
}
