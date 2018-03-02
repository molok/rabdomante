package alebolo.rabdomante;

import java.util.Objects;

class WaterProfile {
    static final WaterProfile SANTANNA = new WaterProfile(10, 0, 1, 8, 0, 26, "santanna");
    static final WaterProfile MILANO = new WaterProfile(70, 15, 12, 42, 27, 228, "milano");
    static final WaterProfile BOARIO = new WaterProfile(131, 40, 5, 240, 4, 303, "boario");
    static final WaterProfile LEVISSIMA = new WaterProfile(21, 2, 2, 17, 0, 57, "levissima");
    static final WaterProfile EVA = new WaterProfile(10, 4, 0, 2, 0, 48, "eva");
    static final WaterProfile NORDA = new WaterProfile(11, 3, 2, 6, 1, 52, "norda");
    static final WaterProfile VERA = new WaterProfile(35, 12, 2, 19, 3, 148, "vera");
    static final WaterProfile VITASNELLA = new WaterProfile(86, 26, 3, 83, 2, 301, "vitasnella");
    static final WaterProfile DOLOMITI = new WaterProfile(8, 9, 1, 22, 1, 95, "dolomiti");
    static final WaterProfile SANBERARDO = new WaterProfile(9, 1, 1, 2, 1, 30, "sanberardo");
    static final WaterProfile DISTILLED = new WaterProfile(0, 0, 0, 0, 0, 0, "distilled");
    static final WaterProfile YELLOW_DRY = new WaterProfile(50, 10, 5, 105, 45, 0, "yellow dry");

    final int ca;
    final int mg;
    final int na;
    final int so4;
    final int cl;
    final int hco3;
    final String nome;

    WaterProfile(int ca, int mg, int na, int so4, int cl, int hco3, String nome) {
        this.ca = ca;
        this.mg = mg;
        this.na = na;
        this.so4 = so4;
        this.cl = cl;
        this.hco3 = hco3;
        this.nome = nome;
    }

    @Override public String toString() {
        return "Water{" +
                "ca=" + ca +
                ", mg=" + mg +
                ", na=" + na +
                ", so4=" + so4 +
                ", cl=" + cl +
                ", hco3=" + hco3 +
                ", nome='" + nome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterProfile that = (WaterProfile) o;
        return ca == that.ca &&
                mg == that.mg &&
                na == that.na &&
                so4 == that.so4 &&
                cl == that.cl &&
                hco3 == that.hco3 &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ca, mg, na, so4, cl, hco3, nome);
    }
}
