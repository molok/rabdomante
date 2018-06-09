package alebolo.rabdomante.core;

import java.util.Objects;

public class DeltaWaterProfile implements IWaterProfile {
    private final int ca;
    private final int mg;
    private final int na;
    private final int so4;
    private final int cl;
    private final int hco3;
    private final String name;

    public DeltaWaterProfile(IWaterProfile profile) {
        this.name = profile.name();
        this.ca = profile.ca();
        this.mg = profile.mg();
        this.na = profile.na();
        this.so4 = profile.so4();
        this.cl = profile.cl();
        this.hco3 = profile.hco3();
    }

    public DeltaWaterProfile(String name, int ca, int mg, int na, int so4, int cl, int hco3) {
        this.name = name;
        this.ca = ca;
        this.mg = mg;
        this.na = na;
        this.so4 = so4;
        this.cl = cl;
        this.hco3 = hco3;
    }

    @Override public int ca() { return ca; }
    @Override public int mg() { return mg; }
    @Override public int na() { return na; }
    @Override public int so4() { return so4; }
    @Override public int cl() { return cl; }
    @Override public int hco3() { return hco3; }
    @Override public String name() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeltaWaterProfile that = (DeltaWaterProfile) o;
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
}
