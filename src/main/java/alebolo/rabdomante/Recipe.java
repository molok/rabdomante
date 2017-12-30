package alebolo.rabdomante;

import com.google.common.math.DoubleMath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class Recipe implements IMgPerLDouble {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final List<ProfileRatio> profRatio;
    private final Map<String, MineralRatio> salts;
    private final double calcioMgPerL;
    private final double magnesioMgPerL;
    private final double sodioMgPerL;
    private final double bicarbonatiMgPerL;
    private final double solfatoMgPerL;
    private final double cloruroMgPerL;

    public Recipe(List<ProfileRatio> profRatio, List<MineralRatio> salts) {
        if (DoubleMath.fuzzyCompare(profRatio.stream().mapToDouble(r -> r.ratio()).sum(), 1., 0.1) != 0) {
            throw new Defect("ratio non consistenti, trovato:"+ profRatio.stream().mapToDouble(r -> r.ratio()).sum() + ", "+ Arrays.toString(profRatio.toArray()));
        }
        this.profRatio = profRatio;
        this.salts = mergeDuplicates(salts);

        /* li calcolo perché ci accediamo molto spesso */
        this.calcioMgPerL = sumWaterAndSalts(ProfileRatio::calcioMgPerL, MineralRatio::calcioMgPerL);
        this.magnesioMgPerL = sumWaterAndSalts(ProfileRatio::magnesioMgPerL, MineralRatio::magnesioMgPerL);
        this.sodioMgPerL = sumWaterAndSalts(ProfileRatio::sodioMgPerL, MineralRatio::sodioMgPerL);
        this.bicarbonatiMgPerL = sumWaterAndSalts(ProfileRatio::bicarbonatiMgPerL, MineralRatio::bicarbonatiMgPerL);
        this.solfatoMgPerL = sumWaterAndSalts(ProfileRatio::solfatoMgPerL, MineralRatio::solfatoMgPerL);
        this.cloruroMgPerL = sumWaterAndSalts(ProfileRatio::cloruroMgPerL, MineralRatio::cloruroMgPerL);
    }

    private Map<String, MineralRatio> mergeDuplicates(List<MineralRatio> salts) {
        Map<String,MineralRatio> res = new HashMap<>();
        for (MineralRatio s : salts) {
            if (s.mgPerL() > 0.) {
                res.merge(s.profile().name(), s, (s1, s2) -> new MineralRatio(s.profile(), s1.mgPerL() + s2.mgPerL()));
            }
        }
        return res;
    }

    public static Recipe create(Profile profile) {
        return new Recipe(Arrays.asList(new ProfileRatio(profile, 1.0)));
    }

    public static Recipe create(Profile profile, List<MineralRatio> salts) {
        return new Recipe(Arrays.asList(new ProfileRatio(profile, 1.0)), salts);
    }

    public Recipe(List<ProfileRatio> profRatio) {
        this(profRatio, new ArrayList<>());
    }

    public List<ProfileRatio> profilesRatio() { return profRatio; }
    public List<MineralRatio> saltsRatio() { return new ArrayList<>(salts.values()); }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    private double sumWaterAndSalts(ToDoubleFunction<ProfileRatio> getterProfileRatio,
                                    ToDoubleFunction<MineralRatio> getterSaltRatio) {
        return profRatio.stream().mapToDouble(getterProfileRatio).sum()
             + salts.values().stream().mapToDouble(getterSaltRatio).sum();
    }

    public double calcioMgPerL() { return calcioMgPerL; }
    public double magnesioMgPerL() { return magnesioMgPerL; }
    public double sodioMgPerL() { return sodioMgPerL; }
    public double bicarbonatiMgPerL() { return bicarbonatiMgPerL; }
    public double solfatoMgPerL() { return solfatoMgPerL; }
    public double cloruroMgPerL() { return cloruroMgPerL; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(profRatio, recipe.profRatio) &&
                Objects.equals(salts, recipe.salts);
    }

    @Override public int hashCode() { return Objects.hash(profRatio, salts); }
}
