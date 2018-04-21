package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.WaterProfile;

import java.util.ArrayList;
import java.util.List;

public class UserInput {
    private final List<WaterProfile> waters = new ArrayList<>();

    public void addWater(WaterProfile profile) {
        waters.add(profile);
    }
}
