package alebolo.rabdomante;

import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class WaterMixer {
    public static Water merge(Water w1, Water w2) {
        double totLiters = w1.liters() + w2.liters();
        return new Water(totLiters,
                new Recipe(combineProfiles(w1, w2, totLiters), combineSalts(w1, w2, totLiters)));
    }

    private static List<MineralRatio> combineSalts(Water w1, Water w2, double totLiters) {
        Map<String, MineralRatio> salts = new HashMap<>();
        for (MineralRatio currSalt : ListUtils.union(weigthedSalts(w1, totLiters), weigthedSalts(w2, totLiters))) {
            salts.merge(
                    currSalt.profile().name(),
                    currSalt,
                    (a, b) -> new MineralRatio(a.profile(), a.mgPerL() + b.mgPerL()) );
        }

        return new ArrayList<>(salts.values());
    }

    private static List<ProfileRatio> combineProfiles(Water w1, Water w2, double totLiters) {
        Map<String, ProfileRatio> profiles = new HashMap<>();
        for (ProfileRatio currProfile : ListUtils.union(weightedProfiles(w1, totLiters),
                                                        weightedProfiles(w2, totLiters))) {
            profiles.merge(
                    currProfile.profile().name(),  /* assunzione che a parità di */
                    currProfile,
                    (a, b) -> new ProfileRatio(a.profile(), a.ratio() + b.ratio()) );
        }

        return new ArrayList<>(profiles.values());
    }

    private static List<MineralRatio> weigthedSalts(Water w, double totLiters) {
        return w.recipe().saltsRatio().stream()
                .map(pr -> new MineralRatio(pr.profile(), pr.mgPerL() * (w.liters() / totLiters)))
                .collect(Collectors.toList());
    }

    private static List<ProfileRatio> weightedProfiles(Water w, double totLiters) {
        return w.recipe().profilesRatio().stream()
                .map(pr -> new ProfileRatio(pr.profile(), pr.ratio() * (w.liters() / totLiters)))
                .collect(Collectors.toList());
    }
}
