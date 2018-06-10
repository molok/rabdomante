package alebolo.rabdomante.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Salt extends SaltProfile {
    public final int dg;
    public Salt(SaltProfile salt, int dg) {
        this(salt.ca, salt.mg, salt.na, salt.so4, salt.cl, salt.hco3, salt.name, dg);
    }
    public Salt(
            @JsonProperty(value = "ca", required = true)   int ca,
            @JsonProperty(value = "mg", required = true)   int mg,
            @JsonProperty(value = "na", required = true)   int na,
            @JsonProperty(value = "so4", required = true)  int so4,
            @JsonProperty(value = "cl", required = true)   int cl,
            @JsonProperty(value = "hco3", required = true) int hco3,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value= "dg", required = true)   int dg) {
        super(name, ca, mg, na, so4, cl, hco3);
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
