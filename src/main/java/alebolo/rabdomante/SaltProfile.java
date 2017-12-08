package alebolo.rabdomante;

public class SaltProfile {
    private final double sodio;
    private final double cloruro;
    private final double calcio;
    private final double solfato;

    public SaltProfile(double sodioPerc, double cloruroPerc, double calcioPerc, double solfatoPerc) {
        this.sodio = sodioPerc;
        this.cloruro = cloruroPerc;
        this.calcio = calcioPerc;
        this.solfato = solfatoPerc;
        if ((sodio + cloruro + calcio + solfato) > 100 ) {
            throw new RuntimeException("SaltProfile invalido");
        }
    }

    public double sodioPerc() { return sodio; }
    public double calcioPerc() { return calcio; }
    public double cloruroPerc() { return cloruro;}
    public double solfatoPerc() { return solfato;}
}
