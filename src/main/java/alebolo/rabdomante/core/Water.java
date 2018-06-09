package alebolo.rabdomante.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Water implements IWaterProfile {
    public final int l;
    public final IWaterProfile profile;
    public Water(IWaterProfile profile, int l) {
        this.profile = profile;
        this.l = l;
    }

    public boolean sameAs(Water b) {
        Water a = this;
        return (a.l == b.l) && IWaterProfile.sameProfile(a.profile, b.profile);
    }

    public Water(
            @JsonProperty(value = "ca", required = true)   int ca,
            @JsonProperty(value = "mg", required = true)   int mg,
            @JsonProperty(value = "na", required = true)   int na,
            @JsonProperty(value = "so4", required = true)  int so4,
            @JsonProperty(value = "cl", required = true)   int cl,
            @JsonProperty(value = "hco3", required = true) int hco3,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "l", required = true)    int l) {
        this.profile = new WaterProfile(name, ca, mg, na, so4, cl, hco3);
        this.l = l;
    }

    @Override
    public String toString() {
        return "AvailableWater{" +
                "ca=" + ca() +
                ", mg=" + mg() +
                ", na=" + na() +
                ", so4=" + so4() +
                ", cl=" + cl() +
                ", hco3=" + hco3() +
                ", name='" + name() + '\'' +
                ", l=" + l +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water water = (Water) o;
        return l == water.l &&
                Objects.equals(profile, water.profile);
    }

    @Override public int hashCode() { return Objects.hash(l, profile); }

    @Override public int ca() { return profile.ca(); }
    @Override public int mg() { return profile.mg(); }
    @Override public int na() { return profile.na(); }
    @Override public int so4() { return profile.so4(); }
    @Override public int cl() { return profile.cl(); }
    @Override public int hco3() { return profile.hco3(); }
    @Override public String name() { return profile.name(); }
}
