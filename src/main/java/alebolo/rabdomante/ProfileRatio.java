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
                "profile=" + profile +
                ", ratio=" + ratio +
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
}
