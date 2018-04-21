package alebolo.rabdomante.core;

import java.util.Objects;

public class WaterProfile {
    public final int ca;
    public final int mg;
    public final int na;
    public final int so4;
    public final int cl;
    public final int hco3;
    public final String nome;

    public static boolean sameProfile(WaterProfile a, WaterProfile b) {
        return a.ca == b.ca &&
               a.mg == b.mg &&
               a.na == b.na &&
               a.so4 == b.so4 &&
               a.cl == b.cl &&
               a.hco3 == b.hco3;
    }

    public WaterProfile(String nome, int ca, int mg, int na, int so4, int cl, int hco3) {
        this.nome = nome;
        this.ca = ca;
        this.mg = mg;
        this.na = na;
        this.so4 = so4;
        this.cl = cl;
        this.hco3 = hco3;
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
