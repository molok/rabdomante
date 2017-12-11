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

    public double liters() { return liters; }
}
