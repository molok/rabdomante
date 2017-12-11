package alebolo.rabdomante;

import java.text.DecimalFormat;

public class Water2 {
    private final double liters;
    private final Recipe recipe;
    private final static DecimalFormat fmt = new DecimalFormat("###.##");

    public Water2(double liters, Recipe recipe) {
        this.liters = liters;
        this.recipe = recipe;
    }

    @Override public String toString() {
        return String.format("%sL of %s", fmt.format(liters), recipe.toString());
    }

    public Recipe recipe() { return recipe; }

    public double calcioMg() { return liters * recipe.calcioMgPerL(); }
    public double magnesioMg() { return liters * recipe.magnesioMgPerL(); }
    public double sodioMg() { return liters * recipe.sodioMgPerL(); }
    public double bicarbonatiMg() { return liters * recipe.bicarbonatiMgPerL(); }
    public double solfatoMg() { return liters * recipe.solfatoMgPerL(); }
    public double cloruroMg() { return liters * recipe.cloruroMgPerL();  }
    public double liters() { return liters; }
}
