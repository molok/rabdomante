package alebolo.rabdomante.core;

import java.util.Objects;

public class Water extends WaterProfile {
    public final int liters;
    public Water(WaterProfile profile, int liters) {
        this(profile.ca, profile.mg, profile.na, profile.so4, profile.cl, profile.hco3, profile.nome, liters);
    }
    public Water(int ca, int mg, int na, int so4, int cl, int hco3, String nome, int liters) {
        super(nome, ca, mg, na, so4, cl, hco3);
        this.liters = liters;
    }

    @Override
    public String toString() {
        return "AvailableWater{" +
                "ca=" + ca +
                ", mg=" + mg +
                ", na=" + na +
                ", so4=" + so4 +
                ", cl=" + cl +
                ", hco3=" + hco3 +
                ", nome='" + nome + '\'' +
                ", liters=" + liters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Water water = (Water) o;
        return liters == water.liters;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), liters);
    }
}
