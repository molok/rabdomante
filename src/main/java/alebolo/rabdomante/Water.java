package alebolo.rabdomante;

import com.google.common.math.DoubleMath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.stream.Collectors;

public class Water {
    private final double liters;
    private final Recipe recipe;
    private final static DecimalFormat fmt = new DecimalFormat("###.##");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final int hashCode;

    public Water(double liters, Recipe recipe) {
        this.liters = liters;
        this.recipe = recipe;
        this.hashCode = Objects.hash(liters, recipe);
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

    public boolean isSameAs(Water water) {
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
        Water water2 = (Water) o;
        return Double.compare(water2.liters, liters) == 0 &&
                Objects.equals(recipe, water2.recipe);
    }

    @Override public int hashCode() { return hashCode; }

    public String description() {
        String salts = this.recipe().saltsRatio().stream()
                .map(s -> String.format("%.2f", (s.mgPerL()/1000.) * liters()) + "g " + s.profile().name()
                        + String.format(" (%.2f mg/L)", s.mgPerL()))
                .collect( Collectors.joining(", "));
        String profiles = this.recipe().profilesRatio().stream()
                .map(p -> String.format("%.2f", p.ratio() * liters()) + "L " + p.profile().name())
                .collect( Collectors.joining(", "));
        return profiles + " " + salts;
    }
}
