package alebolo.rabdomante.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Water extends WaterProfile {
    public final int liters;
    public Water(WaterProfile profile, int liters) {
        this(profile.ca, profile.mg, profile.na, profile.so4, profile.cl, profile.hco3, profile.name, liters);
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
        super(name, ca, mg, na, so4, cl, hco3);
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
                ", name='" + name + '\'' +
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
