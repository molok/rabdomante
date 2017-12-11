package alebolo.rabdomante;

import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class WaterMerger {
    public static Water2 merge(Water2 w1, Water2 w2) {
        double totLiters = w1.liters() + w2.liters();
        return new Water2(totLiters,
                new Recipe(combineProfiles(w1, w2, totLiters), combineSalts(w1, w2, totLiters)));
    }

    private static List<SaltRatio> combineSalts(Water2 w1, Water2 w2, double totLiters) {
        Map<String, SaltRatio> salts = new HashMap<>();
        for (SaltRatio currSalt : ListUtils.union(weigthedSalts(w1, totLiters),
                weigthedSalts(w2, totLiters))) {
            salts.merge(
                    currSalt.profile().name(),
                    currSalt,
                    (a, b) -> new SaltRatio(a.profile(), a.gramsPerL() + b.gramsPerL()) );
        }

        return new ArrayList<>(salts.values());
    }

    private static List<ProfileRatio> combineProfiles(Water2 w1, Water2 w2, double totLiters) {
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

    private static List<SaltRatio> weigthedSalts(Water2 w, double totLiters) {
        return w.recipe().saltsRatio().stream()
                .map(pr -> new SaltRatio(pr.profile(), pr.gramsPerL() * (w.liters() / totLiters)))
                .collect(Collectors.toList());
    }

    private static List<ProfileRatio> weightedProfiles(Water2 w, double totLiters) {
        return w.recipe().profilesRatio().stream()
                .map(pr -> new ProfileRatio(pr.profile(), pr.ratio() * (w.liters() / totLiters)))
                .collect(Collectors.toList());
    }
}
