package alebolo.rabdomante;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

public class Recipe {
    private final List<ProfileRatio> profRatio;
    private final List<SaltRatio> salts;
    private final static DecimalFormat df = new DecimalFormat("0.00");
    {
        df.applyLocalizedPattern("0.00");
    }

    public Recipe(List<ProfileRatio> profRatio, List<SaltRatio> salts) {
        if (profRatio.stream().mapToDouble(r -> r.ratio()).sum() != 1.) {
            throw new Defect("ratio non consistenti:"+ Arrays.toString(profRatio.toArray()));
        }
        this.profRatio = profRatio;
        this.salts = salts;
    }

    public static Recipe create(Profile profile) {
        return new Recipe(Arrays.asList(new ProfileRatio(profile, 1.0)));
    }

    public static Recipe create(Profile profile, List<SaltRatio> salts) {
        return new Recipe(Arrays.asList(new ProfileRatio(profile, 1.0)), salts);
    }

    public Recipe(List<ProfileRatio> profRatio) {
        this.profRatio = profRatio;
        this.salts = new ArrayList<>();
    }

    public List<ProfileRatio> profilesRatio() { return profRatio; }
    public List<SaltRatio> saltsRatio() { return salts; }

    @Override
    public String toString() {
        return "Recipe{"
//                + " calcioMgPerL:" + df.format(calcioMgPerL())
//                + ", magnesioMgPerL:" + df.format(magnesioMgPerL())
//                + ", sodioMgPerL:" + df.format(sodioMgPerL())
//                + ", bicarbonatiMgPerL:" + df.format(bicarbonatiMgPerL())
//                + ", solfatoMgPerL:" + df.format(solfatoMgPerL())
//                + ", cloruroMgPerL:" + df.format(cloruroMgPerL() )
                + "  profRatio=" + profRatio +
                ", , salts=" + salts +
                '}';
    }

    private double sumWaterAndSalts(ToDoubleFunction<ProfileRatio> getterProfileRatio,
                                    ToDoubleFunction<SaltRatio> getterSaltRatio) {
        return profRatio.stream().mapToDouble(getterProfileRatio).sum()
             + salts.stream().mapToDouble(getterSaltRatio).sum();
    }

    public double calcioMgPerL() { return sumWaterAndSalts(ProfileRatio::calcioMgPerL, SaltRatio::calcioMgPerL); }
    public double magnesioMgPerL() { return sumWaterAndSalts(ProfileRatio::magnesioMgPerL, SaltRatio::magnesioMgPerL); }
    public double sodioMgPerL() { return sumWaterAndSalts(ProfileRatio::sodioMgPerL, SaltRatio::sodioMgPerL); }
    public double bicarbonatiMgPerL() { return sumWaterAndSalts(ProfileRatio::bicarbonatiMgPerL, SaltRatio::bicarbonatiMgPerL); }
    public double solfatoMgPerL() { return sumWaterAndSalts(ProfileRatio::solfatoMgPerL, SaltRatio::solfatoMgPerL); }
    public double cloruroMgPerL() { return sumWaterAndSalts(ProfileRatio::cloruroMgPerL, SaltRatio::cloruroMgPerL); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(profRatio, recipe.profRatio) &&
                Objects.equals(salts, recipe.salts);
    }

    @Override public int hashCode() { return Objects.hash(profRatio, salts); }
}
