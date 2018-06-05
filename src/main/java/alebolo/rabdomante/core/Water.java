package alebolo.rabdomante.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Water extends WaterProfile {
    public final int l;
    public Water(WaterProfile profile, int l) {
        this(profile.ca, profile.mg, profile.na, profile.so4, profile.cl, profile.hco3, profile.name, l);
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
        super(name, ca, mg, na, so4, cl, hco3);
        this.l = l;
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
                ", l=" + l +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Water water = (Water) o;
        return l == water.l;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), l);
    }
}
