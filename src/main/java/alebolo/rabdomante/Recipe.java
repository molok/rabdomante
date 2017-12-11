package alebolo.rabdomante;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private final List<ProfileRatio> profRatio;
    private final List<SaltRatio> salts;

    public Recipe(List<ProfileRatio> profRatio, List<SaltRatio> salts) {
        if (profRatio.stream().mapToDouble(r -> r.ratio()).sum() != 1.) {
            throw new Defect("ratio non consistenti");
        }
        this.profRatio = profRatio;
        this.salts = salts;
    }

    public Recipe(List<ProfileRatio> profRatio) {
        this.profRatio = profRatio;
        this.salts = new ArrayList<>();
    }

    public List<ProfileRatio> profilesRatio() { return profRatio; }
    public List<SaltRatio> saltsRatio() { return salts; }

    @Override public String toString() { return String.format("profilesRatio {%s}, saltsRatio {%s}", profRatio.toString(), salts.toString()); }
}
