package alebolo.rabdomante.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Water implements IWaterProfile {
    public final int liters;
    public final IWaterProfile profile;
    public Water(IWaterProfile profile, int liters) {
        this.profile = profile;
        this.liters = liters;
    }

    public boolean sameAs(Water b) {
        Water a = this;
        return (a.liters == b.liters) && IWaterProfile.sameProfile(a.profile, b.profile);
    }

    public Water(
            @JsonProperty(value = "ca", required = true)   int ca,
            @JsonProperty(value = "mg", required = true)   int mg,
            @JsonProperty(value = "na", required = true)   int na,
            @JsonProperty(value = "so4", required = true)  int so4,
            @JsonProperty(value = "cl", required = true)   int cl,
            @JsonProperty(value = "hco3", required = true) int hco3,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "l", required = true)    int liters) {
        this.profile = new WaterProfile(name, ca, mg, na, so4, cl, hco3);
        this.liters = liters;
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
                ", liters=" + liters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water water = (Water) o;
        return liters == water.liters &&
                Objects.equals(profile, water.profile);
    }

    @Override public int hashCode() { return Objects.hash(liters, profile); }

    @Override public int ca() { return profile.ca(); }
    @Override public int mg() { return profile.mg(); }
    @Override public int na() { return profile.na(); }
    @Override public int so4() { return profile.so4(); }
    @Override public int cl() { return profile.cl(); }
    @Override public int hco3() { return profile.hco3(); }
    @Override public String name() { return profile.name(); }
}
