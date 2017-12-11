package alebolo.rabdomante;

import java.util.Objects;

class ProfileRatio {
    private final Profile profile;
    private final double ratio;
    public ProfileRatio(Profile profile, double ratio) {
        this.profile = profile;
        this.ratio = ratio;
    }

    Profile profile() { return profile; }
    double ratio() { return ratio; }

    @Override
    public String toString() {
        return "ProfileRatio{" +
                " ratio=" + ratio +
                ", profile=" + profile +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileRatio that = (ProfileRatio) o;
        return Double.compare(that.ratio, ratio) == 0 &&
                Objects.equals(profile, that.profile);
    }

    @Override public int hashCode() { return Objects.hash(profile, ratio); }

    public double calcioMgPerL() { return profile.calcioMgPerL() * ratio; }
    public double magnesioMgPerL() { return profile.magnesioMgPerL() * ratio; }
    public double sodioMgPerL() { return profile.sodioMgPerL() * ratio; }
    public double bicarbonatiMgPerL() { return profile.bicarbonatiMgPerL() * ratio; }
    public double solfatoMgPerL() { return profile.solfatoMgPerL() * ratio; }
    public double cloruroMgPerL() { return profile.cloruroMgPerL() * ratio; }
}
