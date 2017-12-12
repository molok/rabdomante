package alebolo.rabdomante;

import com.google.common.math.DoubleMath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.stream.Collectors;

public class Water2 {
    private final double liters;
    private final Recipe recipe;
    private final static DecimalFormat fmt = new DecimalFormat("###.##");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public Water2(double liters, Recipe recipe) {
        this.liters = liters;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    public Recipe recipe() { return recipe; }

    public double calcioMg() { return liters * recipe.calcioMgPerL(); }
    public double magnesioMg() { return liters * recipe.magnesioMgPerL(); }
    public double sodioMg() { return liters * recipe.sodioMgPerL(); }
    public double bicarbonatiMg() { return liters * recipe.bicarbonatiMgPerL(); }
    public double solfatoMg() { return liters * recipe.solfatoMgPerL(); }
    public double cloruroMg() { return liters * recipe.cloruroMgPerL();  }
    public double liters() { return liters; }

    public boolean isSameAs(Water2 water) {
        return DoubleMath.fuzzyCompare(water.liters(), this.liters(), 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.calcioMg(), this.calcioMg(), 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.magnesioMg(), this.magnesioMg(), 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.sodioMg(), this.sodioMg(), 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.bicarbonatiMg(), this.bicarbonatiMg(), 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.solfatoMg(), this.solfatoMg(), 0.001) == 0 &&
                DoubleMath.fuzzyCompare(water.cloruroMg(), this.cloruroMg(), 0.001) == 0;
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water2 water2 = (Water2) o;
        return Double.compare(water2.liters, liters) == 0 &&
                Objects.equals(recipe, water2.recipe);
    }

    @Override public int hashCode() {
        return Objects.hash(liters, recipe);
    }

    public String description() {
        String salts = this.recipe().saltsRatio().stream()
                .map(s -> String.format("%.2f", (s.mgPerL()/1000.) * liters()) + "g " + s.profile().name())
                .collect( Collectors.joining(", "));
        String profiles = this.recipe().profilesRatio().stream()
                .map(p -> String.format("%.2f", p.ratio() * liters()) + "L " + p.profile().name())
                .collect( Collectors.joining(", "));
        return profiles + " " + salts;
    }
}
