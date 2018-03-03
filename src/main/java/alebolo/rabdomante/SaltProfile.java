package alebolo.rabdomante;

import java.util.Objects;

class SaltProfile {
    final int ca;
    final int mg;
    final int na;
    final int so4;
    final int cl;
    final int hco3;
    final String nome;

    static class Builder {
        private final String nome;
        int ca = 0;
        int mg = 0;
        int na = 0;
        int so4 = 0;
        int cl = 0;
        int hco3 = 0;

        Builder(String nome) { this.nome = nome; }
        Builder ca(int ca) { this.ca = ca; return this; }
        Builder mg(int mg) { this.mg = mg; return this; }
        Builder na(int na) { this.na = na; return this; }
        Builder so4(int so4) { this.so4 = so4; return this; }
        Builder cl(int cl) { this.cl = cl; return this; }
        Builder hco3(int hco3) { this.hco3 = hco3; return this; }
        SaltProfile build() { return new SaltProfile(nome, ca, mg, na, so4, cl, hco3); }
    }

    SaltProfile(String nome, int ca, int mg, int na, int so4, int cl, int hco3) {
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
