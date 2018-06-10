package alebolo.rabdomante.core;

public class WSolution {
    public final Recipe recipe;
    public final boolean searchCompleted;

    public WSolution(Recipe recipe, boolean searchCompleted) {
        this.recipe = recipe;
        this.searchCompleted = searchCompleted;
    }
}
