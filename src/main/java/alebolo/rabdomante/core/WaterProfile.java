package alebolo.rabdomante.core;

import java.util.Objects;

public class WaterProfile implements IWaterProfile {
    private final int ca;
    private final int mg;
    private final int na;
    private final int so4;
    private final int cl;
    private final int hco3;
    private final String name;

    public WaterProfile(String name, int ca, int mg, int na, int so4, int cl, int hco3) {
        this.name = name;
        this.ca = validate(ca);
        this.mg = validate(mg);
        this.na = validate(na);
        this.so4 = validate(so4);
        this.cl = validate(cl);
        this.hco3 = validate(hco3);
    }

    @Override public String toString() {
        return "Water{" +
                "ca=" + ca() +
                ", mg=" + mg() +
                ", na=" + na() +
                ", so4=" + so4() +
                ", cl=" + cl() +
                ", hco3=" + hco3() +
                ", name='" + name() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterProfile that = (WaterProfile) o;
        return ca() == that.ca() &&
                mg() == that.mg() &&
                na() == that.na() &&
                so4() == that.so4() &&
                cl() == that.cl() &&
                hco3() == that.hco3() &&
                Objects.equals(name(), that.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ca(), mg(), na(), so4(), cl(), hco3(), name());
    }

    @Override public int ca() { return ca; }
    @Override public int mg() { return mg; }
    @Override public int na() { return na; }
    @Override public int so4() { return so4; }
    @Override public int cl() { return cl; }
    @Override public int hco3() { return hco3; }
    @Override public String name() { return name; }
}
