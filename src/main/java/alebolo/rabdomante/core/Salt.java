package alebolo.rabdomante.core;

import java.util.Objects;

public class Salt extends SaltProfile {
    public final int dg;
    public Salt(SaltProfile salt, int dg) {
        this(salt.ca, salt.mg, salt.na, salt.so4, salt.cl, salt.hco3, salt.name, dg);
    }
    Salt(int ca, int mg, int na, int so4, int cl, int hco3, String nome, int dg) {
        super(nome, ca, mg, na, so4, cl, hco3);
        this.dg = dg;
    }

    @Override
    public String toString() {
        return "Salt{" +
                "ca=" + ca +
                ", mg=" + mg +
                ", na=" + na +
                ", so4=" + so4 +
                ", cl=" + cl +
                ", hco3=" + hco3 +
                ", name='" + name + '\'' +
                ", g=" + dg/10. +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Salt salt = (Salt) o;
        return dg == salt.dg;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), dg);
    }
}
