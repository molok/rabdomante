package alebolo.rabdomante;

public class SaltProfile {
    private final double sodio;
    private final double cloruro;
    private final double calcio;
    private final double solfato;
    private final String name;

    public SaltProfile(double sodioRatio, double cloruroRatio, double calcioRatio, double solfatoRatio, String name) {
        this.sodio = sodioRatio;
        this.cloruro = cloruroRatio;
        this.calcio = calcioRatio;
        this.solfato = solfatoRatio;
        this.name = name;
        if ((sodio + cloruro + calcio + solfato) > 1 ) {
            throw new RuntimeException("SaltProfile invalido");
        }
    }

    public double sodioRatio() { return sodio; }
    public double calcioRatio() { return calcio; }
    public double cloruroRatio() { return cloruro;}
    public double solfatoRatio() { return solfato;}
    public String name() { return name; }

    @Override public String toString() {
        return "SaltProfile{" +
                "sodio=" + sodio +
                ", cloruro=" + cloruro +
                ", calcio=" + calcio +
                ", solfato=" + solfato +
                ", name='" + name + '\'' +
                '}';
    }
}
