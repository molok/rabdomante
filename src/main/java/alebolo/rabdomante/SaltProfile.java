package alebolo.rabdomante;

import java.util.Objects;

class SaltProfile {
    static final SaltProfile GYPSUM = new SaltProfile(23, 0, 0, 56, 0, 0, "GYPSUM");
    static final SaltProfile TABLE_SALT = new SaltProfile(0, 0, 39, 0, 62, 0, "table salt");
    static final SaltProfile CALCIUM_CHLORIDE = new SaltProfile(36, 0, 0, 0, 64, 0, "calcium chloride");
    static final SaltProfile MAGNESIUM_CHLORIDE = new SaltProfile(0, 12, 0, 0, 35, 0, "magnesium chloride");
    static final SaltProfile EPSOM_SALT = new SaltProfile(0, 1, 0, 0, 35, 0, "epsom salt");
    static final SaltProfile BAKING_SODA = new SaltProfile(0, 0, 27, 0, 0, 72, "baking soda");
    static final SaltProfile PICKLING_LIME = new SaltProfile(45, 0, 0, 0, 0, 164, "pickling lime");

    final int ca;
    final int mg;
    final int na;
    final int so4;
    final int cl;
    final int hco3;
    final String nome;

    SaltProfile(int ca, int mg, int na, int so4, int cl, int hco3, String nome) {
        this.ca = ca;
        this.mg = mg;
        this.na = na;
        this.so4 = so4;
        this.cl = cl;
        this.hco3 = hco3;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "SaltProfile{" +
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
        SaltProfile that = (SaltProfile) o;
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
